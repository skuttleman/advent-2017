(ns advent-2017.day-13
    (:require [advent-2017.utils.core :as u]))

(defn ^:private parse-scanner [s]
    (let [[_ depth range] (re-matches #"(\d+): (\d+)" s)]
        [(u/parse-int depth) (u/parse-int range)]))

(defn ^:private board-size [scanners]
    (->> scanners
        (keys)
        (apply max)))

(defn ^:private oscilate [size]
    (let [diff (- size 2)
          positions (+ size diff)]
        (fn [step]
            (->> positions
                (mod (+ diff step))
                (- positions)
                (- size)
                (Math/abs)))))

(def ^:private scannerless (constantly -1))

(defn ^:private build-board [scanners]
    (->> (board-size scanners)
        (inc)
        (range)
        (map (juxt identity #(if-let [size (get scanners %)]
                                 (oscilate size)
                                 scannerless)))))

(defn ^:private step-through [scanners axis [step severity] [depth f]]
    (if (= axis (f step))
        [(inc step) (+ severity (* depth (get scanners depth 0)))]
        [(inc step) severity]))

(defn ^:private prep []
    (->> (u/read-file 13 #"\n")
        (map parse-scanner)
        (into {})))

(defn ^:private get-severity [scanners axis start board]
    (->> board
        (reduce (partial step-through scanners axis) [start 0])
        (second)))

(defn ^:private severe? [axis start board]
    (->> board
        (map-indexed (fn [step [_ f]] (= axis (f (+ start step)))))
        (filter true?)
        (seq)))

;; 1640
(defn step-1 []
    (let [scanners (prep)]
        (->> scanners
            (build-board)
            (get-severity scanners 0 0))))

;; 3960702
(defn step-2 []
    (let [board (build-board (prep))]
        (loop [start 0 severe (severe? 0 0 board)]
            (if severe
                (recur (inc start) (severe? 0 (inc start) board))
                start))))
