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
    (let [[_s sp] (re-matches #"s(\d+)" s)
          [_e x-a x-b] (re-matches #"x(\d+)/(\d+)" s)
          [_p p-a p-b] (re-matches #"p([a-z]+)/([a-z]+)" s)]
        (cond
            _s #(spin % (u/parse-int sp))
            _e #(exchange % (u/parse-int x-a) (u/parse-int x-b))
            _p #(partner % p-a p-b))))

(defn ^:private ->dance []
    (->> (u/read-file 16 #",")
        (map s->fn)
        (reverse)
        (apply comp)
        (memoize)))

;; ehdpincaogkblmfj
(defn step-1 []
    (let [dance (->dance)]
        (dance (apply str (take 16 letters)))))

;; bpcekomfgjdlinha
(defn step-2 []
    (let [dance (->dance)]
        (loop [dancers (apply str (take 16 letters)) remaining 1000000000]
            (if (pos? remaining)
                (recur (dance dancers) (dec remaining))
                dancers))))
