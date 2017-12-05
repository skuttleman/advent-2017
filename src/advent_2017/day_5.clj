(ns advent-2017.day-5
    (:require [advent-2017.utils.core :as u]))

(defn process [updater program]
    (let [length (count program)]
        (loop [position 0 moves 0 program' program]
            (if (or (>= position length) (neg? position))
                moves
                (recur (+ position (get program' position))
                    (inc moves)
                    (update program' position updater))))))

(defn ^:private parse-n-process [updater]
    (->> (u/read-file 5 #"\n")
        (mapv u/parse-int)
        (process updater)))

;; 381680
(defn step-1 []
    (parse-n-process inc))

;; 29717847
(defn step-2 []
    (parse-n-process #(if (>= % 3) (dec %) (inc %))))
