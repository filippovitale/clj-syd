(ns clj-syd.core
  (:require [clojure.tools.cli :as cli]
            [clj-syd.stations-modpow :as s1]
            [clj-syd.stations-modmul :as s2]
            [clj-syd.uphill :as u]
            ))

(set! *warn-on-reflection* true)

(defn solve
  [k]
  (let
    [k->n (fn [k] (* k k k k k))
     n (k->n k)
     stations (s2/station-generator n)]
    (u/uphill-count stations)))

(defn -main
  "Pr*j*ct E*l*r - Pr*bl*m 411"
  [& args]
  (time
    (let [[options args banner]
          (cli/cli args
            ["-k" "--k" "The value of k" :parse-fn #(Integer. ^String %) :default 3])
          k (:k options)]
      (println "Longest uphill path for k =" k ": " (solve k)))))

; benchmark
; (/ (- (. System (nanoTime)) nano-start) 1e6)

; TODO clojure.pprint/print-table
;user=> (clojure.pprint/print-table [:name :initial-impression]
;         [{:name "Rich" :initial-impression "rock star"}
;          {:name "Andy" :initial-impression "engineer"}])
;| :name | :initial-impression |
;|-------+---------------------|
;|  Rich |           rock star |
;|  Andy |            engineer |

;#'user/station-generator-inf
;user=>user=> (take 10 (station-generator-inf (+ 3 1e6)))
;([1 1] [2 3] [4 9] [8 27] [16 81] [32 243] [64 729] [128 2187] [256 6561] [512 19683])
;user=> (time (last (take 48600001 (station-generator-inf (* 30 30 30 30 30)))))
;"Elapsed time: 23087.22632 msecs"
;[12909376 4400001]
;user=> (last (take 2000007 (station-generator-inf (+ 3 1e6))))
;[4 9]
