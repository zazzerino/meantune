(ns meantune.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :app-name
 (fn [db]
   (:app-name db)))

(re-frame/reg-sub
 :sustain?
 (fn [db]
   (:sustain? db)))

(re-frame/reg-sub
 :a4-freq
 (fn [db]
   (:a4-freq db)))

(re-frame/reg-sub
 :temperament
 (fn [db]
   (:temperament db)))
