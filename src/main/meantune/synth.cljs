(ns meantune.synth
  (:require ["tone" :as tone]
            [meantune.theory :as theory]))

(defn make-synth []
  (.toMaster (tone/Synth.)))

(defn make-synths [synth-count]
  (mapv make-synth (range synth-count)))

(defn play-note [synth note & {:keys [:a4-freq :temperament]
                               :or {a4-freq 440 temperament :equal}}]
  (let [freq (theory/frequency note a4-freq temperament)]
    (.triggerAttack synth freq)
    {:synth synth :note note :a4-freq a4-freq :temperament temperament}))

(defn stop [synth]
  (.triggerRelease synth)
  {:synth synth})

(defn retune [synth note & {:keys [:a4-freq :temperament]
                            :or {a4-freq 440 temperament :equal}}]
  (let [new-freq (theory/frequency note a4-freq temperament)]
    (.setValueAtTime (.-frequency synth) new-freq)
    {:synth synth :note note :a4-freq a4-freq :temperament temperament}))

(defn set-oscillator [synth oscillator]
  (let [osc (-> synth .-oscillator)]
    (.set osc #js {:type (name oscillator)})
    {:synth synth :oscillator oscillator}))
