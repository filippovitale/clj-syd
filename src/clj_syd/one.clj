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
