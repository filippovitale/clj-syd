(def fib-seq
  ((fn rfib [a b]
     (lazy-seq (cons a (rfib b (+ a b)))))
    0 1))
;(take 20 fib-seq)


(def fib-seq
  (lazy-cat [0 1]
    (map + fib-seq (rest fib-seq))))
;(take 20 fib-seq)

;(defn seq-fn [builder-fn num ss]
;  (cons
;    (builder-fn (take num ss))
;    (lazy-seq (seq-fn builder-fn (inc num) ss))))
;
;(def lazy-primes
;  (lazy-cat [2 3 5] (seq-fn next-prime 3 lazy-primes)))

(defn fff
  [a b]
  (fn rrr [a b]
    (lazy-seq (cons a (rrr b (+ a b))))
    )
  )


;user=> (cons 1 [2 3 4])
;(1 2 3 4)
;user=> (take 2 (lazy-seq (cons 1 [2 3 4])))
;(1 2)
;user=> (take 10 (lazy-seq (g22 1)))
;(2 3)

;; http://stackoverflow.com/questions/12206806/how-can-i-create-a-lazy-seq-vector
;(defn long-seq-vec [n]
;  (lazy-seq (cons
;              (vector n {:somekey (* n 2)})
;              (long-seq-vec (+ n 1)))))
;
;(take 3 (long-seq-vec 3))
;
;=> ([3 {:somekey 6}] [4 {:somekey 8}] [5 {:somekey 10}])
;Or as an alternative, you can use for which is lazy in itself:
;
;(defn long-seq-vec [n]
;  (for [x (iterate inc n)]
;    (vector x {:somekey (* x 2)})))

(defn mod-pow
  [base n i]
  (.modPow (biginteger base) (biginteger i) (biginteger n)))

(defn station
  [n i]
  [(mod-pow 2 n i) (mod-pow 3 n i)])

(defn generate-stations-version-3
  "Generate the stations for n"
  [n]
  (for [i (range) :while (< i (inc n))]
    (station n i)))

(defn nk [k] (* k k k k k))

(take 10 generate-stations-version-3(nk 3))

(defn rep? [x] (= [2 3] x))
(filter #(= [1 1] %) (generate-stations-version-3 (nk 15)))

;(some #{7} [9 8 9 7 6])
;-> 7
;(sorted-set 3 2 1 1)
;-> #{1 2 3}

;; find a whether a word is in a list of words.
;(def word "foo")
;(some (partial = word) words)

; vectors of maps
; http://stackoverflow.com/questions/3052162/coverting-a-vector-of-maps-to-map-of-maps-in-clojure

; http://en.wikipedia.org/wiki/Fenwick_tree

;user=> (zipmap [1 2 3] [10 20 40])
;{3 40, 2 20, 1 10}
;user=> (first (zipmap [1 2 3] [10 20 40]))
;[3 40]
;user=> (last (zipmap [1 2 3] [10 20 40]))
;[1 10]

(defn dup? [x] (= 2 x))
(take-while dup? [2 2 1 2])


;user=> (time (take 10 (generate-stations-version-3 10000000000000000000000000)))
;"Elapsed time: 0.033585 msecs"
;([1 1] [2 3] [4 9] [8 27] [16 81] [32 243] [64 729] [128 2187] [256 6561] [512 19683])