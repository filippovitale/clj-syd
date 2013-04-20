(ns clj-syd.core)

(defn station-generator
  "Infinite Station Generator"
  [n]
  (if (= n 1)
    (cycle [[0 0]])
    (iterate
      #(let
         [[x y] %]
         [(mod (* 2 x) n)
          (mod (* 3 y) n)])
      [1 1])))

(defn station-structure-using-reduced [list-of-xy]
  (let [conj (fnil conj (sorted-set))]
    (time
      (reduce
        (fn [a [x y]]
          (if-not (get-in a [x y])
            (update-in a [x] conj y)
            (reduced a))) ;)
        (sorted-map)
        list-of-xy))))

(defn update-right
  [coll y]
  (let [i (count (filter #(>= y %) coll))]
    (assoc-in coll [i] y)
    ))

(defn solve
  [n]
  (count
    (reduce
      update-right
      (vector-of :int )
      (reduce into (vector-of :int )
        (vals
          (station-structure-using-reduced
            (station-generator n)))))))

(defn -main [& args]
  (let
    [k (Integer. ^String (or (first args) "7"))
     k->n (fn [k] (* k k k k k))
     n (k->n k)]
    (time
      (printf "k=%s n=%d uphill=%d\n" k n
        (solve n)))))
