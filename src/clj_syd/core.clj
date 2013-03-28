(ns clj-syd.core)

(defn mod-pow
  [base n i]
  (.modPow (biginteger base) (biginteger i) (biginteger n)))

(defn station
  [n i]
  [(mod-pow 2 n i) (mod-pow 3 n i)])

(defn station-generator
  "Generate the stations for n WITH DUPLICATES"
  [n]
  (for [i (range) :while (< i (inc n))]
    (station n i)))

(defn generate-stations
  "Generate the DISTINCT stations for n"
  [n]
  (set ; very inefficient (k->n 20) => 20 seconds)
    (station-generator n)))

(defn k->n [k] (* k k k k k))

(defn -main
  "Project E*l*r - Problem 411"
  [& args]
  (println "Longest uphill path for n=22: " (generate-stations 22))) ; TODO !!!!!!!!!!
