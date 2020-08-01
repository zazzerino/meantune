(ns meantune.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as re-frame]
            [meantune.subs]
            [meantune.events]
            [meantune.views.index :as views]))

(defn ^:dev/after-load init []
  (re-frame/dispatch-sync [:initialize-db])
  (dom/render [views/main-panel]
                  (js/document.getElementById "root")))

(defn ^:export main []
  (init))
