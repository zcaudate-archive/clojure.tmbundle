#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])

(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(enter-file-ns)

(clojure.core/require '[clojure.pprint :as pprint])
(clojure.core/println
  (clojure.core/str 
       "<pre>"
       (eval (read-string (bake/*env* "TM_SELECTED_TEXT")))
       "</pre>"))