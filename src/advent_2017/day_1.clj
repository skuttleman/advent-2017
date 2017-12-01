(ns advent-2017.day-1
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

;; 1228
(defn step-1 []
    (let [digits (->> (u/read-file 1 #"")
                     (map s/trim)
                     (remove empty?)
                     (map #(Integer/parseInt %)))
          digits' (concat (rest digits) [(first digits)])]
        (->> [digits digits']
            (apply map vector)
            (filter (partial apply =))
            (map first)
            (reduce +))))

