(ns advent-2017.day-6-test
    (require [advent-2017.day-6 :as day-6]
             [clojure.test :as t]))

(t/deftest max-pos-test
    (t/testing "getting a vector's max position"
        (t/are [v pos] (= (day-6/max-pos v) pos)
            [0 1 2 3 4] 4
            [5 4 3 2 1] 0
            [1 2 3 4 3 2 1] 3
            [1 5 2 3 4 5] 1)))

(t/deftest redistribute-test
    (t/testing "redistributing a vectors highest value"
        (with-redefs [day-6/max-pos (constantly 2)]
            (t/are [in out] (= (day-6/redistribute in) out)
                [0 0 1 0 0] [0 0 0 1 0]
                [5 0 5 0 5] [6 1 1 1 6]))

        (with-redefs [day-6/max-pos (constantly 0)]
            (t/are [in out] (= (day-6/redistribute in) out)
                [11 4 3 2 1] [2 7 5 4 3]
                [0 1 1 1] [0 1 1 1]))

        (with-redefs [day-6/max-pos (constantly 4)]
            (t/are [in out] (= (day-6/redistribute in) out)
                [4 4 4 4 4] [5 5 5 5 0]
                [0 5 0 5 9] [2 7 2 7 1]))))

(t/deftest run-and-track
    (t/testing "counting redistributions"
        (t/are [banks counter] (= (day-6/run-and-track banks #{banks} 0) counter)
            [1 1 1] 4
            [1 2 1] 3
            [0 2 7 0] 5
            [5 9 8 4] 7)))
