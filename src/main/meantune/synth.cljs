(ns meantune.synth
  (:require ["tone" :as tone]
            [meantune.theory :as theory]))

(defn make-synth []
  (.toMaster (tone/Synth.)))

(def s (make-synth))

(defn play-note [{:keys [synth note-name a4-freq temperament]}]
  (let [a4-freq (or a4-freq 440)
        temperament (or temperament :equal)
        freq (theory/frequency note-name a4-freq temperament)]
    (.triggerAttack synth freq)
    {:synth synth :note-name note-name
     :a4-freq a4-freq :temperament temperament}))

(defn stop [{:keys [synth]}]
  (.triggerRelease synth)
  {:synth synth})

(defn retune [{:keys [synth note-name a4-freq temperament]}]
  (let [a4-freq (or a4-freq 440)
        temperament (or temperament :equal)
        new-freq (theory/frequency note-name a4-freq temperament)]
    (.setValueAtTime (.-frequency synth) new-freq)
    {:synth synth :note-name note-name
     :a4-freq a4-freq :temperament temperament}))

;; (defn set-oscillator [synth oscillator]
;;   (let [osc (-> synth .-oscillator)]
;;     (.set osc #js {:type (name oscillator)})
;;     {:synth synth :oscillator oscillator}))
