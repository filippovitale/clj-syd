(ns clj-syd.uphill
  (:require [clj-syd.quadtree :as q]))

(def uphill-count
  (memoize (fn [qt acc [x y]]
             (if (empty? (q/retrieve-stations qt [x y])) ; TODO let!
               0
               (inc (reduce (fn [x y] (if (> x y) x y))
                      (map #(uphill-count qt (inc acc) %) (q/retrieve-stations qt [x y]))
                      ))))))
