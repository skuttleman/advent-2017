(ns advent-2017.utils.core
    (:require [clojure.string :as s]))

(defn read-file [day re]
    (-> (str "resource/day" day ".txt")
        (slurp)
        (s/split re)))
