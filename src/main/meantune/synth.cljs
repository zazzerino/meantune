(ns meantune.synth
  (:require ["tone" :as tone]
            [meantune.theory :as theory]))

(defn make-synth []
  (.toMaster (tone/Synth.)))

(defn make-synths [synth-count]
  (mapv make-synth (range synth-count)))

(defn note-on [synth note & {:keys [:a4-freq :temperament]
                             :or {a4-freq 440 temperament :equal}}]
  (let [freq (theory/frequency note a4-freq temperament)]
    (.triggerAttack synth freq)))

(defn stop [synth]
  (.triggerRelease synth))

(defn retune [synth note & {:keys [:a4-freq :temperament]
                            :or {a4-freq 440 temperament :equal}}]
  (let [new-freq (theory/frequency note a4-freq temperament)]
    (.setValueAtTime (.-frequency synth) new-freq)))

(defn set-oscillator [synth oscillator]
  (let [osc (-> synth .-oscillator)]
    (.set osc #js {:type (name oscillator)})))