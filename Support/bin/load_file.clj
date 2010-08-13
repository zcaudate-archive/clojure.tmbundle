#!/usr/bin/env cake
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(let [tm-filepath (bake/*env* "TM_FILEPATH")]
  (if (not (= tm-filepath ""))
    (do
      (swap! *compiled-files* conj tm-filepath)
      (load-file tm-filepath)
      (clojure.core/println "Compilation finished."))
    (clojure.core/println "No file.")))