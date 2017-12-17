(ns advent-2017.day-16
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

(def ^:private letters
    (iterate (fn [l] (if (= \z l) \a (char (inc (int l))))) \a))

(defn ^:private spin [s len]
    (let [diff (- (count s) len)]
        (str (subs s diff) (subs s 0 diff))))

(defn ^:private exchange [s idx-1 idx-2]
    (let [idx-1' (min idx-1 idx-2)
          idx-2' (max idx-1 idx-2)
          c1 (.charAt s idx-1')
          c2 (.charAt s idx-2')]
        (str (u/sub-str s 0 idx-1')
            c2
            (u/sub-str s (inc idx-1') idx-2')
            c1
            (u/sub-str s (inc idx-2')))))

(defn ^:private partner [s char-1 char-2]
    (let [idx-1 (s/index-of s char-1)
          idx-2 (s/index-of s char-2)]
        (exchange s idx-1 idx-2)))

(defn ^:private s->fn [s]
    (let [[_spin sp] (re-matches #"s(\d+)" s)
          [_exchange x-a x-b] (re-matches #"x(\d+)/(\d+)" s)
          [_partner p-a p-b] (re-matches #"p([a-z]+)/([a-z]+)" s)]
        (cond
            _spin [spin [(u/parse-int sp)]]
            _exchange [exchange [(u/parse-int x-a) (u/parse-int x-b)]]
            _partner [partner [p-a p-b]])))

(defn ^:private prep []
    (->> (u/read-file 16 #",")
        (map s->fn)))

;; ehdpincaogkblmfj
(defn step-1 []
    (let [dancers (apply str (take 16 letters))]
        (->> (prep)
            (reduce (fn [s [f args]] (apply f s args)) dancers))))


(defn step-2 []
    )
