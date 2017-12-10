(ns advent-2017.day-7
    (:require [advent-2017.utils.core :as u]
              [clojure.set :as set]))

(defn ^:private parse-diagram [line]
    (let [[_ name weight _ more] (re-matches #"([a-z]+) \((\d+)\)( -> )?(.*)" line)]
        [name (u/parse-int weight) (when-not (empty? more)
                                       (u/split-n-trim #"," more))]))

(defn ^:private prep []
    (->> (u/read-file 7 #"\n")
        (map parse-diagram)))

(defn ^:private get-base [towers]
    (->> towers
        (reduce (fn [[names subs] [name _ subs']]
                    [(conj names name) (into subs subs')])
            [#{} #{}])
        (apply set/difference)
        (first)))

(defn ^:private find-tower [towers name]
    (->> towers
        (filter (comp (partial = name) first))
        (first)))

(defn ^:private get-weight [towers name]
    (let [[_ weight children] (find-tower towers name)]
        (apply + weight (map (partial get-weight towers) children))))

(defn ^:private balanced? [towers name]
    (let [[_ weight children] (find-tower towers name)]
        (if (seq children)
            (apply = (map (partial get-weight towers) children))
            true)))

(defn ^:private find-unbalanced [towers name]
    (let [[_ weight children] (find-tower towers name)
          i'm-balanced? (balanced? towers name)
          unbalanced-child (->> children
                               (map (juxt identity (partial balanced? towers)))
                               (remove second)
                               (ffirst))]
        (if (or i'm-balanced? (and (not i'm-balanced?) unbalanced-child))
            (recur towers unbalanced-child)
            (->> children
                (map (partial find-tower towers))
                (map #(assoc % 2 (get-weight towers (first %))))))))

(defn ^:private calculate-weight-diff [unbalanced-children]
    (let [[[w tw] [_ tw']] (->> unbalanced-children
                                (map rest)
                                (sort-by (comp (partial * -1) second)))]
        (- w (- tw tw'))))

;; aapssr
(defn step-1 []
    (->> (prep)
        (get-base)))

;; 1458
(defn step-2 []
    (let [towers (prep)]
        (->> towers
            (get-base)
            (find-unbalanced towers)
            (calculate-weight-diff))))
