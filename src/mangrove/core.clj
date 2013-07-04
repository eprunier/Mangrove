(ns mangrove.core
  (:require [mangrove.io :as io]
            [cerebro.core :as m]))

(defn load-matrix
  "Load a matrix from a CSV file."
  [file]
  (-> file
      io/load-csv
      m/matrix))
