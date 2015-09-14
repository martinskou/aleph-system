(defproject aleph-system "1.0.0"
  :description "Aleph system using Bibi routing"
  :url "http://github.com/martinskou/aleph-system/"
  :license {:name "Apache License"
            :url "http://www.apache.org/licenses/"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/tools.namespace "0.2.3"]
                 [aleph "0.4.0"]
                 [bidi "1.18.11"]
                 [cheshire "5.4.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [hiccup "1.0.5"]]
  :main aleph-system.test
  )
