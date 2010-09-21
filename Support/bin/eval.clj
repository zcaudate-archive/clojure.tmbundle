#!/usr/bin/env cake run
(require '[clojure.java.io :as io])
(load-file (str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(in-ns 'textmate)
(attempt 
  (clojure.core/println
      "<pre>"
      (get-selected-sexpr)
      "<br>"
      "<center>"
      (-> (get-selected-sexpr)
          clojure.core/eval          
          textmate/eval-in-file-ns          
          textmate/ppstr-nil
          textmate/htmlize
          .trim)
      "</center>"    
      "</pre>"))