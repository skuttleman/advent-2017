(ns advent-2017.day-18
    (:require [advent-2017.utils.core :as u]
              [clojure.core.async :as async]))

(def program-0 {:state (atom {:status :ready
                              :receive-count 0})
                :chan  (async/chan 10000)})

(def program-1 {:state (atom {:status :ready
                              :receive-count 0})
                :chan  (async/chan 10000)})

(defn ^:private resolve-val [registers value]
    (if (keyword? value)
        (get registers value 0)
        value))

(defn ^:private -update [f]
    (fn [registers pos ctx register value]
        [(update registers register f (resolve-val registers value)) (inc pos) ctx]))

(defn ^:private -snd [registers pos ctx value]
    [registers (inc pos) (conj ctx (resolve-val registers value))])

(defn ^:private -snd' [registers pos program value]
    (async/>!! (:chan program) (resolve-val registers value))
    (update program :state swap! update :receive-count inc)
    [registers (inc pos)])

(def ^:private -set (-update (comp second vector)))

(def ^:private -add (-update (fnil + 0)))

(def ^:private -mul (-update (fnil * 0)))

(def ^:private -mod (-update (fnil mod 0)))

(defn ^:private -rcv [registers pos ctx value]
    (if-not (zero? (resolve-val registers value))
        (last ctx)
        [registers (inc pos) ctx]))

(defn ^:private -rcv' [registers pos program value]
    (async/go
        (update program :state swap! assoc :status :waiting)
        (let [received (async/<! (:chan program))]
            (update program :state swap! assoc :status :ready)
            (-set registers pos nil value received))))

(defn ^:private -jgz [registers pos ctx condition value]
    (if (> (resolve-val registers condition) 0)
        [registers (+ (resolve-val registers value) pos) ctx]
        [registers (inc pos) ctx]))

(defn ^:private matches [re s & rules]
    (apply u/matches re s #"-?\d+" u/parse-int #"[a-z]+" keyword rules))

(defn ^:private s->instruction [s]
    (let [[snd-x] (matches #"snd ([a-z]+|-?\d+)" s)
          [set-x set-y] (matches #"set ([a-z]+) ([a-z]+|-?\d+)" s)
          [add-x add-y] (matches #"add ([a-z]+) ([a-z]+|-?\d+)" s)
          [mul-x mul-y] (matches #"mul ([a-z]+) ([a-z]+|-?\d+)" s)
          [mod-x mod-y] (matches #"mod ([a-z]+) ([a-z]+|-?\d+)" s)
          [rcv-x] (matches #"rcv ([a-z]+|-?\d+)" s)
          [jgz-x jgz-y] (matches #"jgz ([a-z]+|-?\d+) ([a-z]+|-?\d+)" s)]
        (cond
            snd-x [:sync #(-snd %1 %2 %3 snd-x)]
            set-x [:sync #(-set %1 %2 %3 set-x set-y)]
            add-x [:sync #(-add %1 %2 %3 add-x add-y)]
            mul-x [:sync #(-mul %1 %2 %3 mul-x mul-y)]
            mod-x [:sync #(-mod %1 %2 %3 mod-x mod-y)]
            rcv-x [:sync #(-rcv %1 %2 %3 rcv-x)]
            jgz-x [:sync #(-jgz %1 %2 %3 jgz-x jgz-y)])))


(defn ^:private s->instruction' [s]
    (let [[snd-x] (matches #"snd ([a-z]+|-?\d+)" s)
          [rcv-x] (matches #"rcv ([a-z]+|-?\d+)" s)]
        (cond
            snd-x [:write #(-snd' %1 %2 %3 snd-x)]
            rcv-x [:read #(-rcv' %1 %2 %3 rcv-x)]
            :else (s->instruction s))))

(defn prep [f]
    (mapv f (u/read-file 18 #"\n")))

;; 3423
(defn step-1 []
    (let [steps (prep s->instruction)]
        (loop [[registers pos plays] [{} 0 []]]
            (let [[_ f] (get steps pos)
                  result (f registers pos plays)]
                (if (vector? result)
                    (recur result)
                    result)))))

;; 7493
(defn step-2 []
    (let [steps    (prep s->instruction')]
        (async/go-loop [[registers pos] [{:p 0} 0]]
                 (let [[type f] (get steps pos)
                       result (if (= type :read)
                                  (async/<! (f registers pos program-0))
                                  (f registers pos program-1))]
                     (recur result)))
        (async/go-loop [[registers pos] [{:p 1} 0]]
                    (let [[type f] (get steps pos)
                          result (if (= type :read)
                                     (async/<! (f registers pos program-1))
                                     (f registers pos program-0))]
                        (recur result)))
        (async/go-loop [wait-time 0]
            (let [status-0 (:status @(:state program-0))
                  status-1 (:status @(:state program-1))]
                (if (and (= :waiting status-0) (= :waiting status-1))
                    (if (>= wait-time 3)
                        (:receive-count @(:state program-0))
                        (do (Thread/sleep 1000)
                            (recur (inc wait-time))))
                    (recur 0))))))
