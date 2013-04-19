(def n 500)
(def num-station 20)

(defn gen-data []
  (for [x (range num-station)]
    {:x (rand-int n), :y (rand-int n)}))


(def fff '( {:x 23, :y 404} {:x 268, :y 52} {:x 442, :y 28} {:x 143, :y 219}
            {:x 72, :y 399} {:x 39, :y 291} {:x 154, :y 464} {:x 128, :y 421}
            {:x 2, :y 429} {:x 227, :y 338} {:x 365, :y 55} {:x 332, :y 188}
            {:x 315, :y 422} {:x 111, :y 343} {:x 224, :y 496} {:x 78, :y 154}
            {:x 255, :y 339} {:x 154, :y 125} {:x 24, :y 382} {:x 473, :y 224}))
