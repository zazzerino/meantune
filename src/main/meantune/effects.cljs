(ns meantune.effects
  (:require [re-frame.core :as re-frame]
            [meantune.synth :as synth]))

(re-frame/reg-fx
 :synth/play-note!
 (fn [{:keys [synth note a4-freq temperament]}]
   (synth/play-note synth note :a4-freq a4-freq :temperament temperament)))

(re-frame/reg-fx
 :synth/stop-note!
 (fn [{:keys [synth]}]
   (synth/stop synth)))

(re-frame/reg-fx
 :synth/stop-all!
 (fn [{:keys [synths]}]
   (doseq [synth (map :synth synths)]
     (synth/stop synth))))
