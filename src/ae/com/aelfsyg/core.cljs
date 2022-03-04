(ns ae.com.aelfsyg.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [ae.com.aelfsyg.events :as events]
   [ae.com.aelfsyg.routes :as routes]
   [ae.com.aelfsyg.views :as views]
   [ae.com.aelfsyg.config :as config]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

#_{:clj-kondo/ignore [:clojure-lsp/unused-public-var]}
(defn init []
  (routes/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
