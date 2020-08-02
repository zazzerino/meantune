(ns meantune.theory-test
  (:require [cljs.test :refer (deftest is)]
            [meantune.theory :as t]))

(def round js/Math.round)

(deftest parse-note-test
  (is (= (t/parse-note "C4")
         {:white-key "C" :accidental "" :octave 4 :pitch-class 0}))
  (is (= (t/parse-note "C#4")
         {:white-key "C" :accidental "#" :octave 4 :pitch-class 1}))
  (is (= (t/parse-note "Ab6")
         {:white-key "A" :accidental "b" :octave 6 :pitch-class 8}))
  (is (= (t/parse-note "D##2")
         {:white-key "D" :accidental "##" :octave 2 :pitch-class 4}))
  (is (= (t/parse-note "Dbb4")
         {:white-key "D" :accidental "bb" :octave 4 :pitch-class 0})))

(deftest interval-frequency-test
  (is (= (t/interval-frequency 440 0 :equal)
         440))
  (is (= (t/interval-frequency 440 12 :equal)
         880))
  (is (= (t/interval-frequency 440 -12 :equal)
         220))
  (is (= (round (t/interval-frequency 440 7 :equal))
         (round 659.25)))
  (is (= (t/interval-frequency 330 24 :equal)
         1320))
  (is (= (t/interval-frequency 330 24 :equal)
         1320))
  (is (= (t/interval-frequency 440 7 :pythagorean)
         660))
  (is (= (t/interval-frequency 440 4 :quarter-comma)
         550))
  (is (= (round (t/interval-frequency 440 5 :just))
         (round 586.66))))

(deftest half-steps-from-d4-test
  (is (= (t/half-steps-from-d4 "D4")
         0))
  (is (= (t/half-steps-from-d4 "Db3")
         -13))
  (is (= (t/half-steps-from-d4 "G#5")
         18)))

(deftest frequency
  (is (= (t/frequency "A4" 440 :equal)
         440))
  (is (= (t/frequency "E5" 440 :just)
         660))
  (is (= (t/frequency "E4" 440 :pythagorean)
         330))
  (is (= (round (t/frequency "C#5" 440 :quarter-comma))
         (round 550)))
  )
