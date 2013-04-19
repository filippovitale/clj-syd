;(deftype Station [^int x ^int y])
;(defn station-generator
;  "Infinite Station Generator"
;  [n]
;  (iterate
;    #(let
;       [x (.x %)
;        y (.y %)]
;       (Station.
;         (mod (* 2 x) n)
;         (mod (* (int 3) y) n)))
;    (Station. 1 1)))
; slower
;
;(defrecord Station [^int x ^int y])
;(defn station-generator
;  "Infinite Station Generator"
;  [n]
;  (iterate
;    #(let
;       [x (.-x %)
;        y (.-y %)]
;       (Station.
;         (mod (* 2 x) n)
;         (mod (* (int 3) y) n)))
;    (Station. 1 1)))
; slower
