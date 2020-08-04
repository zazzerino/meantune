(ns meantune.events
  (:require [re-frame.core :as re-frame]
            [meantune.db :as db]
            [meantune.effects]
            [meantune.theory :as theory]
            [meantune.synth :as synth]))

(defn- filter-by-note-name [note-name notes]
  (first (filter #(= (:note-name %) note-name)
                 notes)))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 :play-note
 [(re-frame/inject-cofx :synth)]
 (fn [{:keys [db synth]} [_ note-name]]
   (let [note {:synth synth
               :note-name note-name
               :a4-freq (:a4-freq db)
               :temperament (:temperament db)}]
     (if (filter-by-note-name note-name (:notes db)) ; note is already playing
       {:db db}
       {:db (update-in db [:notes] conj note)
        :play-note! note}))))

(re-frame/reg-event-fx
 :stop-note
 (fn [{:keys [db]} [_ note-name]]
   (let [note (filter-by-note-name note-name (:notes db))
         new-notes (filterv #(not= note-name (:note-name %))
                            (:notes db))]
     {:db (assoc db :notes new-notes)
      :stop-note! note})))

(re-frame/reg-event-fx
 :stop-all
 (fn [{:keys [db]} _]
   {:db (assoc db :notes [])
    :stop-all! {:notes (:notes db)}}))

(re-frame/reg-event-fx
 :toggle-sustain
 (fn [{:keys [db]} _]
   {:db (-> db (update :sustain? not) (update :notes empty))
    :stop-all! (select-keys db [:notes])}))

(re-frame/reg-event-fx
 :keydown
 (fn [{:keys [db]} [_ note-name]]
   {:db db
    :dispatch [:play-note note-name]}))

(re-frame/reg-event-fx
 :keyup
 (fn [{:keys [db]} [_ note-name]]
   (merge {:db db}
          (if-not (:sustain? db)
            {:dispatch [:stop-note note-name]}))))

(re-frame/reg-event-fx
 :change-temperament
 (fn [{:keys [db]} [_ temperament]]
   {:db (assoc db :temperament temperament)
    :retune! {:notes (:notes db)
              :a4-freq (:a4-freq db)
              :temperament temperament}}))

;; (re-frame/reg-event-fx
;;  :synth/change-temperament
;;  (fn [{:keys [db]} [_ temperament]]
;;    {:db (assoc db :temperament temperament)
;;     :synth/retune! (merge (select-keys db [:synths :a4-frequency])
;;                           {:temperament temperament})}))
