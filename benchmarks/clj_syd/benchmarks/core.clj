(ns clj-syd.benchmarks.core
  (:use perforate.core
        clj-syd.core))

(defgoal generate-stations-n22 "Generate the stations for n=22")

(defcase generate-stations-n22 :naive-implementation [] (generate-stations 22))
