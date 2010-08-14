#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.java.io :as io])
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(textmate/attempt
  (println
    (str
      "<pre>"
      (eval-in-file-ns (clojure.core/eval (get-last-sexpr)))
      "</pre>")))