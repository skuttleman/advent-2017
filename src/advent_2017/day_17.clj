(ns advent-2017.day-17)


(defn step-forward [coll pos steps]
    (mod (+ pos steps) (count coll)))

(defn insert [coll pos value]
    (concat (take pos coll) [value] (drop pos coll)))

;; 136
(defn step-1 []
    (let [steps 363 max-value 2017]
        (loop [coll [0] pos 0 next-value 1]
            (if (> next-value max-value)
                (let [pos' (inc (step-forward coll pos (* -1 steps)))]
                    (first (drop pos' coll)))
                (let [pos'  (inc pos)
                      coll' (insert coll pos' next-value)]
                    (recur coll'
                        (step-forward coll' pos' steps)
                        (inc next-value)))))))


(defn step-2 []
    )
