(ns advent-2017.utils.matrix-test
    (:require [advent-2017.utils.matrix :as m]
              [clojure.test :as t]))

(t/deftest flip-test
    (t/testing "flips a matrix"
        (t/are [m m'] (= (m/flip m) m')
            [[1 2] [3 4]]
            [[2 1] [4 3]]

            [[1 2 3] [4 5 6] [7 8 9]]
            [[3 2 1] [6 5 4] [9 8 7]])))

(t/deftest rotate-test
    (t/testing "rotates a matrix"
        (t/are [m m'] (= (m/rotate m) m')
            [[1 2] [3 4]]
            [[3 1] [4 2]]

            [[1 2 3] [4 5 6] [7 8 9]]
            [[7 4 1] [8 5 2] [9 6 3]])))

(t/deftest flatten-test
    (t/testing "flattens a matrix to a string"
        (t/are [m r c s] (= (m/flatten r c m) s)
            [[1 2] [3 4]] "." "|"
            "1|2.3|4"

            [[1 2 3] [4 5 6] [7 8 9]] " # " "@@"
            "1@@2@@3 # 4@@5@@6 # 7@@8@@9")))

(t/deftest expand-test
    (t/testing "expands a string into a matrix"
        (t/are [s r c m] (= (m/expand r c s) m)
            "1,2,3\"4,5,6\"7,8,9" #"\"" #","
            [["1" "2" "3"] ["4" "5" "6"] ["7" "8" "9"]]

            "aa:bb::cc:dd" #"::" #":"
            [["aa" "bb"] ["cc" "dd"]])))

(def ^:private twelve [["aa" "ab" "ac" "ad" "ae" "af" "ag" "ah" "ai" "aj" "ak" "al"]
                       ["ba" "bb" "bc" "bd" "be" "bf" "bg" "bh" "bi" "bj" "bk" "bl"]
                       ["ca" "cb" "cc" "cd" "ce" "cf" "cg" "ch" "ci" "cj" "ck" "cl"]
                       ["da" "db" "dc" "dd" "de" "df" "dg" "dh" "di" "dj" "dk" "dl"]
                       ["ea" "eb" "ec" "ed" "ee" "ef" "eg" "eh" "ei" "ej" "ek" "el"]
                       ["fa" "fb" "fc" "fd" "fe" "ff" "fg" "fh" "fi" "fj" "fk" "fl"]
                       ["ga" "gb" "gc" "gd" "ge" "gf" "gg" "gh" "gi" "gj" "gk" "gl"]
                       ["ha" "hb" "hc" "hd" "he" "hf" "hg" "hh" "hi" "hj" "hk" "hl"]
                       ["ia" "ib" "ic" "id" "ie" "if" "ig" "ih" "ii" "ij" "ik" "il"]
                       ["ja" "jb" "jc" "jd" "je" "jf" "jg" "jh" "ji" "jj" "jk" "jl"]
                       ["ka" "kb" "kc" "kd" "ke" "kf" "kg" "kh" "ki" "kj" "kk" "kl"]
                       ["la" "lb" "lc" "ld" "le" "lf" "lg" "lh" "li" "lj" "lk" "ll"]])
(def ^:private split-2 [[["aa" "ab"] ["ba" "bb"]] [["ac" "ad"] ["bc" "bd"]] [["ae" "af"] ["be" "bf"]]
                        [["ag" "ah"] ["bg" "bh"]] [["ai" "aj"] ["bi" "bj"]] [["ak" "al"] ["bk" "bl"]]
                        [["ca" "cb"] ["da" "db"]] [["cc" "cd"] ["dc" "dd"]] [["ce" "cf"] ["de" "df"]]
                        [["cg" "ch"] ["dg" "dh"]] [["ci" "cj"] ["di" "dj"]] [["ck" "cl"] ["dk" "dl"]]
                        [["ea" "eb"] ["fa" "fb"]] [["ec" "ed"] ["fc" "fd"]] [["ee" "ef"] ["fe" "ff"]]
                        [["eg" "eh"] ["fg" "fh"]] [["ei" "ej"] ["fi" "fj"]] [["ek" "el"] ["fk" "fl"]]
                        [["ga" "gb"] ["ha" "hb"]] [["gc" "gd"] ["hc" "hd"]] [["ge" "gf"] ["he" "hf"]]
                        [["gg" "gh"] ["hg" "hh"]] [["gi" "gj"] ["hi" "hj"]] [["gk" "gl"] ["hk" "hl"]]
                        [["ia" "ib"] ["ja" "jb"]] [["ic" "id"] ["jc" "jd"]] [["ie" "if"] ["je" "jf"]]
                        [["ig" "ih"] ["jg" "jh"]] [["ii" "ij"] ["ji" "jj"]] [["ik" "il"] ["jk" "jl"]]
                        [["ka" "kb"] ["la" "lb"]] [["kc" "kd"] ["lc" "ld"]] [["ke" "kf"] ["le" "lf"]]
                        [["kg" "kh"] ["lg" "lh"]] [["ki" "kj"] ["li" "lj"]] [["kk" "kl"] ["lk" "ll"]]])
