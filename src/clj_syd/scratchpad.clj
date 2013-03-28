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
        (reduce (fn [a v] (if-not (a v) (conj a v) (reduced a)))
            #{}
          (station-generator (k->n 15)))))



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

((first (retrieve-rect a1 (rect 0 0 22 22))) :bounds)


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
