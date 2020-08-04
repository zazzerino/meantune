(ns meantune.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as re-frame]
            [meantune.specs]
            [meantune.subs]
            [meantune.cofx]
            [meantune.effects]
            [meantune.events]
            [meantune.synth]
            [meantune.views :as views]))

(enable-console-print!)

(defn ^:dev/after-load init []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [:initialize-db])
  (dom/render [views/main-panel]
              (js/document.getElementById "root")))

(defn ^:export main []
  (init))
