(ns advent-2017.day-12
    (:require [advent-2017.utils.core :as u]))

(defn s->pipe [s]
    (let [[_ id pipes] (re-matches #"(\d+) <->(.*)" s)
          id' (u/parse-int id)]
        [id' (disj (->> pipes
                       (u/split-n-trim #",")
                       (map u/parse-int)
                       (set))
                 id')]))

(defn is-connected-to? [memoized pipes pipe [id conns]]
    (or (= pipe id)
        (contains? conns pipe)
        (->> conns
            (remove memoized)
            (filter #(is-connected-to? (conj memoized %) pipes pipe [% (get pipes %)]))
            (seq))))

(defn prep []
    (->> (u/read-file 12 #"\n")
        (map s->pipe)
        (into {})))

;; 169
(defn step-1 []
    (let [pipes (prep)]
        (->> pipes
            (filter (partial is-connected-to? #{} pipes 0))
            (count))))


(defn step-2 []
    )
