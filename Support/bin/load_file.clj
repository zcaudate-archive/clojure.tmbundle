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
      ;(clojure.core/println "<pre>Compiling...")
      (try 
        (do
          (load-file tm-filepath)
          (clojure.core/println "<pre>Compilation finished.</pre>"))
        (catch Exception e
          (do 
            (textmate/print-stack-trace e)
            (clojure.core/println "</pre>")))))
    (clojure.core/println "No file.")))