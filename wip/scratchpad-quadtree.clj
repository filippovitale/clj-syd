;(ns clj-syd.quadtree)
;
;(defrecord QuadTree [s ll lg gl gg])
;
;(def empty nil)
;
;(defn insert-station
;  [qt [x y]]
;  ; start from root
;  ; x >= g* else l*
;  ; y >= *g else *l
;  ; nil? new_QT_there else that_s_the_root
;  ; recur
;  (if (nil? qt)
;    (QuadTree. [x y] nil nil nil nil)
;    (let [lx (first (:s qt))
;          ly (last (:s qt))
;          ll (:ll qt)
;          lg (:lg qt)
;          gl (:gl qt)
;          gg (:gg qt)]
;      (cond
;        (and (<, x lx) (<, y ly))
;        (QuadTree. [lx ly]
;          (if (nil? ll)
;            (QuadTree. [x y] nil nil nil nil)
;            (insert-station ll [x y]))
;          lg gl gg)
;
;        (and (<, x lx) (>= y ly))
;        (QuadTree. [lx ly]
;          ll (if (nil? lg)
;               (QuadTree. [x y] nil nil nil nil)
;               (insert-station lg [x y]))
;          gl gg)
;
;        (and (>= x lx) (<, y ly))
;        (QuadTree. [lx ly]
;          ll lg (if (nil? gl)
;                  (QuadTree. [x y] nil nil nil nil)
;                  (insert-station gl [x y]))
;          gg)
;
;        (and (>= x lx) (>= y ly))
;        (QuadTree. [lx ly]
;          ll lg gl (if (nil? gg)
;                     (QuadTree. [x y] nil nil nil nil)
;                     (insert-station gg [x y])))
;        ))))
;
;(defn retrieve-stations
;  ([qt]
;    (retrieve-stations qt [-1 -1]))
;  ([qt [x y]]
;    (let [lx (first (:s qt))
;          ly (last (:s qt))
;          ll (:ll qt)
;          lg (:lg qt)
;          gl (:gl qt)
;          gg (:gg qt)]
;      (filter
;        #(and
;           (>= (first %) x)
;           (>= (last %) y)
;           (not= [x y] %))
;        (vec #{[16 15] [1 1] [2 3] [4 9] [6 15]
;               [20 3] [12 1] [10 1] [14 5]
;               [18 9] [8 5]})) ; TODO
;      )))
;
;(defn contain-station?
;  [qt [x y]]
;  (if (nil? qt)
;    false
;    (let [lx (first (:s qt))
;          ly (last (:s qt))
;          ll (:ll qt)
;          lg (:lg qt)
;          gl (:gl qt)
;          gg (:gg qt)]
;      (cond
;        (and (,= x lx) (,= y ly)) true
;        (and (<, x lx) (<, y ly)) (contain-station? ll [x y])
;        (and (<, x lx) (>= y ly)) (contain-station? lg [x y])
;        (and (>= x lx) (<, y ly)) (contain-station? gl [x y])
;        (and (>= x lx) (>= y ly)) (contain-station? gg [x y])))
;    ))
;
;;Example
;; (def a (QuadTree. [1 1] nil nil nil nil))
;; (def b (QuadTree. [8 5]
;;         (QuadTree. [1 1] nil nil nil nil) ; ll
;;         (QuadTree. [4 9] nil nil nil nil) ; lg
;;         (QuadTree. [12 1] nil nil nil nil) ; gl
;;         (QuadTree. [18 9] nil nil nil nil))) ; gg
;; (def c (QuadTree. [8 5]
;;         (QuadTree. [1 1] nil nil nil
;;           (QuadTree. [2 3] nil nil nil nil))
;;         (QuadTree. [4 9] nil nil nil
;;           (QuadTree. [6 15] nil nil nil nil))
;;         (QuadTree. [12 1] nil
;;           (QuadTree. [10 1] nil nil nil nil)
;;           nil
;;           (QuadTree. [20 3] nil nil nil nil))
;;         (QuadTree. [18 9]
;;           (QuadTree. [14 5] nil nil nil nil)
;;           (QuadTree. [12 1] nil nil nil nil)
;;           nil nil)))
;
;(def g #{[16 15] [1 1] [2 3] [4 9] [6 15] [20 3] [12 1] [10 1] [14 5] [18 9] [8 5]})
;(def t (reduce #(insert-station %1 %2) empty (vec g)))
;;(pprint t)
;
;;                              [16 15]
;;                                 |
;;      ======================================
;;      [1 1]         [6 15]       [20 3]  nil
;;        |                           |
;; =================          =================
;; nil nil nil [2 3]          nil [18 9] nil nil
;;               |
;; ===============================
;; nil nil [12 1]            [4 9]
;;            |                |
;;  ==================  ==================
;;  nil [10 1] nil nil  nil nil [14 5] nil
;;                                 |
;;                       ==================
;;                       nil [8 5] nil nil
;
;; (:s (:lg (:gl (:gg (:ll t)))))
;
;;{:s [16 15],
;; :ll
;; {:s [1 1],
;;  :ll nil,
;;  :lg nil,
;;  :gl nil,
;;  :gg
;;  {:s [2 3],
;;   :ll nil,
;;   :lg nil,
;;   :gl
;;   {:s [12 1],
;;    :ll nil,
;;    :lg {:s [10 1], :ll nil, :lg nil, :gl nil, :gg nil},
;;    :gl nil,
;;    :gg nil},
;;   :gg
;;   {:s [4 9],
;;    :ll nil,
;;    :lg nil,
;;    :gl
;;    {:s [14 5],
;;     :ll nil,
;;     :lg {:s [8 5], :ll nil, :lg nil, :gl nil, :gg nil},
;;     :gl nil,
;;     :gg nil},
;;    :gg nil}}},
;; :lg {:s [6 15], :ll nil, :lg nil, :gl nil, :gg nil},
;; :gl
;; {:s [20 3],
;;  :ll nil,
;;  :lg {:s [18 9], :ll nil, :lg nil, :gl nil, :gg nil},
;;  :gl nil,
;;  :gg nil},
;; :gg nil}
;;nil
;
;;#((let [ll (:ll %)
;;        lg (:lg %)
;;        gl (:gl %)
;;        gg (:gg %)]
;;    false));(not (and (nil? ll) )))); (nil? lg) (nil? gl) (nil? gg)))))
;
;;(filter #(instance? QuadTree %)
;;  (map #(:s %)
;;    (tree-seq
;;      #(let [ll (:ll %)
;;             lg (:lg %)
;;             gl (:gl %)
;;             gg (:gg %)]
;;         ;(not (and (nil? ll)))) ; (nil? lg) (nil? gl) (nil? gg)))))
;;         (not (nil? ll))) ; (nil? lg) (nil? gl) (nil? gg)))))
;;      #(:ll %)
;;      t)))
;
;;; TODO http://stackoverflow.com/a/12379141/81444
;
;
;;(map #(:s %)
;;  (tree-seq
;;    #(not (nil? (:ll %)))
;;    #(do
;;       (println "-->" (:ll %) "<--")
;;       (:ll %))
;;    t))
;
;;(map #(:s %)
;;(map #(println "-->" % "<--")
;;  (tree-seq
;;    #(not (nil? (:ll %)))
;;    #(:ll %)
;;    t))
;
;(defn qt-with-children?
;  [qt]
;  (let [ll (:ll qt)
;        lg (:lg qt)
;        gl (:gl qt)
;        gg (:gg qt)]
;    (if (and (nil? ll) (nil? lg) (nil? gl) (nil? gg)) ; REFACTOR
;      false
;      true)
;    ))
;
;(defn qt-children
;  [qt]
;  (let [ll (:ll qt)
;        lg (:lg qt)
;        gl (:gl qt)
;        gg (:gg qt)]
;    (filter #(not (nil? %)) [ll lg gl gg])))
;
;; (map #(:s %) (qt-children t))
;;=> ([1 1] [6 15] [20 3])
;
;;(count
;;  (tree-seq
;;    qt-with-children?
;;    qt-children
;;    t))
;; => 11
;
;;(map #(:s %)
;;  (tree-seq
;;    qt-with-children?
;;    qt-children
;;    t))
;;([16 15] [1 1] [2 3] [12 1] [10 1] [4 9] [14 5] [8 5] [6 15] [20 3] [18 9])
;
;
;;(defn qt-children-uphill
;;  [qt [x y]]
;;  (let [lx (first (:s qt))
;;        ly (last (:s qt))
;;        ll (:ll qt)
;;        lg (:lg qt)
;;        gl (:gl qt)
;;        gg (:gg qt)
;;        uphill? (fn [qt]
;;                  (and
;;                    (not (nil? qt))
;;                    (not= [x y] qt)
;;                    (let [lx (first (:s qt))
;;                          ly (last (:s qt))]
;;                      (and (>= lx x) (>= ly y)))))]
;;    (if (and (< x lx) (< y ly)) ; TODO sure <= or just < ?
;;      (concat [lg gl gg] (if (uphill? ll) ll nil))
;;      gg;(filter uphill? [lg gl gg])
;;      )
;;    ))
;
;(defn qt-children-uphill ; TODO rename somethign like possible branches with...
;  [qt [x y]]
;  (let [lx (first (:s qt))
;        ly (last (:s qt))
;        ll (:ll qt)
;        lg (:lg qt)
;        gl (:gl qt)
;        gg (:gg qt)]
;    (cond
;      (and (<, x lx) (<, y ly)) (filter #(not (nil? %)) [ll lg gl gg])
;      (and (<, x lx) (>= y ly)) (filter #(not (nil? %)) [,, lg ,, gg])
;      (and (>= x lx) (<, y ly)) (filter #(not (nil? %)) [,, ,, gl gg])
;      :else (filter #(not (nil? %)) [gg]))))
;
;(defn retrieve-stations
;  ([qt]
;    (retrieve-stations qt [-1 -1]))
;  ([qt [x y]]
;    (letfn
;      [(uphill [qt] (qt-children-uphill qt [x y]))]
;      (filter
;        #(let
;           [lx (first %)
;            ly (last %)]
;           (and (>= lx x) (>= lx y) (not= [lx ly] [x y])))
;        (map
;          #(:s %)
;          (tree-seq qt-with-children? uphill t))))))
;
;;(retrieve-stations t)
;;([16 15] [1 1] [2 3] [12 1] [10 1] [4 9] [14 5] [8 5] [6 15] [20 3] [18 9])
;
;;(retrieve-stations t [10 10])
;;([16 15] [20 3] [18 9])

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

;(defprotocol Q
;  " Datastructure: A point Quad Tree for representing 2D data. Each
;    region has the same ratio as the bounds for the tree.
;    The implementation currently requires pre-determined bounds for data as it
;    can not rebalance itself to that degree."
;  (q-constructor [this] "Constructs a new quad tree")
;  (q-root_ [this] "new goog.structs.QuadTree.Node(minX, minY, maxX - minX, maxY - minY)")
;  (q-count_ [this] "0")
;  (q-count_ [this] "0")
;  (q-set [this] "Sets the value of an (x, y) point within the quad-tree."))

;(defn insert-station
;  [qt [x y]]
;; start from root
;; x >= g* else l*
;; y >= *g else *l
;; nil? new_QT_there else that_s_the_root
;; recur