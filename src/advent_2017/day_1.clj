(ns advent-2017.day-1
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

(defn ^:private count-em [digits-fn]
    (let [digits (->> (u/read-file 1 #"")
                     (map s/trim)
                     (remove empty?)
                     (map u/parse-int))
          digits' (digits-fn digits)]
        (->> [digits digits']
            (apply map vector)
            (filter (partial apply =))
            (map first)
            (reduce +))))

(defn ^:private send-n-to-end [list n]
    (concat (drop n list) (take n list)))

;; 1228
(defn step-1 []
    (count-em #(send-n-to-end % 1)))

;; 1238
(defn step-2 []
    (count-em #(send-n-to-end % (/ (count %) 2))))
