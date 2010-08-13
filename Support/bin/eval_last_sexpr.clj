#!/usr/bin/env cake
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(use 'clojure.java.io)

(let  [before (text-before-carret)
       forms  (clojure.core/read-string (str "[" before "]"))
       form-to-eval (last forms)]
     (println
       (str 
          "<pre>"
          (eval-in-file-ns (clojure.core/eval form-to-eval))                    
          "</pre>")))       
       



