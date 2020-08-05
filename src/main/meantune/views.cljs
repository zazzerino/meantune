(ns meantune.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            ["qwerty-hancock" :as qwerty-hancock]))

(defn welcome-message []
  (let [app-name @(re-frame/subscribe [:app-name])]
    [:div {:class "welcome-message"}
     [:p "Welcome to " app-name "."]]))

(defn keyboard []
  (let [id "keyboard"
        width 400
        height 100
        start-note "C4"
        octaves 2]
    (reagent/create-class
     {:render (fn []
                [:div {:id id}])
      :component-did-mount
      (fn [_]
        (let [qh (new qwerty-hancock/QwertyHancock
                      #js {:id id
                           :width width
                           :height height
                           :startNote start-note
                           :octaves octaves})]
          (set! (.-keyDown qh)
                #(re-frame/dispatch [:keydown %]))
          (set! (.-keyUp qh)
                #(re-frame/dispatch [:keyup %]))))})))

(defn sustain-checkbox []
  (let [sustain? @(re-frame/subscribe [:sustain?])]
    [:div {:class "sustain-checkbox"}
     [:label "Sustain: "]
     [:input {:type "checkbox"
              :checked sustain?
              :on-change #(re-frame/dispatch [:toggle-sustain])}]]))

(defn a4-freq-input []
  (let [a4-freq @(re-frame/subscribe [:a4-freq])]
    [:div {:class "a4-input"}
     [:label "A4 Frequency: "]
     [:input {:type "number"
              :min 220
              :max 880
              :value a4-freq
              :on-change #(re-frame/dispatch
                           [:change-a4-freq
                            (-> % .-target .-value js/parseInt)])}]]))

(defn temperament-select []
  (let [temperament @(re-frame/subscribe [:temperament])]
    [:div {:class "temperament-select"}
     [:label "Temperament: "]
     [:select {:value temperament
               :on-change #(re-frame/dispatch
                            [:change-temperament
                             (-> % .-target .-value keyword)])}
      [:option {:value "just"} "just"]
      [:option {:value "pythagorean"} "pythagorean"]
      [:option {:value "quarter-comma"} "quarter-comma"]
      [:option {:value "equal"} "equal"]]]))

(defn stop-button []
  [:button {:on-click #(re-frame/dispatch [:stop-all])}
   "Stop"])

;; (defn app-state []
;;   (let [state (str @re-frame.db/app-db)]
;;     [:div state]))

(defn main-panel []
  [:div {:class "main-panel"}
   [welcome-message]
   [keyboard]
   [sustain-checkbox]
   [a4-freq-input]
   [temperament-select]
   [stop-button]
   #_[app-state]])
