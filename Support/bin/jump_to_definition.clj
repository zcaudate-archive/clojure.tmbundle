#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/require '[clojure.contrib.repl-utils :as ru])
(clojure.core/require '[clojure.java.io :as io])
(clojure.core/load-file (clojure.core/str (io/file (cake/*env* "TM_BUNDLE_SUPPORT") "utils.clj")))

(when-let [symb	 (get-current-symbol)]
	(when-let [{:keys [file line]} (meta symb)]
		(println (format "txmt://open?line=%s&url=file:///%s" 
								line 
								(if (.startsWith file "/") file (str (cake/*env* "TM_PROJECT_DIRECTORY") "/src/" file))))))
