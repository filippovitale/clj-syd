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
  (time
    (take 11 (station-generator 22)) ;clojure.lang.LazySeq
    ))
