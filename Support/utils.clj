(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])

(defonce *compiled-files* (atom #{}))

(defn filepath->ns-str [path]
  (-> path
      (string/replace ".clj" "")
      (string/replace "_" "-")
      (string/replace "/" ".")))

(defn enter-file-ns 
  ([] (enter-file-ns false))
  ([ensure-loaded]
    (let [tm-filepath (bake/*env* "TM_FILEPATH")]
      (if (or (not ensure-loaded) (some @*compiled-files* #{tm-filepath}))
        (let [user-dir (str (System/getProperty "user.dir") "/src/")
              path-to-file (string/replace tm-filepath user-dir "")]
          (in-ns (symbol (filepath->ns-str path-to-file))))
        (in-ns 'user)))))
        
(defn get-last-sexpr [])
(defn get-enclosing-sexpr [])
(defn get-current-symbol [])