(ns clj-syd.core)

(import java.math.BigInteger)

(defn generate-stations
  "Generate the stations for n"
  [n]
  (set (map #(list
          (.modPow (biginteger 2) (biginteger %) (biginteger n))
          (.modPow (biginteger 3) (biginteger %) (biginteger n)))
    (range 0 (inc (* 2 n))))))

(defn -main
  "Project E*l*r - Problem 411"
  [& args]
  (println "Stations: " (generate-stations 22)))
