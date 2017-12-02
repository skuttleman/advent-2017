(ns advent-2017.utils.core
    (:require [clojure.string :as s]))

(defn parse-int [value]
    (Integer/parseInt (str value)))

(defn split-n-trim [re input]
    (->> (s/split input re)
        (map s/trim)
        (remove empty?)))

(defn read-file [day re]
    (->> (slurp (str "resources/day" day ".txt"))
        (split-n-trim re)
        (map s/trim)
        (remove empty?)))
