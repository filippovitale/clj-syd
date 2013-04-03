(ns clj-syd.quadtree
  (:require [clojure.core.reducers :as r]))

(defn stations [size] [])

(defn contain-station?
  [qt [x y]]
  (qt [x y]))

(defn insert-station [qt [x y]]
  (conj qt [x y])) ; fake implementations to test the interface
;(def q (insert-station (insert-station (quadtree 22) [1 2]) [5 9]))

(defn retrieve-stations
  ([qt]
    (retrieve-stations qt [-1 -1]))
  ([qt [x y]]
    ;(r/filter #(and
    (filter #(and
               (>= (first %) x)
               (>= (last %) y)
               (not= [x y] %)) qt)))
;(retrieve-stations q [1 2])


(defrecord QuadTree [s ll lg gl gg])

(def a (QuadTree. [1 1] nil nil nil nil))

;=>  #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]}

(def b (QuadTree. [8 5]
         (QuadTree. [1 1] nil nil nil nil) ; ll
         (QuadTree. [4 9] nil nil nil nil) ; lg
         (QuadTree. [12 1] nil nil nil nil) ; gl
         (QuadTree. [18 9] nil nil nil nil))) ; gg

(def c (QuadTree. [8 5]
         (QuadTree. [1 1] nil nil nil
           (QuadTree. [2 3] nil nil nil nil))
         (QuadTree. [4 9] nil nil nil
           (QuadTree. [6 15] nil nil nil nil))
         (QuadTree. [12 1] nil
           (QuadTree. [10 1] nil nil nil nil)
           nil
           (QuadTree. [20 3] nil nil nil nil))
         (QuadTree. [18 9]
           (QuadTree. [14 5] nil nil nil nil)
           (QuadTree. [12 1] nil nil nil nil)
           nil nil)))

(def g #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]})

(defn i-s
  [qt [x y]]
  (if (nil? qt)
    (QuadTree. [x y] nil nil nil nil)
    (let [lx (first (:s qt))
          ly (last (:s qt))
          ll (:ll qt)
          lg (:lg qt)
          gl (:gl qt)
          gg (:gg qt)]
      (cond
        (and (<, x lx) (<, y ly)) (QuadTree. [lx ly] (if (nil? ll) (QuadTree. [x y] nil nil nil nil) (i-s ll [x y])) lg gl gg)
        (and (<, x lx) (>= y ly)) (QuadTree. [lx ly] ll (if (nil? lg) (QuadTree. [x y] nil nil nil nil) (i-s lg [x y])) gl gg)
        (and (>= x lx) (<, y ly)) (QuadTree. [lx ly] ll lg (if (nil? gl) (QuadTree. [x y] nil nil nil nil) (i-s gl [x y])) gg)
        (and (>= x lx) (>= y ly)) (QuadTree. [lx ly] ll lg gl (if (nil? gg) (QuadTree. [x y] nil nil nil nil) (i-s gg [x y])))
        ))))

; start from root
; x >= g* else l*
; y >= *g else *l
; nil? new_QT_there else that_s_the_root
; recur

;user=> (pprint (reduce #(i-s %1 %2) nil (vec g)))
;{:s [16 15],
; :ll
; {:s [1 1],
;  :ll nil,
;  :lg nil,
;  :gl nil,
;  :gg
;  {:s [2 3],
;   :ll nil,
;   :lg nil,
;   :gl
;   {:s [12 1],
;    :ll nil,
;    :lg {:s [10 1], :ll nil, :lg nil, :gl nil, :gg nil},
;    :gl nil,
;    :gg nil},
;   :gg
;   {:s [4 9],
;    :ll nil,
;    :lg nil,
;    :gl
;    {:s [14 5],
;     :ll nil,
;     :lg {:s [8 5], :ll nil, :lg nil, :gl nil, :gg nil},
;     :gl nil,
;     :gg nil},
;    :gg nil}}},
; :lg {:s [6 15], :ll nil, :lg nil, :gl nil, :gg nil},
; :gl
; {:s [20 3],
;  :ll nil,
;  :lg {:s [18 9], :ll nil, :lg nil, :gl nil, :gg nil},
;  :gl nil,
;  :gg nil},
; :gg nil}
;nil
