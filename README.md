# Aleph-system

Small wrapper for Clojure, Aleph server and Bidi routing.

## Usage

[![Clojars Project](http://clojars.org/aleph-system/latest-version.svg)](http://clojars.org/aleph-system)

Example, starts Aleph webserver at http://localhost:9990/ 

```
(ns ...
  (:require
  ...
  [ring.util.response :refer :all]
  [hiccup.page :refer :all]
  [hiccup.core :refer :all]
  [aleph-system.core :as asystem])
  ...
)

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
                 
                 
(asystem/system-restart setup)
```


## License

Copyright © 2015 Martin Skou Drewes

Distributed under the Apache License.
