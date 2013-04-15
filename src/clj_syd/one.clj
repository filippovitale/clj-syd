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


(defn -main [& args]
  (let
    [k (or (first args) "2")
     k->n (fn [k] (* k k k k k))
     n (k->n (Integer. ^String k))]
    (time
      (printf "k=%s n=%d stations=%s\n" k n
        (into ()
          (take 13 (station-generator n)) ;clojure.lang.LazySeq
        )))))


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
