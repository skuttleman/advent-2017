(ns advent-2017.day-2
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

(defn ^:private prep []
    (->>
        (u/read-file 2 #"\n")
        (map (comp (partial map u/parse-int) (partial u/split-n-trim #"\W")))))

(defn ^:private diff-max-min [values]
    (- (apply max values) (apply min values)))

(defn ^:private find-divis [values]
    (for [num values denom values
          :when (and (> num denom) (= 0 (mod num denom)))]
        (/ num denom)))

;; 50376
(defn step-1 []
    (->> (prep)
        (map diff-max-min)
        (reduce +)))

;; 267
(defn step-2 []
    (->> (prep)
        (map (comp first find-divis))
        (reduce +)))
