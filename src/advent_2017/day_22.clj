(ns advent-2017.day-22
    (:require [advent-2017.utils.core :as u]
              [clojure.set :as set]))

(defn ^:private prep []
    (let [grid (->> (u/read-file 22 #"\n")
                   (mapv vec))
          height (count grid)
          width (count (first grid))
          height-offset (/ (dec height) 2)
          width-offset (/ (dec width) 2)]
        (->> (for [i (range height) j (range width)]
                 [i j])
            (reduce (fn [infected-grids [row col]]
                        (if (= \# (get-in grid [row col]))
                            (assoc infected-grids [(- row height-offset) (- col width-offset)] true)
                            infected-grids))
                {}))))

(defn ^:private move [[row col] [row' col']]
    [(+ row row') (+ col col')])

(def ^:private turn-left
    {[-1 0] [0 -1]
     [0 -1] [1 0]
     [1 0] [0 1]
     [0 1] [-1 0]})

(def ^:private turn-right
    (set/map-invert turn-left))

(defn ^:private infect [initial-grid limit]
    (loop [grid initial-grid infections 0 position [0 0] direction [-1 0] bursts 0]
        (if (= bursts limit)
            infections
            (let [infected?   (get grid position)
                  infections' (if infected? infections (inc infections))
                  direction'  (if infected? (turn-right direction) (turn-left direction))
                  position' (move position direction')]
                (recur (update grid position not) infections' position' direction' (inc bursts))))))

;; 5246
(defn step-1 []
    (-> (prep)
        (infect 10000)))


(defn step-2 []
    )
