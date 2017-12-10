(ns advent-2017.day-9
    (:require [advent-2017.utils.core :as u]))

(defmulti ^:private determine-ctx (comp ffirst vector))

(defmethod ^:private determine-ctx :normal
    [[state nesting canceled score :as ctx] char]
    (case char
        \{ [state (inc nesting) canceled (+ score (inc nesting))]
        \} [state (dec nesting) canceled score]
        \< [:garbage nesting canceled score]
        ctx))

(defmethod ^:private determine-ctx :garbage
    [[state nesting canceled score :as ctx] char]
    (case char
        \! [:not nesting canceled score]
        \> [:normal nesting canceled score]
        [state nesting (inc canceled) score]))

(defmethod ^:private determine-ctx :not
    [[state nesting canceled score :as ctx] char]
    [:garbage nesting canceled score])

(defn ^:private calc-score [[state nesting canceled score :as ctx] [char & chars]]
    (let [ctx' (determine-ctx ctx char)]
        (if (empty? chars)
            ctx'
            (recur ctx' chars))))

(defn ^:private prep []
    (->> (u/read-file 9 #"\n")
        (first)
        (calc-score [:normal 0 0 0])))

;; 12803
(defn step-1 []
    (->> (prep)
        (last)))

(defn step-2 []
    (->> (prep)
        (butlast)
        (last)))
