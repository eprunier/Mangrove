(ns mangrove.matrix
  (:refer-clojure :exclude [max min])
  (:require [clojure.core.matrix :as m]
            [clojure.core.matrix.operators :as mo]))

(m/set-current-implementation :vectorz)

(defn zeros
  "Create a square matrix if size is provided, or a rows x cols matrix."
  ([size]
     (m/new-matrix size size))
  ([rows cols]
     (m/new-matrix rows cols)))

(defn matrix
  "Create a matrix"
  [& more]
  (m/matrix more))

(defn- apply-to-cols
  "Apply the function f to each column of the x matrix,
   or to the row if x is a one row matrix."
  [f x]
  (if (m/row-matrix? x)
    (f (first x))
    (map f (m/transpose x))))

(defn max
  "Return the maximum values for each column of the x matrix,
   or the maximum value for the row if x is a one row matrix."
  [x]
  (matrix
   (apply-to-cols #(apply clojure.core/max %) 
                  x)))

(defn min
  "Return the minimum values for each column of the x matrix,
   or the minimum value for the row if x is a one row matrix."
  [x]
  (matrix
   (apply-to-cols #(apply clojure.core/min %) 
                  x)))

(defn mean
  "Return the mean values for each column of the x matrix,
   or the mean value for the row if x is a one row matrix."
  [x]
  (matrix (apply-to-cols #(/ (apply + %)
                             (count (seq %))) 
                         x)))

(defn- subtract
  "Subtract y from the matrix x."
  [x y]
  (if (m/matrix? y)
    (m/sub x y)
    (m/emap #(- % y) x)))

(defn minus
  "Subtract element-wise items from the x matrix.
   Examples: 
     (minus (matrix [10 20 30]) 
            (matrix [5 5 5])
            (matrix [1 1 1])) 
     => #<Matrix [[0.0,10.0,20.0]]>

     (minus (matrix [10 20 30]) 5 5)
     => #<Matrix [[0.0,10.0,20.0]]>"
  [x & items]
  (reduce subtract x items))

(defn div
  "Divide x by the dividers."
  [x & dividers]
  (apply m/div x dividers))

(defn normalize
  [x]
  (m/normalise x))
