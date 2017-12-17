(ns advent-2017.core
    (:require [advent-2017.day-1 :as day-1]
              [advent-2017.day-2 :as day-2]
              [advent-2017.day-3 :as day-3]
              [advent-2017.day-4 :as day-4]
              [advent-2017.day-5 :as day-5]
              [advent-2017.day-6 :as day-6]
              [advent-2017.day-7 :as day-7]
              [advent-2017.day-8 :as day-8]
              [advent-2017.day-9 :as day-9]
              [advent-2017.day-10 :as day-10]
              [advent-2017.day-11 :as day-11]
              [advent-2017.day-12 :as day-12]
              [advent-2017.day-13 :as day-13]
              [advent-2017.day-14 :as day-14]
              [clojure.string :as s]))

(def steps
    [[day-1/step-1 day-1/step-2]
     [day-2/step-1 day-2/step-2]
     [day-3/step-1 day-3/step-2]
     [day-4/step-1 day-4/step-2]
     [day-5/step-1 day-5/step-2]
     [day-6/step-1 day-6/step-2]
     [day-7/step-1 day-7/step-2]
     [day-8/step-1 day-8/step-2]
     [day-9/step-1 day-9/step-2]
     [day-10/step-1 day-10/step-2]
     [day-11/step-1 day-11/step-2]
     [day-12/step-1 day-12/step-2]
     [day-13/step-1 day-13/step-2]
     [day-14/step-1 day-14/step-2]])

(defn -main [& [day step]]
    (let [[day' step'] (map (fn [v] (-> (str "0" v)
                                        (s/replace #"\D" "")
                                        (Integer/parseInt)
                                        (dec))) [day step])
          step (get-in steps [day' step'])]
        (when step (println (step)))))
