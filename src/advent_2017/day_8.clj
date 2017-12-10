(ns advent-2017.day-8
    (:require [advent-2017.utils.core :as u]))

(def ^:private fns
    {:inc (fnil + 0 0)
     :dec (fnil - 0 0)
     :> (fnil > 0 0)
     :< (fnil < 0 0)
     :!= (fnil not= 0 0)
     :<= (fnil <= 0 0)
     :>= (fnil >= 0 0)
     :== (fnil = 0 0)})

(defn ^:private s->instruction [s]
    (let [re #"([a-z]+) (inc|dec) (-?\d+) if ([a-z]+) (>|<|!=|<=|>=|==) (-?\d+)"
          [_ reg f amt targ op val] (re-matches re s)]
        [(keyword reg) (keyword f) (u/parse-int amt)
         (keyword targ) (keyword op) (u/parse-int val)]))

(defn ^:private prep []
    (->>
        (u/read-file 8 #"\n")
        (map s->instruction)))

(def ^:private find-largest-val
    (comp (partial apply max) vals))

(defn ^:private run-instructions [registers [[reg f amt targ op val] & instructions]]
    (let [[op' f'] (map (partial get fns) [op f])
          [reg' targ'] (map (partial get registers) [reg targ])
          registers' (if (op' targ' val)
                         (update registers reg f' amt)
                         registers)]
        (if (empty? instructions)
            registers'
            (recur registers' instructions))))

;;4647
(defn step-1 []
    (->> (prep)
        (run-instructions {})
        (find-largest-val)))


(defn step-2 []
    )
