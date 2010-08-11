#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(require '[clojure.java.io :as io])
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(enter-file-ns true) ; if file not loaded enter user ns

(require '[clojure.pprint :as pprint])
(require '[clojure.contrib.macro-utils :as macro-utils])
(clojure.core/println
  (clojure.core/str 
     "<pre>" 
     (pprint/with-pprint-dispatch pprint/code-dispatch
       (binding [pprint/*print-suppress-namespaces* true]
        (pprint/write (macro-utils/mexpand-all (read-string (bake/*env* "TM_SELECTED_TEXT")))
          :pretty true :stream nil)))
     "</pre>"))