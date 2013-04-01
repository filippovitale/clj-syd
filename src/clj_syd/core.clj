(ns clj-syd.core
  (:use [clojure.tools.trace :only [deftrace]]))

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
    #(if-not (%1 %2) (conj %1 %2) (reduced %1)) #{}
    (station-generator-with-duplicates n)))

(defn k->n [k] (* k k k k k))

(defn solve
  [n]
  (let [qt (station-generator (k->n n))]
    (uphill-count qt 0 [-1 -1])))

(defn -main
  "Project E*l*r - Problem 411"
  [& args]
  (println "Longest uphill path for n=22: " (solve 22)))
