(ns clj-syd.quadtree)



;QuadTree = function(minX, minY, maxX, maxY)

;goog.structs.QuadTree.prototype.set = function(x, y, value) {
;var root = this.root_;
;if (x < root.x || y < root.y || x > root.x + root.w || y > root.y + root.h) {
;throw Error('Out of bounds : (' + x + ', ' + y + ')');
;}
;if (this.insert_(root, new goog.structs.QuadTree.Point(x, y, value))) {
;this.count_++;
;}
;};

(defprotocol Q
  " Datastructure: A point Quad Tree for representing 2D data. Each
    region has the same ratio as the bounds for the tree.
    The implementation currently requires pre-determined bounds for data as it
    can not rebalance itself to that degree."
  (q-constructor [this] "Constructs a new quad tree")
  (q-root_ [this] "new goog.structs.QuadTree.Node(minX, minY, maxX - minX, maxY - minY)")
  (q-count_ [this] "0")
  (q-count_ [this] "0")
  (q-set [this] "Sets the value of an (x, y) point within the quad-tree."))

(defrecord Bird [name species]
  Fly
  (fly [this] (str (:name this) " flies...")))


(defprotocol Fly
  "A simple protocol for flying"
  (fly [this] "Method to fly"))

(defrecord Bird [name species]
  Fly
  (fly [this] (str (:name this) " flies...")))

(extends? Fly Bird)
-> true

(def crow (Bird. "Crow" "Corvus corax"))

(fly crow)
-> "Crow flies..."