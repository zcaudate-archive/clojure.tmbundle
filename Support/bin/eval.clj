#!/usr/bin/env cake run
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(in-ns 'textmate)
(doseq [form (get-selected-sexprs)] (display-form-eval form))
