(ns meantune.specs
  (:require [cljs.spec.alpha :as s]))

(def note-regex #"^([a-gA-G])(#{1,2}|b{1,2})?(\d)$")

(s/def ::app-name string?)
(s/def ::a4-freq number?)
(s/def ::temperament #{:just :pythagorean :quarter-comma :equal})
(s/def ::sustain? boolean?)
(s/def ::note-name (and string? #(re-matches note-regex %)))
(s/def ::synth (comp not nil?))

(s/def ::note (s/keys :req-un [::synth
                               ::note-name
                               ::a4-freq
                               ::temperament]))

(s/def ::notes (s/coll-of ::note :kind vector?))
