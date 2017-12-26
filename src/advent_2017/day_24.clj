(ns advent-2017.day-24
    (:require [advent-2017.utils.core :as u]))

(defn ^:private s->port [s]
    (let [[_ a b] (re-matches #"(\d+)/(\d+)" s)]
        [(u/parse-int a) (u/parse-int b)]))

(defn ^:private connectable? [bridge [a b]]
    (let [[a' b'] (get bridge (dec (count bridge)))]
        (or (= b' a) (= b' b))))

(defn ^:private connectables [bridge ports]
    (->> ports
        (filter (partial connectable? bridge))))

(defn ^:private connect [bridge [a b]]
    (let [[a' b'] (get bridge (dec (count bridge)))]
        (cond
            (and (empty? bridge) (zero? a)) [[a b]]
            (and (empty? bridge) (zero? b)) [[b a]]
            (= b' a) (conj bridge [a b])
            (= b' b) (conj bridge [b a])
            :else (throw (ex-info "Not a matching connection!" {:bridge bridge :port [a b]})))))

(defn ^:private strength [bridge]
    (->> bridge
        (flatten)
        (reduce + 0)))

(defn ^:private start-ports [_ ports]
    (->> ports
        (filter (fn [[a b]] (or (zero? a) (zero? b))))))

(defn ^:private prep []
    (->> (u/read-file 24 #"\n")
        (map s->port)
        (set)))

(def ^:private remove-port disj)

(defn ^:private build-strongest [next-f bridge ports]
    (let [next-ports (next-f bridge ports)]
        (if (empty? next-ports)
            bridge
            (->> next-ports
                (map #(build-strongest connectables (connect bridge %) (remove-port ports %)))
                (sort-by strength >)
                (first)))))

;; 1906
(defn step-1 []
    (->> (prep)
        (build-strongest start-ports nil)
        (strength)))


(defn step-2 []
    )
