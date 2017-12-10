(ns advent-2017.day-9
    (:require [advent-2017.utils.core :as u]))

(defmulti ^:private determine-ctx (comp ffirst vector))

(defmethod ^:private determine-ctx :normal
    [[state nesting score :as ctx] char]
    (case char
        \{ [state (inc nesting) (+ score (inc nesting))]
        \} [state (dec nesting) score]
        \< [:garbage nesting score]
        ctx))

(defmethod ^:private determine-ctx :garbage
    [[state nesting score :as ctx] char]
    (case char
        \! [:not nesting score]
        \> [:normal nesting score]
        ctx))

(defmethod ^:private determine-ctx :not
    [[state nesting score :as ctx] char]
    [:garbage nesting score])

(defn ^:private calc-score [[state nesting score :as ctx] [char & chars]]
    (let [ctx' (determine-ctx ctx char)]
        (if (empty? chars)
            ctx'
            (recur ctx' chars))))

(defn ^:private prep []
    (-> (u/read-file 9 #"\n")
        (first)))

;; 12803
(defn step-1 []
    (->> (prep)
        (calc-score [:normal 0 0])
        (last)))

(defn step-2 []
    )
