(ns meantune.effects
  (:require [re-frame.core :as re-frame]
            [meantune.synth :as synth]))

(re-frame/reg-fx
 :play-note!
 (fn [{:keys [synth note-name a4-freq temperament] :as args}]
   (synth/play-note args)))

(re-frame/reg-fx
 :stop-note!
 (fn [{:keys [synth]}]
   (synth/stop {:synth synth})))

(re-frame/reg-fx
 :stop-all!
 (fn [{:keys [notes]}]
   (doseq [synth (map :synth notes)]
     (synth/stop {:synth synth}))))

(re-frame/reg-fx
 :retune!
 (fn [{:keys [notes a4-freq temperament]}]
   (doseq [note notes]
     (let [synth (:synth note)
           note-name (:note-name note)]
       (synth/retune {:synth synth
                      :note-name note-name
                      :a4-freq a4-freq
                      :temperament temperament})))))
