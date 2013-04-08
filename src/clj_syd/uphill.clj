(ns clj-syd.uphill
  (:require [clj-syd.quadtree-set :as q1]
            ;[clj-syd.quadtree-record :as q2]
            ))

(def uphill-count-rec
  (memoize (fn [qt acc [x y]]
             (let
               [next-station (q1/retrieve-stations qt [x y])]
               (if (empty? next-station)
                 0
                 (inc (reduce (fn [x y] (if (> x y) x y))
                        (map #(uphill-count-rec qt (inc acc) %) next-station))))))))

(defn uphill-count
  [stations]
  (uphill-count-rec stations 0 [-1 -1]))

; TODO memoize must be cleared or I must create a closure to generate different uphill functions
