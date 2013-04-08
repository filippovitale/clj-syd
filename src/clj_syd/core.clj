(ns clj-syd.core
  (:require [clj-syd.uphill :as u]
            [clj-syd.quadtree :as q]))

(defn mod-pow
  [base n i]
  (.intValue
    (.modPow (biginteger base) (biginteger i) (biginteger n))))

(defn station
  [n i]
  [(mod-pow 2 n i) (mod-pow 3 n i)])

(defn station-generator-with-duplicates
  "Generate the stations for n WITH DUPLICATES"
  [n]
  (for [i (range 0 (inc (* 2 n)))]
    (station n i)))

(defn s2gen
  "Experiment..."
  [n]
  (for [i (range 0 (inc (* 2 n)))]
    (station n i)
    ; x = 1; y = 1
    ; x = (3 * x) % n
    ; y = (2 * y) % n

    ;(map inc (take 5 (repeat 3)))
    ;(map inc (take 5 (repeat 2)))

    ))

; Co-Recursion in Clojure
; http://squirrel.pl/blog/2010/07/26/corecursion-in-clojure/
(def fib-seq
  (lazy-cat
    [0 1]
    (map + fib-seq (rest fib-seq))))

;http://rosettacode.org/wiki/Fibonacci_sequence#Clojure
(defn fibs []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

;try
(defn fib-seq []
  ((fn rfib [a b]
     (lazy-cons a (rfib b (+ a b))))
    0 1))

; lazy-cons + lazy-seq ==> ITERATE !!!!
;(def newton (iterate (fn[x] (/ (+ x (/ 2.0 x)) 2)) 2))
;(take 5 newton)
(def n-c 22)
(def i-c 3)

(defn m2
  [i n]
  (iterate
    #(mod (* i %) n)
    1))

(take 45 (m2 2 22))

(defn m
  [n]
  (iterate
    #([% %])
    [1 1]))

(take 45 (m 22))

(defn m
  [n]
  (iterate
    #([mod (* 2 (first %)) n
       mod (* 3 (last %)) n])
    [1 1]))

(take 45 (m 22))

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

(defn k->n [k] (* k k k k k))

(defn solve
  [n]
  (let
    [stations (station-generator (k->n n))]
    (u/uphill-count stations 0 [-1 -1])))

(defn -main
  "Pr*j*ct E*l*r - Pr*bl*m 411"
  [& args]
  (println "Longest uphill path for k=3: " (solve 5)))
