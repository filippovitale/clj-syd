(ns clj-syd.core
  (:gen-class ))

(defn generate-stations
  "Generate the stations for n"
  [n]
  (range 1 n)) ; TODO

(defn -main
  "Project E*l*r - Problem 411"
  [& args]
  (println "Stations (count): " (count (generate-stations 22))))
