(ns mangrove.regression.linear
  (:require [mangrove.matrix :as m]))

(defn scale-features
  "Scale the features matrix:
   scaled-feature = (feature - min) / (max - min)"
  [features]
  (let [mu (m/mean features)
        min (m/min features)
        max (m/max features)]
    (println "mu:" mu)
    (println "min:" min)
    (println "max:" max)
    (m/div
     (m/minus features mu)
     (- max min))))
