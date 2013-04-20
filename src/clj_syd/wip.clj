(ns clj-syd.wip)

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

        ; TODO don't know why it is not working
        ;(update-in (sorted-map) [1] conj (int 1))
        ;(update-in (sorted-map) [1] conj 1)
        ;(into (sorted-map) {1 (sorted-set 1)})
        ;(drop 1 list-of-xy))))

        (sorted-map)
        list-of-xy))))

(defn update-right
  [coll y]
  ; TODO find a more efficient (*BISECT*) way to -> (count (filter #(>= y %) in))
  (let [i (count (filter #(>= y %) coll))]
    (assoc-in coll [i] y)
    ))

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

(defn -main [& args]
  (let
    [k (Integer. ^String (or (first args) "2"))
     k->n (fn [k] (* k k k k k))
     n (k->n k)]
    ;s (into () (take (* 2 n) (station-generator n)))]
    (time
      (printf "k=%s n=%d stations-x=%d\n" k n
        (solve n)))))
