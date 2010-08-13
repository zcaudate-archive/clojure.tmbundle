(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])

(defonce *compiled-files* (atom #{}))

(defn filepath->ns-str [path]
  (-> path
      (string/replace ".clj" "")
      (string/replace "_" "-")
      (string/replace "/" ".")))

(defn text-forms [t]
  (read-string (str "[" t "]")))
      
(defn file-ns []
  (let [forms (-> (bake/*env* "TM_FILEPATH") slurp text-forms)
        [ns-fn ns] (first (for [f forms :when (and (seq? f) (#{"ns" "in-ns"} (str (first f))))] 
                    [(first f) (second f)]))]                                                          
    (if ns  
      (if (= (str ns-fn) "ns") ns (eval ns))
      'user)))
      
(defn enter-ns [ns]
  #_(println (str "Entering " ns))
  (in-ns ns))      
        
(defn enter-file-ns []
  (let [ns (file-ns)]
    (enter-ns ns)))     
    
(defmacro eval-in-file-ns [& forms]
  `(let [old-ns# *ns*]
    (enter-file-ns)
    (let [r# ~@forms]
      (enter-ns (-> old-ns# str symbol))
      r#)))       
      
      
(defn project-relative-src-path []  
   (let [user-dir (str (bake/*env* "TM_PROJECT_DIRECTORY") "/src/")
        path-to-file (string/replace (bake/*env* "TM_FILEPATH")  user-dir "")]
  path-to-file))      
    
        
(defn carret-info 
  "returns [path line-index column-index] info
   about current location of cursor"
  []
  [(bake/*env* "TM_FILEPATH")
    (dec (Integer/parseInt (bake/*env* "TM_LINE_NUMBER")))
    (Integer/parseInt (bake/*env* "TM_LINE_INDEX"))])      

(defn text-before-carret []
  (let [[path,line-index,column-index] (carret-info)
        lines (-> path io/reader line-seq)]
     (apply str 
       (apply str (for [l (take line-index lines)] (str l "\n")))
       (.substring #^String (nth lines line-index) 0 column-index))))    
        
        
(defn get-last-sexpr [])
(defn get-enclosing-sexpr [])
(defn get-current-symbol [])