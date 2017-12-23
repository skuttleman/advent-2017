(ns advent-2017.utils.matrix
    (:refer-clojure :exclude [flatten])
    (:require [clojure.string :as s]))

(def flip
    "Flips a 2d seq on the y axis"
    (partial map reverse))

(defn rotate [matrix]
    "Rotates a 2d seq 90° to the right."
    (->> matrix
        (reduce (fn [result row]
                    (->> row
                        (map-indexed (fn [col result']
                                         (concat [result'] (get (vec result) col []))))))
            [])))

(defn flatten [row-join col-join matrix]
    "Joins a 2d seq into a string separating rows with `row-join` and cols with `col-join`."
    (->> matrix
        (map (partial s/join col-join))
        (s/join row-join)))

(defn expand [row-re col-re s]
    "Splits a string into a 2d seq splitting rows by row-re and cols by col-re"
    (->> (s/split s row-re)
        (map #(s/split % col-re))))

(defn split [n matrix]
    "Splits a matrix (whose grid is evenly divisible by n) into a
    seq of matrices of size n."
    (let [size (/ (count matrix) n)]
        (for [i (range size) j (range size)]
            (->> matrix
                (map (comp (partial take n) (partial drop (* j n))))
                (drop (* i n))
                (take n)))))

(defn join [matrices]
    "Joins matrices of the same size into a single matrix where
    the size of the new matrix sqrt of the number of matrices times
    the size of each inner matrix."
    (let [sqrt (int (Math/sqrt (count matrices)))
          inner-size (count (first matrices))
          size (* inner-size sqrt)]
        (for [i (range sqrt) j (range inner-size)]
            (->> matrices
                (mapcat #(nth % j))
                (drop (* i size))
                (take size)))))
