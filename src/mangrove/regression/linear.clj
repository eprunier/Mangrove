(ns mangrove.regression.linear
  (:require [cerebro.core :as m]
            [cerebro.ops :as mo]))

#_(defn scale-mean
    "Scales the matrix based on min/max : (X - means) / (max - min)
   and returns a clojure vector containing:
      * normalized X
      * means
      * (max - min)"
    [X]
    (let [mu (mo/mean X)
          min (mo/min X)
          max (mo/max X)
          sigma (mo/sub (mo/max X)
                        (mo/min X))
          X-norm (mo/ediv (mo/sub X mu)
                          sigma)]
      [X-norm mu sigma]))

(defn scale-std
  "Scales the features matrix based on standard derivation : (X - means) / std
   and returns a clojure vector containing:
      * normalized X
      * means
      * standard derivation"
  [X]
  (let [mu (mo/mean X)
        sigma (mo/std X)
        X_norm (mo/ediv (mo/sub X mu)
                        sigma)]
    [X_norm mu sigma]))