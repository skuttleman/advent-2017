(ns advent-2017.utils.core
    (:require [clojure.string :as s]))

(defn parse-int [value]
    (Integer/parseInt (str value)))

(def divisible-by?
    (comp zero? mod))

(defn split-n-trim [re input]
    (->> (s/split input re)
        (map s/trim)
        (remove empty?)))

(defn read-file [day re]
    (->> (slurp (str "resources/day" day ".txt"))
        (split-n-trim re)
        (map s/trim)
        (remove empty?)))

(defn s->ascii [s]
    (int (.charAt (str s) 0)))

(defn pad [s padder min-len]
    (if (>= (count s) min-len)
        s
        (recur (padder s) padder min-len)))

(defn left-pad [s pad-char min-len]
    (pad s #(str pad-char %) min-len))

(defn right-pad [s pad-char min-len]
    (pad s #(str % pad-char) min-len))
