(ns clj-syd.core
  (:require [clojure.tools.cli :as cli]
            [clojure.pprint :as pp]
            ; [clj-syd.stations-modpow :as s1]
            [clj-syd.stations-modmul :as s2]
            [clj-syd.quadtree-type :as q3]
            [clj-syd.uphill :as u]
            ))

(set! *warn-on-reflection* true)

(defn solve
  [k]
  (let
    [k->n (fn [k] (* k k k k k))
     n (k->n k)
     stations (s2/station-generator n)
     ; TODO how to stop when duplicates come?
     ;(defn station-generator
     ;  "Generate the DISTINCT stations for n"
     ;  [n]
     ;  (reduce
     ;    #(if (q/contain-station? %1 %2)
     ;       (reduced %1)
     ;       (q/insert-station %1 %2))
     ;    q/empty-stations
     ;    (station-generator-with-duplicates n)))
     ;(station-generator 22)
     ;=>  #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]}

     quadtree (reduce q3/insert-station nil stations)]
    ; (u/uphill-count quadtree)))
    ))

(defn -main [& args]
  (let [[options args banner]
        (cli/cli args
          ["-k" "--k" "The value of k" :parse-fn #(Integer. ^String %) :default 3])
        k (:k options)]
    (time
      (pp/print-table [:k :path ]
        ; k
        ; longest-uphill-path
        [{:k 22 :path 5}
         {:k k :path (solve k)}])
      )))

; benchmark
; (/ (double (- (. System (nanoTime)) nano-start) 1e6))

;#'user/station-generator-inf
;user=>user=> (take 10 (station-generator-inf (+ 3 1e6)))
;([1 1] [2 3] [4 9] [8 27] [16 81] [32 243] [64 729] [128 2187] [256 6561] [512 19683])
;user=> (time (last (take 48600001 (station-generator-inf (* 30 30 30 30 30)))))
;"Elapsed time: 23087.22632 msecs"
;[12909376 4400001]
;user=> (last (take 2000007 (station-generator-inf (+ 3 1e6))))
;[4 9]
