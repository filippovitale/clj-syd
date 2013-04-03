(ns clj-syd.core
  (:require [clj-syd.uphill :as u]
            [clj-syd.quadtree :as q]))

(defn mod-pow
  [base n i]
  (.intValue
    (.modPow (biginteger base) (biginteger i) (biginteger n))))

(defn station
  [n i]
  [(mod-pow 2 n i) (mod-pow 3 n i)])

(defn station-generator-with-duplicates
  "Generate the stations for n WITH DUPLICATES"
  [n]
  (for [i (range 0 (inc (* 2 n)))]
    (station n i)))

(defn station-generator
  "Generate the DISTINCT stations for n"
  [n]
  (reduce
    #(if (q/contain-station? %1 %2) (reduced %1) (q/insert-station %1 %2)) ; TODO instead of q/insert-station --> conj
      #{} ; TODO empty QuadTree q/empty
    (station-generator-with-duplicates n)))
;http://clojure.github.com/clojure/clojure.core-api.html#clojure.core/reduced
;https://news.ycombinator.com/item?id=5304949

;Try deftype and/or defrecord
;http://stackoverflow.com/questions/2944108/implementing-custom-data-structures-using-clojure-protocols
;http://techbehindtech.com/2010/11/15/clojure-defrecord-deftype/

;(station-generator 22)
;=>  #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]}

(defn k->n [k] (* k k k k k))

(defn solve
  [n]
  (let [qt (station-generator (k->n n))]
    (u/uphill-count qt 0 [-1 -1])))

(defn -main
  "Pr*j*ct E*l*r - Pr*bl*m 411"
  [& args]
  (println "Longest uphill path for k=3: " (solve 3)))

;(load-file "C:/Users/filippo/Experiments/clj-syd/src/clj_syd/core.clj")
;(use 'clj-syd.core)
