(ns advent-2017.day-18
    (:require [advent-2017.utils.core :as u]))

(defn ^:private resolve-val [registers value]
    (if (keyword? value)
        (get registers value 0)
        value))

(defn ^:private -update [f]
    (fn [registers pos plays register value]
        [(update registers register f (resolve-val registers value)) (inc pos) plays]))

(defn ^:private -snd [registers pos plays value]
    [registers (inc pos) (conj plays (resolve-val registers value))])

(def ^:private -set (-update (comp second vector)))

(def ^:private -add (-update (fnil + 0)))

(def ^:private -mul (-update (fnil * 0)))

(def ^:private -mod (-update (fnil mod 0)))

(defn ^:private -rcv [registers pos plays value]
    (if-not (zero? (resolve-val registers value))
        (last plays)
        [registers (inc pos) plays]))

(defn ^:private -jgz [registers pos plays condition value]
    (if (> (resolve-val registers condition) 0)
        [registers (+ (resolve-val registers value) pos) plays]
        [registers (inc pos) plays]))

(defn ^:private matches [re s & rules]
    (apply u/matches re s #"-?\d+" u/parse-int #"[a-z]+" keyword rules))

(defn ^:private s->instruction [s]
    (let [
          [snd-x] (matches #"snd ([a-z]+|-?\d+)" s)
          [set-x set-y] (matches #"set ([a-z]+) ([a-z]+|-?\d+)" s)
          [add-x add-y] (matches #"add ([a-z]+) ([a-z]+|-?\d+)" s)
          [mul-x mul-y] (matches #"mul ([a-z]+) ([a-z]+|-?\d+)" s)
          [mod-x mod-y] (matches #"mod ([a-z]+) ([a-z]+|-?\d+)" s)
          [rcv-x] (matches #"rcv ([a-z]+|-?\d+)" s)
          [jgz-x jgz-y] (matches #"jgz ([a-z]+|-?\d+) ([a-z]+|-?\d+)" s)]
        (cond
            snd-x [s #(-snd %1 %2 %3 snd-x)]
            set-x [s #(-set %1 %2 %3 set-x set-y)]
            add-x [s #(-add %1 %2 %3 add-x add-y)]
            mul-x [s #(-mul %1 %2 %3 mul-x mul-y)]
            mod-x [s #(-mod %1 %2 %3 mod-x mod-y)]
            rcv-x [s #(-rcv %1 %2 %3 rcv-x)]
            jgz-x [s #(-jgz %1 %2 %3 jgz-x jgz-y)])))

(defn prep []
    (->> (u/read-file 18 #"\n")
        #_(u/split-n-trim #"\n" "set a 1\nadd a 2\nmul a a\nmod a 5\nsnd a\nset a 0\nrcv a\njgz a -1\nset a 1\njgz a -2")
        (mapv s->instruction)))

;; 3423
(defn step-1 []
    (let [steps (prep)]
        (loop [[registers pos plays] [{} 0 []]]
            (let [[s f] (get steps pos)
                  result (f registers pos plays)]
                (if (vector? result)
                    (recur result)
                    result)))))


(defn step-2 []
    )
