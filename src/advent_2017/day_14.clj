(ns advent-2017.day-14
    (:require [advent-2017.day-10 :as day-10]
              [advent-2017.utils.core :as u]
              [clojure.string :as string]))

(def ^:private c->binary
    {\0 "0000"
     \1 "0001"
     \2 "0010"
     \3 "0011"
     \4 "0100"
     \5 "0101"
     \6 "0110"
     \7 "0111"
     \8 "1000"
     \9 "1001"
     \a "1010"
     \b "1011"
     \c "1100"
     \d "1101"
     \e "1110"
     \f "1111"})

(defn ^:private s->grid [s]
    (->> (range 128)
        (map (comp (partial mapcat c->binary) day-10/knot-hash (partial str s "-")))))

(defn ^:private fill [grid row col]
    (if (or (neg? row) (neg? col) (>= row 128) (>= col 128) (= \0 (get-in grid [row col])))
        grid
        (-> grid
            (assoc-in [row col] \0)
            (fill (dec row) col)
            (fill (inc row) col)
            (fill row (dec col))
            (recur row (inc col)))))

;; 8216
(defn step-1 []
    (->> "nbysizxe"
        (s->grid)
        (flatten)
        (filter (partial = \1))
        (count)))

;; 1139
(defn step-2 []
    (loop [grid (mapv vec (s->grid "nbysizxe")) row 0 col 0 groups 0]
        (cond
            (= 128 col) (recur grid (inc row) 0 groups)
            (= 128 row) groups
            (= \1 (get-in grid [row col])) (recur (fill grid row col) row (inc col) (inc groups))
            :else (recur grid row (inc col) groups))))
