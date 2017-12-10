(ns advent-2017.day-10
    (:require [advent-2017.utils.core :as u]))

(defn ^:private reverse-section [rope pos len]
    (let [pre (take pos rope)
          twisted (reverse (take len (concat (drop pos rope) rope)))
          post (drop (+ pos len) rope)
          xtra (- (+ len pos) (count rope))
          overlap (- (count twisted) xtra)]
        (if (> xtra 0)
            (concat (drop overlap twisted) (drop xtra pre) (take overlap twisted))
            (concat pre twisted post))))

(defn ^:private knot-it-up [rope pos skip [len & lens]]
    (let [rope' (reverse-section rope pos len)]
        (if (empty? lens)
            rope'
            (recur rope' (mod (+ pos len skip) (count rope')) (inc skip) lens))))

;; 62238
(defn step-1 []
    (->> (u/read-file 10 #",")
        (map u/parse-int)
        (knot-it-up (range 256) 0 0)
        (take 2)
        (reduce * 1)))


(defn step-2 []
    )
