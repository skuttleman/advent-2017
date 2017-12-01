(ns advent-2017.core
    (:require [advent-2017.day-1 :as day-1]
              [clojure.string :as s]))

(def steps
    [[day-1/step-1 day-1/step-2]])

(defn -main [& [day step]]
    (let [[day' step'] (map (fn [v] (-> (str "0" v)
                                        (s/replace #"\D" "")
                                        (Integer/parseInt)
                                        (dec))) [day step])
          step (get-in steps [day' step'])]
        (println (step))))
