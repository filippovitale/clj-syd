(ns clj-syd.core)

(defn mod-pow [base n i]
  (.modPow
    (biginteger base) (biginteger i) (biginteger n)))

(defn generate-station
  [n i]
  [(mod-pow 2 n i) (mod-pow 3 n i)])

(defn generate-stations-version-1
  "Generate the stations for n"
  [n]
  (set
    (map #(generate-station n %)
      (range 0 (inc (* 2 n))))))

(defn generate-stations-version-2
  "Generate the stations for n - WITH DUPLICATES"
  [n]
  (map #(vector (mod-pow 2 n %) (mod-pow 3 n %))
    (range 0 (inc (* 2 n)))))


(defn g22
  [i]
  [(mod-pow 2 100000 i) (mod-pow 3 100000 i)])

(defn gv3 [s]
  (cons (g22 (first s))
    (lazy-seq (gv3 (rest s)))))

(defn a
  ([n] (generate-station n 0))
  ([n i] (lazy-cons (generate-station n i) (generate-station n (inc i)))))

(defn a
  ([n] (println n))
  ([n i] (println n i)))

;(defn generate-stations-version-3
;  "Stations generator for n"
;  (cons (#(vector (mod-pow 2 100000 %) (mod-pow 3 100000 %)) (first s))
;    (lazy-seq (generate-stations-version-3 (rest s)))))

;(defn sieve [s]
;  (cons (first s)
;    (lazy-seq (sieve (filter #(not= 0 (mod % (first s)))
;                       (rest s))))))
;
;user=> (take 20 (sieve (iterate inc 2)))
;(2 3 5 7 11 13 17 19 23 29 31 37 41 43 47 53 59 61 67 71)

(group-by
  #(identity (second %))
  (generate-stations 10000))

(defn -main
  "Project E*l*r - Problem 411"
  [& args]
  (println "Stations: " (generate-stations 22)))
