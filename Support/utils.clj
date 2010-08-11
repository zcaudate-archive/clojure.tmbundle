(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])

(defn enter-file-ns []
  (let [tm-filepath (bake/*env* "TM_FILEPATH")]
    (if (not (=  tm-filepath ""))
      (let [user-dir (str (System/getProperty "user.dir") "/src/")
            path-to-file (string/replace tm-filepath user-dir "")]
        (-> path-to-file
            (string/replace ".clj" "")
            (string/replace "_" "-")
            (string/replace "/" ".")
            symbol
            in-ns))
       (in-ns 'user))))