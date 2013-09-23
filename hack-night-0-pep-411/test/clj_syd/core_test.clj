(ns clj-syd.core-test
  (:use clojure.test
        clj-syd.core))

(defn unique-stations
  [n]
  (count
    (reduce
      (fn [a x]
        (if (contains? a x)
          (reduced a)
          (conj a x)))
      (set nil) (station-generator n))))

(deftest generate-stations-n22-test
  (testing
    (is (= 11 (unique-stations 22)))))

(deftest generate-stations-k01->10-test
  (testing
    (is (= 1 (unique-stations 1)))
    (is (= 13 (unique-stations 32)))
    (is (= 167 (unique-stations 243)))
    (is (= 266 (unique-stations 1024)))
    (is (= 2500 (unique-stations 3125)))
    (is (= 653 (unique-stations 7776)))
    (is (= 14406 (unique-stations 16807)))
    (is (= 8207 (unique-stations 32768)))
    (is (= 39376 (unique-stations 59049)))
    (is (= 5005 (unique-stations 100000)))))

(deftest generate-stations-k10->17-test
  (testing
    (is (= 146410 (unique-stations 161051)))
    (is (= 20746 (unique-stations 248832)))
    (is (= 342732 (unique-stations 371293)))
    (is (= 57629 (unique-stations 537824)))
    (is (= 202505 (unique-stations 759375)))
    (is (= 262164 (unique-stations 1048576)))
    (is (= 1336336 (unique-stations 1419857)))
    (is (= 157474 (unique-stations 1889568)))
    (is (= 2345778 (unique-stations 2476099)))
    (is (= 160010 (unique-stations 3200000)))))

;(deftest generate-stations-k18->30-test
;(testing
; (is (= 388967 (unique-stations 4084101)))
; (is (= 585645 (unique-stations 5153632)))
; (is (= 3078251 (unique-stations 6436343)))
; (is (= 663567 (unique-stations 7962624)))
; (is (= 7812500 (unique-stations 9765625)))
; (is (= 685469 (unique-stations 11881376)))
; (is (= 9565953 (unique-stations 14348907)))
; (is (= 1843978 (unique-stations 17210368)))
; (is (= 19803868 (unique-stations 20511149)))
; (is (= 405005 (unique-stations 24300000)))
; ))