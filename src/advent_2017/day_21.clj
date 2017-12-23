(ns advent-2017.day-21
    (:refer-clojure :exlcude [flatten])
    (:require [advent-2017.utils.core :as u]
              [advent-2017.utils.matrix :as m]))

(defn spy [s] (println s) s)

(def ^:private pattern ".#./..#/###")

(def ^:private flatten-matrix
    (partial m/flatten "/" ""))

(def ^:private expand-matrix
    (partial m/expand #"/" #""))

(defn ^:private s->rule [s]
    (let [[_ rule result] (re-matches #"(\S+) => (\S+)" s)]
        #(when (= % rule) result)))

(defn ^:private find-match [rules match matrix]
    (or match
        (let [flattened-matrix (flatten-matrix matrix)]
            (->> rules
                (map #(% flattened-matrix))
                (remove nil?)
                (first)))))

(defn ^:private apply-rules [rules matrix]
    (->> matrix
        ((juxt identity
             m/flip
             m/rotate
             (comp m/rotate m/flip)
             (comp m/rotate m/rotate)
             (comp m/rotate m/rotate m/flip)
             (comp m/rotate m/rotate m/rotate)
             (comp m/rotate m/rotate m/rotate m/flip)))
        (reduce (partial find-match rules) nil)
        (expand-matrix)))

(defn ^:private split-and-expand [matrix rules]
    (let [size (count matrix)
          matrices (cond
                       (<= size 3) [matrix]
                       (u/divisible-by? size 2) (m/split 2 matrix)
                       :else (m/split 3 matrix))]
        (->> matrices
            (map (partial apply-rules rules))
            (m/join))))

(defn ^:private prep []
    (->> (u/read-file 21 #"\n")
        (map s->rule)))

;; 190
(defn step-1 []
    (let [rules (prep)]
        (loop [matrix (expand-matrix pattern) iterations 0]
            (if (= iterations 5)
                (->> matrix (flatten-matrix) (filter #{\#}) (count))
                (recur (split-and-expand matrix rules) (inc iterations))))))


(defn step-2 []
    )
