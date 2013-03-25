(ns clj-syd.benchmarks.core
  (:use perforate.core
        clj-syd.core))

(defgoal generate-stations-n22 "Generate the stations for n=22")

(defcase generate-stations-n22 :loop-recur []
  (count
    (loop
      [[head & tail] (station-generator (k->n 15))
       result #{}]
      (if (contains? result head)
        result
        (recur tail (conj result head))))))

(defcase generate-stations-n22 :reduce-reduced []
  (count
    (reduce (fn [a v] (if-not (a v) (conj a v) (reduced a)))
        #{}
      (station-generator (k->n 15)))))
