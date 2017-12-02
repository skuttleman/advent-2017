(ns advent-2017.day-2
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

(defn ^:private prep []
    (->>
        (u/read-file 2 #"\n")
        (map (comp (partial map u/parse-int) (partial u/split-n-trim #"\W")))))

;; 50376
(defn step-1 []
    (->> (prep)
        (map (fn [values] (- (apply max values) (apply min values))))
        (reduce +)))
