(ns advent-2017.day-3)

(defn ^:private taxicab-dist [pt1 pt2]
    (+ (Math/abs (- (:x pt1) (:x pt2)))
        (Math/abs (- (:y pt1) (:y pt2)))))

(defn ^:private prev-perfect-sq [num]
    (assert (and (integer? num) (pos? num)))
    (let [sqrt (Math/sqrt num)]
        (if (== sqrt (int sqrt))
            num
            (recur (dec num)))))

(defn ^:private move [{:keys [x y]} direction]
    (case direction
        :up {:x x :y (dec y)}
        :down {:x x :y (inc y)}
        :left {:x (dec x) :y y}
        :right {:x (inc x) :y y}
        {:x x :y y}))

(defn ^:private turn [direction]
    (->> direction
        (get {:up    :left
              :left  :down
              :down  :right
              :right :up})))

(defn ^:private what-next? [num]
    (let [sqrt-able  (prev-perfect-sq num)
          sqrt       (int (Math/sqrt sqrt-able))
          sqrt'      (if (even? sqrt) (dec sqrt) sqrt)
          spaces     (/ (dec sqrt') 2)]
        {:position  {:x (inc spaces) :y spaces}
         :round     sqrt'
         :current   sqrt'
         :direction :up
         :start    (inc (* sqrt' sqrt'))}))

(defn ^:private reduce-next [{:keys [position round direction current] :as result} target]
    (if (zero? target)
        result
        (let [[current' direction'] (if (zero? current) [round (turn direction)] [(dec current) direction])]
            (recur {:position  (move position direction')
                    :round     round
                    :current   current'
                    :direction direction'}
                (dec target)))))

;; 475
(defn step-1 []
    (let [target 277678
          {:keys [start] :as initial} (what-next? target)]
        (->> (- target start)
            (reduce-next initial)
            (:position)
            (taxicab-dist {:x 0 :y 0}))))
