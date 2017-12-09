(ns advent-2017.day-6
    (:require [advent-2017.utils.core :as u]))

(defn spy [v] (println v) v)

(defn max-pos [banks]
    (let [length (count banks)]
        (loop [pos 0 max-pos 0 max-val (get banks 0)]
            (cond
                (= pos length) max-pos
                (> (get banks pos) max-val) (recur (inc pos) pos (get banks pos))
                :else (recur (inc pos) max-pos max-val)))))

(defn redistribute [banks]
    (let [pos (max-pos banks)
          length (count banks)]
        (loop [pos' pos blocks (get banks pos) banks' (assoc banks pos 0)]
            (cond
                (zero? blocks) banks'
                (= length (inc pos')) (recur 0 (dec blocks) (update banks' 0 inc))
                :else (recur (inc pos') (dec blocks) (update banks' (inc pos') inc))))))

(defn run-and-track [banks history counter]
    (let [next     (redistribute banks)
          counter' (inc counter)]
        (if (contains? history next)
            counter'
            (recur next (conj history next) counter'))))

(defn ^:private prep []
    (->> (u/read-file 6 #"\s")
        (mapv u/parse-int)))

;; 11137
(defn step-1 []
    (let [initial (prep)]
        (run-and-track initial #{initial} 0)))


(defn step-2 []
    )
