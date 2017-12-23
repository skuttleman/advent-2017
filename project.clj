(defproject advent-2017 "0.1.0-SNAPSHOT"
    :description "FIXME: write description"
    :url "http://example.com/FIXME"
    :license {:name "Eclipse Public License"
              :url  "http://www.eclipse.org/legal/epl-v10.html"}
    :dependencies [[org.clojure/clojure "1.8.0"]
                   [org.clojure/core.async "0.3.465"]]
    :main advent-2017.core
    :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.22.0"]]}})
