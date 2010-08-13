#!/usr/bin/env cake
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(load-file (str (io/file (bake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))
(use 'clojure.java.io)

(println "#After: " (text-before-carret) "#")
(println "#Form: "  (text-forms (text-after-carret)))

(println
 (str 
   "<pre>"
   (eval-in-file-ns (clojure.core/eval (get-last-sexpr)))                    
   "</pre>"))       
       



