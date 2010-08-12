#!/usr/bin/env cake
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(enter-file-ns true) ; if file not loaded enter user ns

(clojure.core/println
  (clojure.core/str 
       "<pre>"
       (clojure.core/eval (clojure.core/read-string (bake/*env* "TM_SELECTED_TEXT")))
       "</pre>"))