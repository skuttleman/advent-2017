(ns advent-2017.day-11
    (:require [advent-2017.utils.core :as u]))

(defn move-to [{:keys [x y]} move]
    (case move
        :n {:x x :y (dec y)}
        :s {:x x :y (inc y)}
        :nw {:x (- x 0.5) :y (- y 0.5)}
        :ne {:x (+ x 0.5) :y (- y 0.5)}
        :sw {:x (- x 0.5) :y (+ y 0.5)}
        :se {:x (+ x 0.5) :y (+ y 0.5)}
        {:x x :y x}))

(defn calculate-position [pos [move & moves]]
    (let [pos' (move-to pos move)]
        (if (empty? moves)
            pos'
            (recur pos' moves))))

(defn prep []
    (->> (u/read-file 11 #",")
        (map keyword)))

;; 796
(defn step-1 []
    (->> (prep)
        (calculate-position {:x 0 :y 0})
        (vals)
        (map #(Math/abs %))
        (reduce + 0)
        (int)))


(defn step-2 []
    )
