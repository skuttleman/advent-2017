(ns advent-2017.day-4
    (:require [advent-2017.utils.core :as u]))

(defn ^:private unique-pws? [pws]
    (= (count pws) (count (distinct pws))))

(defn ^:private no-anagrams? [pws]
    (->> pws
        (map sort)
        (distinct)
        (count)
        (= (count pws))))

(defn ^:private count-valid [filter-fn]
    (->> (u/read-file 4 #"\n")
        (map (partial u/split-n-trim #"\W"))
        (filter filter-fn)
        (count)))

;; 325
(defn step-1 []
    (count-valid unique-pws?))

;; 119
(defn step-2 []
    (count-valid no-anagrams?))