(def ^:private split-3 [[["aa" "ab" "ac"] ["ba" "bb" "bc"] ["ca" "cb" "cc"]]
                        [["ad" "ae" "af"] ["bd" "be" "bf"] ["cd" "ce" "cf"]]
                        [["ag" "ah" "ai"] ["bg" "bh" "bi"] ["cg" "ch" "ci"]]
                        [["aj" "ak" "al"] ["bj" "bk" "bl"] ["cj" "ck" "cl"]]
                        [["da" "db" "dc"] ["ea" "eb" "ec"] ["fa" "fb" "fc"]]
                        [["dd" "de" "df"] ["ed" "ee" "ef"] ["fd" "fe" "ff"]]
                        [["dg" "dh" "di"] ["eg" "eh" "ei"] ["fg" "fh" "fi"]]
                        [["dj" "dk" "dl"] ["ej" "ek" "el"] ["fj" "fk" "fl"]]
                        [["ga" "gb" "gc"] ["ha" "hb" "hc"] ["ia" "ib" "ic"]]
                        [["gd" "ge" "gf"] ["hd" "he" "hf"] ["id" "ie" "if"]]
                        [["gg" "gh" "gi"] ["hg" "hh" "hi"] ["ig" "ih" "ii"]]
                        [["gj" "gk" "gl"] ["hj" "hk" "hl"] ["ij" "ik" "il"]]
                        [["ja" "jb" "jc"] ["ka" "kb" "kc"] ["la" "lb" "lc"]]
                        [["jd" "je" "jf"] ["kd" "ke" "kf"] ["ld" "le" "lf"]]
                        [["jg" "jh" "ji"] ["kg" "kh" "ki"] ["lg" "lh" "li"]]
                        [["jj" "jk" "jl"] ["kj" "kk" "kl"] ["lj" "lk" "ll"]]])
(def ^:private split-4 [[["aa" "ab" "ac" "ad"] ["ba" "bb" "bc" "bd"] ["ca" "cb" "cc" "cd"] ["da" "db" "dc" "dd"]]
                        [["ae" "af" "ag" "ah"] ["be" "bf" "bg" "bh"] ["ce" "cf" "cg" "ch"] ["de" "df" "dg" "dh"]]
                        [["ai" "aj" "ak" "al"] ["bi" "bj" "bk" "bl"] ["ci" "cj" "ck" "cl"] ["di" "dj" "dk" "dl"]]
                        [["ea" "eb" "ec" "ed"] ["fa" "fb" "fc" "fd"] ["ga" "gb" "gc" "gd"] ["ha" "hb" "hc" "hd"]]
                        [["ee" "ef" "eg" "eh"] ["fe" "ff" "fg" "fh"] ["ge" "gf" "gg" "gh"] ["he" "hf" "hg" "hh"]]
                        [["ei" "ej" "ek" "el"] ["fi" "fj" "fk" "fl"] ["gi" "gj" "gk" "gl"] ["hi" "hj" "hk" "hl"]]
                        [["ia" "ib" "ic" "id"] ["ja" "jb" "jc" "jd"] ["ka" "kb" "kc" "kd"] ["la" "lb" "lc" "ld"]]
                        [["ie" "if" "ig" "ih"] ["je" "jf" "jg" "jh"] ["ke" "kf" "kg" "kh"] ["le" "lf" "lg" "lh"]]
                        [["ii" "ij" "ik" "il"] ["ji" "jj" "jk" "jl"] ["ki" "kj" "kk" "kl"] ["li" "lj" "lk" "ll"]]])

(t/deftest split-test
    (t/testing "splits a matrix into multiple matrices"
        (t/are [m n m'] (= (m/split n m) m')
            twelve 2 split-2
            twelve 3 split-3
            twelve 4 split-4)))

(t/deftest join-test
    (t/testing "joins matrices into a single matrix"
        (t/are [m m'] (= (m/join m) m')
            split-2 twelve
            split-3 twelve
            split-4 twelve)))
