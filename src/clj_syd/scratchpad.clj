(defn rep? [x] (= [2 3] x))
(filter #(= [1 1] %) (generate-stations-version-3 (k->n 15)))
(take 10 (station-generator (k->n 15)))

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

;(group-by
;  #(identity (second %))
;  (generate-stations 10000))


; REPL => (use 'clj-quadtree.core :reload-all)
;         (use '[cljts.geom :as g])
;         (use '[cljts.analysis :as a])
;(use 'clj-syd.core :reload-all)

(def generator '([1 1]
                  [2 3] [4 9] [8 5] [16 15] [10 1] [20 3] [18 9] [14 5] [6 15] [12 1]
                  [2 3] [4 9] [8 5] [16 15] [10 1] [20 3] [18 9] [14 5] [6 15] [12 1]
                  ....))

(def infinite-lazy-sequence
  (lazy-cat [4] (cycle [1 2 3])))

(def result
  (loop
    [input (vec generator)
     s #{}]
    (if (contains? s (first input))
      s
      (recur (rest input) (conj s (first input)))))
  )

(loop
  [input (vec generator)
   s #{}]
  (let [f (first input)]
    (if (contains? s f)
      s
      (recur (rest input) (conj s f)))))

(loop
  [[[x y] & r] (vec generator)
   s #{}]
  (if (contains? s [x y])
    s
    (recur r (conj s [x y]))))

(loop
  [[f & r] (vec generator)
   s #{}]
  (if (contains? s f)
    s
    (recur r (conj s f))))
(loop
  [[f & r] (seq generator)
   s #{}]
  (if (contains? s f)
    s
    (recur r (conj s f))))
(loop
  [[f & r] generator
   s #{}]
  (if (contains? s f)
    s
    (recur r (conj s f))))
;----------------------------------------
(loop
  [[head & tail] infinite-lazy-sequence
   result #{}]
  (if (contains? result head)
    result
    (recur tail (conj result head))))
;----------------------------------------
(time (count
        (loop
          [[head & tail] (station-generator (k->n 15))
           result #{}]
          (if (contains? result head)
            result
            (recur tail (conj result head))))))

(time (count
        (reduce
          (fn [a v] (if-not (a v) (conj a v) (reduced a)))
            #{}
          (station-generator (k->n 15)))))

(time (count ; anonymous version
        (reduce
          #(if-not (%1 %2) (conj %1 %2) (reduced %1)) #{}
          (station-generator (k->n 15)))))

;(time (count (set (station-generator-with-duplicates (k->n 20)))))
; very inefficient (k->n 20) => 42 seconds
;----------------------------------------

(defn r
  "Creates a random item for insertion. Mostly for testing."
  [max-x max-y]
  {:id (node-count)
   :bounds {:x (rand-int max-x)
            :y (rand-int max-y)
            :width (+ 5 (rand-int 10))
            :height (+ 5 (rand-int 10))}})

(defn a3 [quad]
  (reduce
    (fn [quad i]
      (insert quad (r 22 22)))
    quad
    (range 5)))

;-------------------------------------------

(use 'clj-quad.core :reload-all )

(def q22
  (quadtree
    {:depth 0
     :bounds {:x 0 :y 0 :width 22 :height 22}
     :nodes []}))

(def a1 (reduce insert q22
          [(point 1 1) (point 3 1) (point 1 4) (point 1 8) (point 1 9)]))

((first (retrieve-rect a1 (rect 0 0 22 22))) :bounds )


;(defprotocol Q
;  " Datastructure: A point Quad Tree for representing 2D data. Each
;    region has the same ratio as the bounds for the tree.
;    The implementation currently requires pre-determined bounds for data as it
;    can not rebalance itself to that degree."
;  (q-constructor [this] "Constructs a new quad tree")
;  (q-root_ [this] "new goog.structs.QuadTree.Node(minX, minY, maxX - minX, maxY - minY)")
;  (q-count_ [this] "0")
;  (q-count_ [this] "0")
;  (q-set [this] "Sets the value of an (x, y) point within the quad-tree."))

;(defrecord tn [v l r])

;user=> (defrecord Point3 [x y])
;user.Point3
;user=> (def p3 (Point3. 11 12))
;#'user/p3
;user=> (println p3)
;#:user.Point3{:x 11, :y 12}
;nil
;user=> (println (:x p3)) ; works
;11
;nil
;user=> (println (.y p3)) ; also works!
;12
;nil


;(for [i (range) :while (< i (inc (* 2 n)))] 10% slower
;(for [i (range 0 (inc (* 2 n)))] faster

;http://clojure.github.com/clojure/clojure.core-api.html#clojure.core/reduced
;https://news.ycombinator.com/item?id=5304949

;Try deftype and/or defrecord
;http://stackoverflow.com/questions/2944108/implementing-custom-data-structures-using-clojure-protocols
;http://techbehindtech.com/2010/11/15/clojure-defrecord-deftype/

; improve mod-pow
;(map first (station-generator-with-duplicates 22))
;(1 2 4 8 16 10 20 18 14 6 12 2 4 8 16 10 20 18 14 6 12 2 4 8 16 10 20 18 14 6 12 2 4 8 16 10 20 18 14 6 12 2 4 8 16)
;(map last (station-generator-with-duplicates 22))
;(1 3 9 5 15 1 3 9 5 15 1 3 9 5 15 1 3 9 5 15 1 3 9 5 15 1 3 9 5 15 1 3 9 5 15 1 3 9 5 15 1 3 9 5 15)

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

