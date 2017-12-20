(ns advent-2017.day-19
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

(defn ^:private find-start [grid]
    (->> grid
        (first)
        (map-indexed vector)
        (remove (comp empty? s/trim second))
        (ffirst)
        (conj [0])))

(def ^:private invert
    {[0 1] [0 -1]
     [0 -1] [0 1]
     [1 0] [-1 0]
     [-1 0] [1 0]})

(def ^:private directions
    [[0 1] [0 -1] [1 0] [-1 0]])

(defn ^:private combine-positions [[a b] [a' b']]
    [(+ a a') (+ b b')])

(defn ^:private mismatched? [direction cell]
    (if (#{[0 1] [0 -1]} direction)
        (#{"|"} cell)
        (#{"-"} cell)))

(defn ^:private turn [grid position direction]
    (->> directions
        (map (juxt identity (comp (partial get-in grid) (partial combine-positions position))))
        (remove (fn [[direction' cell]]
                     (or (empty? (s/trim (str cell)))
                         (= direction' (invert direction))
                         (mismatched? direction' cell))))
        (ffirst)))

(defn ^:private get-move [grid position direction]
    (let [cell (get-in grid position)]
        (cond
            (= "+" cell) (turn grid position direction)
            (empty? (s/trim cell)) nil
            :else direction)))

(defn ^:private update-letters [grid position letters]
    (let [letter (get-in grid position)]
        (if (re-matches #"[A-z]" letter)
            (str letters letter)
            letters)))

(defn ^:private prep []
    (->> (s/split (slurp "resources/day19.txt") #"\n")
        (mapv (comp vec #(s/split % #"")))))

;; SXWAIBUZY
(defn step-1 []
    (let [grid (prep)]
        (loop [position (find-start grid) direction [1 0] letters ""]
            (if-let [move (get-move grid position direction)]
                (recur (combine-positions position move) move (update-letters grid position letters))
                letters))))


(defn step-2 []
    )

