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
              [advent-2017.day-15 :as day-15]
              [advent-2017.day-16 :as day-16]
              [advent-2017.day-17 :as day-17]
              [advent-2017.day-18 :as day-18]
              [advent-2017.day-19 :as day-19]
              [advent-2017.day-20 :as day-20]
              [clojure.string :as s]
              [advent-2017.utils.core :as u]
              [clojure.core.async :as async]))

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
     [day-14/step-1 day-14/step-2]
     [day-15/step-1 day-15/step-2]
     [day-16/step-1 day-16/step-2]
     [day-17/step-1 day-17/step-2]
     [day-18/step-1 day-18/step-2]
     [day-19/step-1 day-19/step-2]
     [day-20/step-1 day-20/step-2]])

(defn -main [& [day step]]
    (let [[day' step'] (map #(-> (str "0" %)
                                 (s/replace #"\D" "")
                                 (Integer/parseInt)
                                 (dec)) [day step])
          step (get-in steps [day' step'])]
        (when step
            (let [result (step)]
                (if (u/chan? result)
                    (println (async/<!! result))
                    (println result))))))
