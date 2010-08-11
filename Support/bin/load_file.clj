#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(let [tm-filepath (bake/*env* "TM_FILEPATH")]
  (if (not (= tm-filepath ""))
    (do
      (swap! *compiled-files* conj tm-filepath)
      (let [user-dir (str (System/getProperty "user.dir") "/src/")
            path-to-file (string/replace tm-filepath user-dir "")]
        (-> path-to-file
            (string/replace ".clj" "")
            load))
      (clojure.core/println "Compilation finished."))
    (clojure.core/println "No file.")))