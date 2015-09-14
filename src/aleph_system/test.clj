(ns aleph-system.test
  (:require [clojure.tools.namespace.repl :refer [refresh refresh-all]]
            [clojure.pprint :refer [pprint]]
            [clojure.repl :refer [pst]]
            [aleph-system.core :as asystem]
            [ring.util.response :refer :all]
            [hiccup.page :refer :all]
            [hiccup.core :refer :all]
            ))


(defn home [req]
  (response (html5 [:head [:title "Aleph-system"]] [:body "Home"])))

(defn setup [] {:port 9990
                :cookie-name "somename"
                :cookie-store-key "somekeysomekeyso"
                :routes ["/" [
                              ["" home]
                              ["a/" (fn [req] (pprint req) (response "B"))] 
                              ["j/" (fn [req] (response {:a "abc" :b 13}))] 
                              ["resources/" (asystem/resources)]
                              [#".*" (fn [req] (not-found "Ups"))]
                         ]]
                 })






