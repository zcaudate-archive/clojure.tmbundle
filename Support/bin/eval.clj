#!/usr/bin/env cake run
(in-ns 'textmate)
(clojure.core/refer 'clojure.core)
(require '[clojure.string :as string])
(load-file (file. (bake/*env* "TM_FILEPATH") "utils.clj"))
(entire-file-ns)

(clojure.core/require '[clojure.pprint :as pprint])
(clojure.core/println
  (clojure.core/str 
       "<pre>"
       ${TM_SELECTED_TEXT:-$TM_CURRENT_LINE}
       "</pre>"))