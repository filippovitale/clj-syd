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
