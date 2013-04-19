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

(defn update-right
  [coll y]
  ; TODO find a more efficient (*BISECT*) way to -> (count (filter #(>= y %) in))
  (let [i (count (filter #(>= y %) coll))]
    (assoc-in coll [i] y)
    ))

(defn fff
  [coll]
  vals coll)

(defn solve
  [n]
  (count
    (reduce
      update-right
      (vector-of :int ) ; same as []
      (reduce into [] ; TODO maybe double cycle?
        (vals
          (station-structure-using-reduced
            (station-generator n)))))))

;(vals (station-structure-using-reduced
;        (station-generator n)))
;(#{1} #{3} #{9} #{15} #{5} #{1} #{1} #{5} #{15} #{9} #{3})

(defn -main [& args]
  (let
    [k (Integer. ^String (or (first args) "2"))
     k->n (fn [k] (* k k k k k))
     n (k->n k)]
    ;s (into () (take (* 2 n) (station-generator n)))]
    (time
      (printf "k=%s n=%d stations-x=%d\n" k n
        (solve n)))))

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

;(def in '[1 2 3 4 9 18])
;(def out '[1 2 3 4 6 18])
;
;(= (assoc-in in [4] 6) out) ; true
;
;;(count (filter #(> 6 %) in))
;;=> 4
;
;(= (assoc-in in [(count (filter #(> 6 %) in))] 6) out) ; true
;
;;(assoc-in '[1 2 3 4] [4] 6) ; !!!!! after the end of vec!!!!
;;[1 2 3 4 6]
;
