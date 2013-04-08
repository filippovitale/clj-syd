(ns clj-syd.stations-modmul)

(defn station-generator
  "Infinite Station Generator"
  [^long n]
  (iterate
    #(vec (list
            (mod (* 2 (first %)) n)
            (mod (* 3 (last %)) n)))
    ; x = 1; y = 1
    ; x = (3 * x) % n
    ; y = (2 * y) % n
    [1 1]))

;user=> (last (station-generator-with-duplicates (+ 3 1e6)))
;[4 9]
;user=> (last (take 2000007 (station-generator-inf (+ 3 1e6))))
;[4.0 9.0] <- [4 9] using ^long

; TODO (defrecord Aaa ^int aaa)

; benchmark
; (/ (- (. System (nanoTime)) nano-start) 1e6)

(defn station-generator
  "Generate the DISTINCT stations for n"
  [n]
  (reduce
    #(if (q/contain-station? %1 %2)
       (reduced %1)
       (q/insert-station %1 %2))
    q/empty-stations
    (station-generator-with-duplicates n)))
;(station-generator 22)
;=>  #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]}

