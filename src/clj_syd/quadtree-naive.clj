(ns clj-syd.quadtree-naive)

(def empty #{})

(defn insert-station [qt [x y]] (conj qt [x y]))

(defn retrieve-stations
  ([qt]
    (retrieve-stations qt [-1 -1]))
  ([qt [x y]]
    (filter #(and
               (>= (first %) x)
               (>= (last %) y)
               (not= [x y] %)) qt)))

(defn contain-station? [qt [x y]] (qt [x y]))

;Examples
; (def q (insert-station (insert-station empty [1 2]) [5 9]))
; (retrieve-stations q [1 2])
; (contain-station? q [1 2])
