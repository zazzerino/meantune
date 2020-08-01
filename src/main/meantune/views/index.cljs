(ns meantune.views.index
  (:require [re-frame.core :as re-frame]))

(defn welcome-message []
  (let [app-name (re-frame/subscribe [:app-name])]
    [:div {:class "welcome-message"}
     [:p "Welcome to " @app-name "."]]))

(defn main-panel []
  [:div {:class "main-panel"}
   [welcome-message]])
