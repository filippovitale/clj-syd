(ns clj-syd.quadtree-test
  (:use clojure.test
        clj-syd.quadtree))

(deftest generate-stations-n22-test
  (testing
    (is (empty? (quadtree 22)))
    ))
