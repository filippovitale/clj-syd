(ns clj-syd.one)

(set! *warn-on-reflection* true)

(defn station-generator
  "Infinite Station Generator"
  [n]
  (iterate
    #(let
       [[x y] %]
       [(mod (* 2 x) n)
        (mod (* 3 y) n)])
    [1 1]))

(def s22
  (into ()
    (take 13 (station-generator 22)) ;clojure.lang.LazySeq
    ))

; http://stackoverflow.com/questions/2203213/merge-list-of-maps-and-combine-values-to-sets-in-clojure
; http://clj-me.cgrand.net/2009/06/08/linear-interpolation-and-sorted-map/
(def t22
  (into (sorted-map) s22))

(def i1
  (list
    [1 1] [1 2] [1 3]
    [2 4] [2 5] [2 6]))

;(def t22
;  (reduce
;    #(if (q3/contain-station? %1 %2)
;       (reduced %1)
;       (q3/insert-station %1 %2))
;    nil
;    stations))


; then for the alghorytm:
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

(defn -main [& args]
  (let
    [k (or (first args) "2")
     k->n (fn [k] (* k k k k k))
     n (k->n (Integer. ^String k))]
    (time
      (printf "k=%s n=%d stations=%s\n tree=%s\n" k n s22
        t22
        ))))

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

;(defn into
;  "Returns a new coll consisting of to-coll with all of the items of
;  from-coll conjoined."
;  {:added "1.0"
;   :static true}
;  [to from]
;  (if (instance? clojure.lang.IEditableCollection to)
;    (with-meta (persistent! (reduce conj! (transient to) from)) (meta to))
;    (reduce conj to from)))
