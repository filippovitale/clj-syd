(ns clj-syd.stations-modmul)

(set! *warn-on-reflection* true)

(defn station-generator
  "Infinite Station Generator"
  [n]
  (iterate
    #(let
       [[x y] %]
       [(mod (* 2 x) n)
        (mod (* (int 3) y) n)])
    [1 1]))


(defrecord Station [^int x ^int y])

(defn station-generator
  "Infinite Station Generator"
  [n]
  (iterate
    #(let
       [x (.-x %)
        y (.-y %)]
       (Station.
         (mod (* 2 x) n)
         (mod (* (int 3) y) n)))
    (Station. 1 1)))

(deftype Station [^int x ^int y])
(defn station-generator
  "Infinite Station Generator"
  [n]
  (iterate
    #(let
       [x (.x %)
        y (.y %)]
       (Station.
         (mod (* 2 x) n)
         (mod (* (int 3) y) n)))
    (Station. 1 1)))

; 1-2% faster with:
;   [(mod (bit-shift-left x 1) n)
;   [[^int x ^int y] %]
;   unchecked-multiply-int

; Slower if changing with:
;   [n (int n)

; benchmark
; (/ (- (. System (nanoTime)) nano-start) 1e6)

;(defn station-generator
;  "Generate the DISTINCT stations for n"
;  [n]
;  (reduce
;    #(if (q/contain-station? %1 %2)
;       (reduced %1)
;       (q/insert-station %1 %2))
;    q/empty-stations
;    (station-generator-with-duplicates n)))
;(station-generator 22)
;=>  #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]}



;(def foo 10)
;(time (dotimes [i 1000000] (Math/abs foo)))
;=> "Elapsed time: 3032.741902 msecs"
;(time (dotimes [i 1000000] (Math/abs (long foo))))
;=> "Elapsed time: 16.226857 msec
;(time (dotimes [i 1000000]
;        (let [baz 10] (Math/abs baz))))
;=> "Elapsed time: 5.586289 msecs"


;(defn fib ^long [^long n]
;    (if (<= n 1)
;        1
;        (+ (fib (dec n)) (fib (- n 2)))))
