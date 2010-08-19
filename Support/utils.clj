(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(require '[clojure.contrib.seq-utils :as seq-utils])

(defonce *compiled-files* (atom #{}))

(defn htmlize [#^String text]
  (-> text
      (.replaceAll "&"  "&amp;")
      (.replaceAll "<"  "&lt;")
      (.replaceAll ">"  "&gt;")
      (.replaceAll "\n" "<br>")))

(defn str-nil [o]
  (if o (str o) "nil"))

(defn escape-quotes [#^String s]
  (-> s
     (.replaceAll "\"" "\\\"")))

(defn escape-characters [#^String s]
 (let [#^java.util.regex.Matcher m (.matcher #"\\(\S)" s)]
    (if (.matches m)
      (.replaceAll m "\\\\$1")
      s)))

(defn escape-str [s]
  (-> s #_escape-characters escape-quotes))

(defn print-stack-trace [exc]
  (println (.getMessage exc))
  (doall (map #(println (.toString %)) (seq (.getStackTrace exc)))))

(defmacro attempt [& body]
  `(try
     (do
       ~@body)
     (catch Exception e#
       (clojure.core/println
         (clojure.core/str
           "<pre>"
           (with-out-str (textmate/print-stack-trace e#))
           "</pre>")))))

(defn filepath->ns-str
  "Convert filepath to ns-str"
  [path]
  (-> path
      (string/replace ".clj" "")
      (string/replace "_" "-")
      (string/replace "/" ".")))

(defn text-forms
  "Wrap the forms in text t in a vector. Used
  for all the eval functions"
  [t]
  (read-string (str "[" t "]")))

;(defn push-back-reader-from-path 
;  { :tag #^java.io.PushbackReader }
;  [#^String path]
;  (-> path java.io.FileReader.
;           java.io.BufferedReader.
;           java.io.PushbackReader.))
  
;(defn read-forms [#^java.io.PushbackReader reader]
;  (loop [forms []]
;    (try
;        (let [form (read reader)]
;          (cond )))))

(defn file-ns
  "Find the namespace of a file; searches for the first ns  (or in-ns)
   form in the file and returns that symbol. Defaults to 'user if one
   can't be found"
  []
  (let [forms (-> (cake/*env* "TM_FILEPATH")
                  slurp
                  text-forms
                  #_push-back-reader-from-path)
        [ns-fn ns] (first
                      (for [f forms
                            :when (and (seq? f)
                                       (#{"ns" "in-ns"} (str (first f))))]
                    [(first f) (second f)]))]
    (if ns
      (if (= (str ns-fn) "ns") ns (eval ns))
      'user)))

(defn enter-ns
  "Enter a ns, wrapped for debugging purposes"
  [ns]
  #_(println (str "Entering " ns))
  (in-ns ns))

(defn enter-file-ns 
  "Enter the ns of the file"
  []
  (let [ns (file-ns)]
    (enter-ns ns)))

(defmacro eval-in-ns
  ""
  [the-ns & forms]
  `(let [old-ns# *ns*]
    (enter-ns ~the-ns)
    (let [r# ~@forms]
      (enter-ns (-> old-ns# str symbol))
      r#)))

(defmacro eval-in-file-ns 
  "For the current file, enter the ns (if any)
  and evaluate the form in that ns, then pop
  back up to the original ns"
  [& forms]
  `(let [old-ns# *ns*]
    (enter-file-ns)
    (let [r# ~@forms]
      (enter-ns (-> old-ns# str symbol))
      r#)))

(defn project-relative-src-path []
   (let [user-dir (str (cake/*env* "TM_PROJECT_DIRECTORY") "/src/")
        path-to-file (string/replace (cake/*env* "TM_FILEPATH")  user-dir "")]
  path-to-file))

(defn carret-info
  "returns [path line-index column-index] info
   about current location of cursor"
  []
  [(cake/*env* "TM_FILEPATH")
    (dec (Integer/parseInt (cake/*env* "TM_LINE_NUMBER")))
    (dec (Integer/parseInt (cake/*env* "TM_COLUMN_NUMBER")))])

(defn text-before-carret []
  (let [[path,line-index,column-index] (carret-info)
        lines (-> path io/reader line-seq)
        #^String last-line (nth lines line-index)]
     (apply str
       (apply str (for [l (take line-index lines)] (str l "\n")))
       (.substring last-line 0 (min column-index (.length last-line))))))

(defn text-after-carret []
 (let [[path,line-index,column-index] (carret-info)
       lines (-> path io/reader line-seq)]
    (apply str 
      (.substring #^String (nth lines line-index) column-index)
      (apply str (for [l (drop (inc line-index) lines)] (str l "\n"))))))

;(defn make-cannonical-form-text [t]
;  (.replaceAll #^String t "\\s+" " "))

;(defn str-escape [t]
;  (.replaceAll #^String t "\\n" "\\n"))

(defn get-current-symbol-str
  "Get the string of the current symbol of the cursor"
  []
  (let [#^String line (-> "TM_CURRENT_LINE" cake/*env* escape-str)
        index    (int (last (carret-info)))
        symbol-char? (fn [index]
                       (and (< index (.length line))
                            (let [c (.charAt line #^int index)]
                              (or (Character/isLetterOrDigit c) 
                                  (#{\_ \! \. \? \- \/} c)))))
        symbol-start
          (loop [i index]
            (if (or (= i 0) (-> i dec symbol-char? not))
              i (recur (dec i))))
        symbol-stop
          (loop [i index]
            (if (or (= i (inc (.length line))) (not (symbol-char? (inc i))))
              i (recur (inc i))))]
    (.substring line symbol-start (min (.length line) (inc symbol-stop)))))

(defn get-current-symbol 
  "Get current (selected) symbol. Enters file ns"
  []
  (ns-resolve  (enter-file-ns) (symbol (get-current-symbol-str))))2

(defn find-last-delim [#^String t]
  (let [c (last t)]
    (cond 
        ((hash-set \) \] \} \") c)  c
        ((hash-set \( \[ \{ \") c)
          (throw (RuntimeException. 
            (str "Not a valid form ending in '" c "'")))
        :default :symbol)))

(defn indices-of [#^String t #^Character target]
  (reverse (for [[i c] (seq-utils/indexed t)
          :when (= c target)] i)))

(def matching-delims
  { \) \(
    \] \[
    \} \{
    \" \" })

(defn find-last-sexpr [#^String t]
  (let [t (.trim t)
        d (find-last-delim t)]
    #_(println "last delim: " d)
    (if (= :symbol d) 
      (get-current-symbol)
      (first
        (filter identity
           (for [i (indices-of t (matching-delims d))]
                  (let [cur (.substring t i)]
                    #_(println "search: " i " " cur)
                    (try
                      (let [forms (text-forms cur)]
                        #_(println "forms: " forms)
                        (when (= (count forms) 1)
                          (first forms)))
                      (catch Exception _ nil)))))))))

(defn get-last-sexpr
  "Get last sexpr before carret"
  []
  (find-last-sexpr (text-before-carret)))

(defn get-selected-sexpr
  "Get highlighted sexpr"
  []
  (-> "TM_SELECTED_TEXT" cake/*env* escape-str clojure.core/read-string))

(defn get-enclosing-sexpr [])