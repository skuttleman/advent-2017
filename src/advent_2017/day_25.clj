(ns advent-2017.day-25
    (:require [advent-2017.utils.core :as u]
              [clojure.string :as s]))

(def ^:private s->machine-kw
    (comp keyword s/lower-case))

(defn ^:private direction->f [direction]
    (if (= "left" direction)
        dec
        inc))

(defn ^:private s->rules [s]
    (let [re #"\d:\s+- Write the value (\d)\.\s+- Move one slot to the ([a-z]+)\.\s+- Continue with state ([A-Z]).*"
          [_ new-val direction new-state] (re-matches re s)]
        [(u/parse-int new-val) (direction->f direction) (s->machine-kw new-state)]))

(defn ^:private s->machine [s]
    (let [[_ machine criteria] (re-matches #"([A-Z]):\s+(.*)" s)]
        [(s->machine-kw machine) (mapv s->rules (u/split-n-trim #"If the current value is" criteria))]))

(defn ^:private prep []
    (let [[header & instructions] (u/read-file 25 #"In state")
          [_ start steps] (re-matches #"Begin in state ([A-Z])[^\d]*(\d+).*" header)]
        [(s->machine-kw start) (u/parse-int steps) (->> instructions
                                                                (map (comp s->machine #(s/replace % #"\n" " ")))
                                                                (into {}))]))

(defn ^:private get-value [tape position]
    (get tape position 0))

(defn ^:private write [tape machine position]
    (let [current-value (get-value tape position)
          [next-val f next-machine] (get machine current-value)]
        [(assoc tape position next-val) next-machine (f position)]))

(def ^:private checksum
    (comp (partial reduce + 0) vals))

;; 3554
(defn step-1 []
    (let [[start steps machines] (prep)]
        (loop [[tape machine position] [{} start 0] step 0]
            (if (= step steps)
                (checksum tape)
                (recur (write tape (machines machine) position) (inc step))))))


(defn step-2 []
    )
