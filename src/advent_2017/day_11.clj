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

(defn calculate-distance [pos]
    (->> pos
        (vals)
        (map #(Math/abs %))
        (reduce + 0)
        (int)))

(defn calculate-position [pos max-dist [move & moves]]
    (let [pos' (move-to pos move)
          max-dist' (max max-dist (calculate-distance pos'))]
        (if (empty? moves)
            [pos' max-dist']
            (recur pos' max-dist' moves))))

(defn prep []
    (->> (u/read-file 11 #",")
        (map keyword)))

;; 796
(defn step-1 []
    (->> (prep)
        (calculate-position {:x 0 :y 0} 0)
        (first)
        (calculate-distance)))

;; 1585
(defn step-2 []
    (->> (prep)
        (calculate-position {:x 0 :y 0} 0)
        (second)))
