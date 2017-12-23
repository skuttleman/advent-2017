(ns advent-2017.day-23
    (:require [advent-2017.utils.core :as u]))

(defn ^:private resolve-val [registers value]
    (if (keyword? value)
        (get registers value 0)
        value))

(defn ^:private -update [f]
    (fn [registers pos register value]
        [(update registers register f (resolve-val registers value)) (inc pos)]))

(def ^:private -set (-update (comp second vector)))

(def ^:private -sub (-update (fnil - 0)))

(def ^:private -mul (-update (fnil * 0)))

(defn ^:private -jnz [registers pos condition value]
    (if (zero? (resolve-val registers condition))
        [registers (inc pos)]
        [registers (+ (resolve-val registers value) pos)]))

(defn ^:private matches [re s & rules]
    (apply u/matches re s #"-?\d+" u/parse-int #"[a-z]+" keyword rules))

(defn ^:private s->instruction [s]
    (let [[set-x set-y] (matches #"set ([a-z]+) ([a-z]+|-?\d+)" s)
          [sub-x sub-y] (matches #"sub ([a-z]+) ([a-z]+|-?\d+)" s)
          [mul-x mul-y] (matches #"mul ([a-z]+) ([a-z]+|-?\d+)" s)
          [jnz-x jnz-y] (matches #"jnz ([a-z]+|-?\d+) ([a-z]+|-?\d+)" s)]
        (cond
            set-x [:set #(-set %1 %2 set-x set-y)]
            sub-x [:sub #(-sub %1 %2 sub-x sub-y)]
            mul-x [:mul #(-mul %1 %2 mul-x mul-y)]
            jnz-x [:jnz #(-jnz %1 %2 jnz-x jnz-y)])))

(defn ^:private prep []
    (->> (u/read-file 23 #"\n")
        (map s->instruction)))

;; 6724
(defn step-1 []
    (let [program (prep) length (count program)]
        (loop [registers {} position 0 mult-count 0]
            (if-let [[label f] (nth program position nil)]
                (let [[registers' position'] (f registers position)
                      mult-count' (if (= :mul label) (inc mult-count) mult-count)]
                    (recur registers' position' mult-count'))
                mult-count))))


(defn step-2 []
    )
