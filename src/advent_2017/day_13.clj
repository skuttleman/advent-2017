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
    (let [values (cycle (concat (range size) (reverse (map inc (range (- size 2))))))]
        (fn [step] (first (drop step values)))))

(defn ^:private build-board [scanners]
    (->> (board-size scanners)
        (inc)
        (range)
        (map (fn [depth]
                 (if-let [range (get scanners depth)]
                     [depth (oscilate range)]
                     [depth (constantly -1)])))))

(defn ^:private step-through [scanners axis [step severity] [depth f]]
    (if (= axis (f step))
        [(inc step) (+ severity (* depth (get scanners depth 0)))]
        [(inc step) severity]))

(defn ^:private prep []
    (->> (u/read-file 13 #"\n")
        #_(u/split-n-trim #"\n" "0: 3\n1: 2\n4: 4\n6: 4")
        (map parse-scanner)
        (into {})))

;; 1640
(defn step-1 []
    (let [scanners (prep)]
        (->> scanners
            (build-board)
            (reduce (partial step-through scanners 0) [0 0])
            (second))))


(defn step-2 []
    )
