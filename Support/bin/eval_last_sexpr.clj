#!/usr/bin/env cake
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(use 'clojure.java.io)

(let  [before (text-before-carret)
       forms  (clojure.core/read-string (str "[" before "]"))
       form (last forms)]
   (eval-in-file-ns
     (clojure.core/println
       (clojure.core/str 
          "<pre>"
             (clojure.core/eval form))
          "</pre>")))       
       



