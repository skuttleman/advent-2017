(ns advent-2017.day-20
    (:require [advent-2017.utils.core :as u]))

(defprotocol IDist
    (dist [this]))

(defprotocol IMove
    (move [this]))

(defprotocol IAdd
    (add [this other]))

(defrecord Pt [x y z]
    IDist
    (dist [_]
        (->> [x y z]
            (map #(Math/abs %))
            (reduce + 0)))
    IAdd
    (add [_ other]
        (->Pt (+ x (:x other))
            (+ y (:y other))
            (+ z (:z other)))))

(defrecord Particle [position velocity acceleration]
    IDist
    (dist [_]
        (dist position))
    IMove
    (move [_]
        (let [velocity' (add velocity acceleration)]
            (->Particle (add position velocity')
                velocity'
                acceleration))))

(defn ^:private s->particle [s]
    (->> s
        (re-matches #"p=<(-?\d+),(-?\d+),(-?\d+)>, v=<(-?\d+),(-?\d+),(-?\d+)>, a=<(-?\d+),(-?\d+),(-?\d+)>")
        (rest)
        (map u/parse-int)
        (partition 3)
        (map (partial apply ->Pt))
        (apply ->Particle)))

(defn ^:private find-closest [particles]
    (->> particles
        (reduce (fn [[i p] [i' p']]
                    (if (< (dist p) (dist p'))
                        [i p]
                        [i' p'])))))

(defn ^:private trim-down [particles]
    (let [particles' (->> particles
                         (map (fn [[i p]] (let [p' (move p)]
                                          (when (< (dist p') (dist p))
                                              [i p']))))
                         (remove nil?))]
        (if (empty? particles')
            [(find-closest particles)]
            particles')))

(defn ^:private prep []
    (->> (u/read-file 20 #"\n")
        (map s->particle)
        (map-indexed vector)))

(defn ^:private move-times [n particles]
    (if (pos? n)
        (recur (dec n) (mapv (fn [[i p]] [i (move p)]) particles))
        particles))

;; 258
(defn step-1 []
    (loop [particles (move-times 4500 (prep))]
        (let [c (count particles)]
            (if (> c 1)
                (recur (trim-down particles))
                (first particles)))))


(defn step-2 []
    )
