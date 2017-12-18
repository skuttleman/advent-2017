(ns advent-2017.day-17
    (:require [advent-2017.utils.core :as u]))

(defn ^:private step-forward [len pos steps]
    (mod (+ pos steps) len))

(defn ^:private insert [coll pos value]
    (concat (take pos coll) [value] (drop pos coll)))

;; 136
(defn step-1 []
    (let [steps 363 max-value 2017]
        (loop [coll [0] pos 0 next-value 1]
            (if (> next-value max-value)
                (let [pos' (inc (step-forward (count coll) pos (* -1 steps)))]
                    (first (drop pos' coll)))
                (let [pos'  (inc pos)
                      coll' (insert coll pos' next-value)]
                    (recur coll'
                        (step-forward (count coll') pos' steps)
                        (inc next-value)))))))

;; 1080289
(defn step-2 []
    (let [steps 363 max-value 50000000]
        (loop [second-val nil size 1 pos 0 next-value 1]
            (if (> next-value max-value)
                second-val
                (let [size' (inc size)
                      pos' (step-forward size' (inc pos) steps)]
                    (recur (if (zero? pos) next-value second-val)
                        (inc size)
                        pos'
                        (inc next-value)))))))
