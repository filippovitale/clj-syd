(ns clj-syd.uphill)

(def uphill-count
  (memoize (fn [qt acc [x y]]
             (if (empty? (retrieve-stations qt [x y])) ; TODO let!
               0
               (inc (reduce (fn [x y] (if (> x y) x y))
                      (map #(uphill-count qt (inc acc) %) (retrieve-stations qt [x y]))
                      ))))))
