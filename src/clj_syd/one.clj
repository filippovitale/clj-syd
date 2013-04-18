(ns clj-syd.one)

;(set! *warn-on-reflection* true)

(defn station-generator
  "Infinite Station Generator"
  [n]
  (iterate
    #(let
       [[x y] %]
       [(mod (* 2 x) n)
        (mod (* 3 y) n)])
    [1 1]))

(defn station-structure-using-reduced [list-of-xy]
  (let [conj (fnil conj (sorted-set))]
    (reduce
      (fn [a [x y]]
        (if-not (get-in a [x y])
          (update-in a [x] conj y)
          (reduced a))) ;)

      ; TODO don't know why it is not working
      ;(update-in (sorted-map) [1] conj (int 1))
      ;(update-in (sorted-map) [1] conj 1)
      ;(into (sorted-map) {1 (sorted-set 1)})
      ;(drop 1 list-of-xy))))

      (sorted-map)
      list-of-xy)))

(defn algo
  (println "TODO")) ; TODO
;(if (subseq s > 7) "Y" (conj s 7))
;(if-not (subseq s > 3) (conj s 7) "SUB")
; s is ordered but it is NOT a set!!!
;(into (vector-of :int) [])


(defn -main [& args]
  (let
    [k (Integer. ^String (or (first args) "2"))
     k->n (fn [k] (* k k k k k))
     n (k->n k)
     s (into () (take (* 2 n) (station-generator n)))]
    (time
      (printf "k=%s n=%d stations-x=%s\n" k n
        (count (station-structure-using-reduced s))
        ))))

;(->> (for [x (range 1e3) y (range 1e3)] [x y])
;  (station-structure)
;  (last)
;  (key)
;  (time))

; then for the algorythm:
; http://clojuredocs.org/clojure_core/clojure.core/subseq

;(def in
;  (list
;    {1 1} {1 2} {1 3}
;    {2 4} {2 5} {2 6}))
;=>#'user/in
;(mergeMatches in)
;=> {2 #{4 5 6}, 1 #{1 2 3}}
;(filter #(> (key %) 1) (mergeMatches in))
;=> ([2 #{4 5 6}])

;(defn vrange [n]
;  (loop [i 0 v []]
;    (if (< i n)
;      (recur (inc i) (conj v i))
;      v)))
;
;(defn vrange2 [n]
;  (loop [i 0 v (transient [])]
;    (if (< i n)
;      (recur (inc i) (conj! v i))
;      (persistent! v))))
;
;(time (def v (vrange 1000000)))
;"Elapsed time: 297.444 msecs"
;
;(time (def v2 (vrange2 1000000)))
;"Elapsed time: 34.428 msecs"

;user=> (def g22 (into (sorted-map) (group-by #(first %) s22)))
;#'user/g22
;user=> (filter #(> (key %) 13) g22)
;([14 [[14 5]]] [16 [[16 15]]] [18 [[18 9]]] [20 [[20 3]]])

;(defn list-of-xy->sorted-map-of-sorted-sets [list-of-xy]
;  "Take a list (or lazy-seq) of 2-tuple and return a sorted-map of sorted-sets"
;  (reduce ????? list-of-xy))
;
;(def in
;  '([1 9] [1 8] [1 7]
;     [2 1] [2 2] [2 3]
;     [2 1] [2 2] [2 3]
;     [2 1] [2 2] [2 3]))
;
;(def out
;  (into (sorted-map)
;    {1 (sorted-set 9 8 7)
;     2 (sorted-set 1 2 3)}))
;(type out)
;=> clojure.lang.PersistentTreeMap
;(type (out 1))
;=> clojure.lang.PersistentTreeSet

; these are not working with infinite sequences
;(defn station-structure
;[stations]
;(into (sorted-map)
;  (map (fn [[k v]]
;         [k (apply sorted-set (map second v))])
;    (group-by first stations))))
;
;(defn station-structure-using-reduce [list-of-xy]
;  (let [conj (fnil conj (sorted-set))]
;    (reduce (fn [acc [x y]]
;              (update-in acc [x] conj y))
;      (sorted-map)
;      list-of-xy)))
