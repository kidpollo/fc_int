(ns funding.core
  (:require [clojure.pprint :as pprint]))

(def ex-1 "A")                                          ; A
(def ex-2 "a2bB")                                       ; bB
(def ex-3 "2")                                          ; nil
(def ex-4 "")                                           ; nil
(def ex-5 "aaaaaaaaaaaaaaa3Ab")                         ; Ab
(def ex-6 "aAa3AAa")                                    ; aAa
(def ex-7 "aAa3AAaA")                                   ; AAaA

(defn foo
  [input-string]
  (->> (re-seq #"[^1-9]+" input-string)
       (filter #(re-find #"[A-Z]" %))
       (sort-by count)
       last))

(comment
  (foo ex-7)
)

(defn print-board [board]
  (pprint/print-table board))

(defn parse-int [s]
  (Integer/parseInt (re-find #"\A-?\d+" s)))

(defn parse-input [str-in]
  (re-matches #"(x|y)\s(\d)\s(\d)" str-in))

(defn play [game-state]
  (let [current-board (:current-board game-state)
        player  (:player game-state)
        x (:x game-state)
        y (:y game-state)
        new-board (assoc-in current-board [(parse-int x) (parse-int y)] player)]
    (do
      (print-board new-board)
      new-board)))

(defn show-results [board])

(defn -main [& [args]]
  (println "Hello lets play! Plz gimme your netxt play!")

  (let [board [{0 "" 1 "" 2 ""}
               {0 "" 1 "" 2 ""}
               {0 "" 1 "" 2 ""}]]
    (do
      (print-board board)

      (loop [current-board board]
        (let [input (read-line)
              [_ player x y] (parse-input input)
              game-state {:board current-board
                          :player player
                          :x x
                          :y y}
              end-game? #{%}
              validate #{%}]
          (if (end-game? end-game?)
            (show-results "")
            (recur (-> (validate game-state)
                       (play))))
          ))))
  )
