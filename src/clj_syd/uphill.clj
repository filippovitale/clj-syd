(ns clj-syd.uphill
  (:require [clj-syd.quadtree :as q]))

(def uphill-count
  ; TODO memoize must be cleared or I must create a closure to generate diferent uphill functions
  (memoize (fn [qt acc [x y]]
             (let
               [next-station (q/retrieve-stations qt [x y])]
               (if (empty? next-station)
                 0
                 (inc (reduce (fn [x y] (if (> x y) x y))
                        (map #(uphill-count qt (inc acc) %) next-station))))))))
