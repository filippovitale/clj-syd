(ns clj-syd.stations-modpow)

;(defn modpow
;  [base n i]
;  (.intValue
;    (.modPow (biginteger base) (biginteger i) (biginteger n))))

;(defn station-generator
;  "Generate the stations for n WITH DUPLICATES"
;  [n]
;  (for [i (range 0 (inc (* 2 n)))]
;    [(modpow 2 n i) (modpow 3 n i)]))


;user=> (take 11 (station-generator 22))
;([1 1] [2 3] [4 9] [8 5] [16 15] [10 1] [20 3] [18 9] [14 5] [6 15] [12 1])

;user=> (type (first (last (take 11 (station-generator 22)))))
;java.lang.Integer
