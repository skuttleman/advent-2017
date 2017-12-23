(ns advent-2017.day-22
    (:require [advent-2017.utils.core :as u]
              [clojure.set :as set]))

(defn ^:private prep []
    (let [grid          (mapv vec (u/read-file 22 #"\n"))
          height        (count grid)
          width         (count (first grid))
          height-offset (/ (dec height) 2)
          width-offset  (/ (dec width) 2)]
        (->> (for [i (range height) j (range width)]
                 [i j])
            (reduce (fn [infected-grids [row col]]
                        (if (= \# (get-in grid [row col]))
                            (assoc infected-grids [(- row height-offset) (- col width-offset)] :infected)
                            infected-grids))
                {}))))

(defn ^:private move [[row col] [row' col']]
    [(+ row row') (+ col col')])

(def ^:private turn-left
    {[-1 0] [0 -1]
     [0 -1] [1 0]
     [1 0]  [0 1]
     [0 1]  [-1 0]})

(def ^:private turn-right
    (set/map-invert turn-left))

(def ^:private turn-around
    (partial mapv (partial * -1)))

(defn ^:private turn [state direction]
    (case state
        :infected (turn-right direction)
        :clean (turn-left direction)
        :flagged (turn-around direction)
        direction))

(def ^:private process-binary
    {:infected :clean
     :clean    :infected})

(def ^:private process-quaternary
    {:clean :weak
     :weak :infected
     :infected :flagged
     :flagged :clean})

(defn ^:private infect [initial-grid limit process-f]
    (loop [grid initial-grid infections 0 position [0 0] direction [-1 0] bursts 0]
        (if (= bursts limit)
            infections
            (let [state       (get grid position :clean)
                  new-state   (process-f state)
                  infections' (if (= new-state :infected) (inc infections) infections)
                  direction'  (turn state direction)
                  position'   (move position direction')]
                (recur (assoc grid position new-state) infections' position' direction' (inc bursts))))))

;; 5246
(defn step-1 []
    (-> (prep)
        (infect 10000 process-binary)))

;; 2512059
(defn step-2 []
    (-> (prep)
        (infect 10000000 process-quaternary)))
