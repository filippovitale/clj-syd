


(defn rep? [x] (= [2 3] x))
(filter #(= [1 1] %) (generate-stations-version-3 (k->n 15)))

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