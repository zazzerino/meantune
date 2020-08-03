(ns meantune.cofx
  (:require [re-frame.core :as re-frame]
            [meantune.synth :as synth]))

(re-frame/reg-cofx
 :synth
 (fn [cofx]
   (assoc cofx :synth (synth/make-synth))))
