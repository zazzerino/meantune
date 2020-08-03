(ns meantune.events
  (:require [re-frame.core :as re-frame]
            [meantune.db :as db]
            [meantune.effects]
            [meantune.theory :as theory]
            [meantune.synth :as synth]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 :synth/play-note
 [(re-frame/inject-cofx :synth)]
 (fn [cofx [_ note]]
   (if (theory/find-note note (:synths (:db cofx)))
     {:db (:db cofx)}
     (let [db (:db cofx)
           note-map {:synth (:synth cofx)
                     :note note
                     :a4-freq (:a4-freq db)
                     :temperament (:temperament db)}]
       {:db (assoc db :synths (conj (:synths db) note-map))
        :synth/play-note! note-map}))))

(re-frame/reg-event-fx
 :synth/stop-note
 (fn [cofx [_ note]]
   (let [db (:db cofx)
         note-map (theory/find-note note (:synths db))
         synths (filterv (fn [s]
                           (not= note
                                 (:note s)))
                         (:synths db))
         synth (:synth note-map)]
     {:db (assoc db :synths synths)
      :synth/stop-note! {:synth synth}})))

(re-frame/reg-event-fx
 :keyboard/keydown
 (fn [cofx [_ note]]
   {:db (:db cofx)
    :dispatch [:synth/play-note note]}))

(re-frame/reg-event-fx
 :keyboard/keyup
 (fn [cofx [_ note]]
   {:db (:db cofx)
    :dispatch [:synth/stop-note note]}))
