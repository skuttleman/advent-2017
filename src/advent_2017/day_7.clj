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

;; aapssr
(defn step-1 []
    (->> (prep)
        (get-base)))


(defn step-2 []
    (let [towers (prep)
          base (get-base towers)]))
