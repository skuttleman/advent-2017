(ns advent-2017.day-15
    (:require [advent-2017.utils.core :as u]))

(defn ^:private generator [start factor denom]
    (->> start
        (iterate #(mod (* % factor) denom))
        (drop 1)))

(defn ^:private truncate [s len]
    (let [s-len (count s)]
        (if (> s-len len)
            (subs s (- s-len len))
            s)))

(defn ^:private num->lower-16-bin [num]
    (-> num
        (Integer/toString 2)
        (u/left-pad "0" 16)
        (truncate 16)))

(defn ^:private find-matches [seq-a seq-b sample-size]
    (loop [[a & more-a] seq-a [b & more-b] seq-b remaining sample-size matches 0]
        (if (pos? remaining)
            (let [matches' (if (= (num->lower-16-bin a) (num->lower-16-bin b))
                               (inc matches)
                               matches)]
                (recur more-a more-b (dec remaining) matches'))
            matches)))

;; 631
(defn step-1 []
    (let [generator-a (generator 873 16807 2147483647)
          generator-b (generator 583 48271 2147483647)]
        (find-matches generator-a generator-b 40000000)))

;; 279
(defn step-2 []
    (let [generator-a (filter #(u/divisible-by? % 4) (generator 873 16807 2147483647))
          generator-b (filter #(u/divisible-by? % 8) (generator 583 48271 2147483647))]
        (find-matches generator-a generator-b 5000000)))
