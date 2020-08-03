(ns meantune.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            ["qwerty-hancock" :as qwerty-hancock]))

(defn welcome-message []
  (let [app-name (re-frame/subscribe [:app-name])]
    [:div {:class "welcome-message"}
     [:p "Welcome to " @app-name "."]]))

(defn keyboard []
  (let [id "keyboard"
        width 400
        height 100
        start-note "C4"
        octaves 2]
    (reagent/create-class
     {:render (fn [] [:div {:id id}])
      :component-did-mount
      (fn [_]
        (let [qh (new qwerty-hancock/QwertyHancock
                      #js {:id id
                           :width width
                           :height height
                           :startNote start-note
                           :octaves octaves})]
          (set! (.-keyDown qh)
                #(re-frame/dispatch [:keyboard/keydown %]))
          (set! (.-keyUp qh)
                #(re-frame/dispatch [:keyboard/keyup %]))))})))

(defn sustain-checkbox []
  (let [sustain? (re-frame/subscribe [:sustain?])]
    [:div {:class "sustain-checkbox"}
     [:label "Sustain: "]
     [:input {:type "checkbox"
              :checked @sustain?
              :on-change #(re-frame/dispatch [:synth/toggle-sustain])}]]))

(defn main-panel []
  [:div {:class "main-panel"}
   [welcome-message]
   [keyboard]
   [sustain-checkbox]
   [:div (str @re-frame.db/app-db)]])
