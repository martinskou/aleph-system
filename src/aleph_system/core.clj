(ns aleph-system.core
  (:require [clojure.tools.namespace.repl :refer [refresh refresh-all disable-reload!]]
            [aleph.http :as http]
            [clojure.pprint :refer [pprint]]
            [clojure.repl :refer [pst]]
            [bidi.bidi :as bidi]
            [bidi.ring]
            [clojure.string :as str]
            [cheshire.core :as json]
            [ring.middleware.params :as params]
            [ring.middleware.session :as session]
            [ring.middleware.session.cookie :as sessioncookie]
            [ring.middleware.keyword-params :as keyword-params]
            [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
            ))

(disable-reload!)

(declare system)
(declare system-stop)

(defn resources [] 
  (bidi.ring/->ResourcesMaybe {:prefix "public/"}))

(defonce system (atom {}))

(defn system-start
  ([setup]
  (when (contains? @system :server) (system-stop))
  (let [setup-func (if (nil? setup) (:setup @system) setup)
        setup (apply setup-func [])
        routes (:routes setup)
        new-system {:setup setup-func
                    :server (http/start-server
                              (wrap-json-params
                                (wrap-json-response
                                  (session/wrap-session
                                    (params/wrap-params
                                      (keyword-params/wrap-keyword-params
                                        (bidi.ring/make-handler routes)))
                                     {:cookie-name (:cookie-name setup)
                                      :store (sessioncookie/cookie-store {:key (:cookie-store-key setup)})})))
                              {:port (:port setup)})
                   }]
        (reset! system new-system)))
  ([] (system-start nil)))

(defn system-stop []
  (when @system 
    (when (:server @system)
      (.close (:server @system))))
  (swap! system dissoc :server))


(defn system-restart 
  ([]
    (system-stop)
    (refresh :after 'aleph-system.core/system-start))
  ([setup]
    (swap! system assoc :setup setup)
    (system-restart)))





