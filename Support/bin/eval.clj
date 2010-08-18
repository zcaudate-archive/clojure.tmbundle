#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(textmate/attempt
  (clojure.core/println
    (clojure.core/str 
      "<pre>"
      (textmate/eval-in-file-ns (clojure.core/eval (get-selected-sexpr)))
      "</pre>")))