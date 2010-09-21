#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/require '[clojure.contrib.repl-utils :as ru])
(clojure.core/require '[clojure.java.io :as io])
(clojure.core/require '[clojure.repl :as repl])
(clojure.core/load-file (clojure.core/str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(textmate/attempt
 (if-let [symb (get-current-symbol)]
   (do (when-let [name (-> symb meta :name)]
         (println "<h1>Name</h1>")
         (println name))
       (when-let [arg-list-str (-> symb meta :arglists)]
         (println "<h1>Arg Lists</h1>")
         (println (textmate/htmlize (str arg-list-str))))   
       (when-let [doc-str (-> symb meta :doc)] 
         (println "<h1>Doc</h1>")
         (println (.replaceAll doc-str "\n" "<br>")))
       (when-let [symb-ns (-> symb meta :ns)]
         (println "<h1>Namespace</h1><br>"
                 (htmlize (str symb-ns))))
                  ; #_(.replaceAll
                  ;  (.replaceAll (str symb-ns) "<" "&lt;")
                   ; ">" "&rt;")
                  ; #_"<br>"))  
       ; (when-let [cur-source (with-out-str (eval-in-file-ns (repl/source-fn (symbol name)))]
       ;    (println "<h1>Source</h1>"
       ;             "<pre>"
       ;             (htmlize cur-source)
       ;             "</pre>"))                            
       (when-let [f (-> symb meta :file)]
         (println "<h1>File</h1>")
         (println
          (format "<a href=\"txmt://open?line=%s&url=file:///%s\">%s:%s</a>"
                  (-> symb meta :line)
                  f
                  f
                  (-> symb meta :line)))))
   (println "Couldn't resolve symbol: " (get-current-symbol-str) "<br>")))