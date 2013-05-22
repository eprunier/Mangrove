(ns mangrove.io
  (:require [clojure.tools.logging :as log]
            [clojure.java.io :as io]
            [clojure.data.csv :as csv]))

(defn- auto-detect-type
  "Try to auto convert numeric values."
  [^String value]
  (try
    (Integer/valueOf value)
    (catch Exception e
      (try
        (Long/valueOf value)
        (catch Exception e 
          (try
            (Double/valueOf value)
            (catch Exception e
              value)))))))

(defn- apply-types
  "Convert each line entries according to the types vector."
  [line types]
  (reduce #(let [^String entry-key (key %2)
                 ^String entry-val (val %2)] 
             (-> (condp = entry-key
                   :int (Integer/valueOf entry-val)
                   :long (Long/valueOf entry-val)
                   :double (Double/valueOf entry-val)
                   :other entry-val))
             (cons %)
             vec) 
          [] 
          (zipmap types line)))

(defn- process-types
  "Process data types. Converts line entries according to
   the types vector, or by auto-detection (supported types
   are :int, :long, :double and :other)."
  ([types data]
     (if (seq types)
       (map #(apply-types % types) data)
       (for [line data] 
         (vec (map auto-detect-type line))))))

(defn- not-empty-line?
  [line]
  (not (and (= (count line) 1)
            (= 0 (-> line first count)))))

;;;
;;; Raw datasets
;;;
(defn load-csv
  "Load CSV file"
  ([filename]
     (load-csv filename []))
  ([filename types]
     (with-open [file (io/reader filename)]
       (->> file 
            slurp
            csv/read-csv
            (filter not-empty-line?)
            (process-types types)))))