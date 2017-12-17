(ns advent-2017.day-14
    (:require [advent-2017.day-10 :as day-10]
              [advent-2017.utils.core :as u]
              [clojure.string :as string]))

(def c->binary
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

(defn s->grid [s]
    (->> (range 128)
        (map (comp (partial mapcat c->binary) day-10/knot-hash (partial str s "-")))))

;; 8216
(defn step-1 []
    (->> "nbysizxe"
        (s->grid)
        (flatten)
        (filter (partial = \1))
        (count)))


(defn step-2 []
    )
