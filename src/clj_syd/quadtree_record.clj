(ns clj-syd.quadtree-record)

(defrecord QuadTree [s ll lg gl gg])

(def empty-stations nil)

(defn insert-station
  [qt [x y]]
  (if (nil? qt)
    (QuadTree. [x y] nil nil nil nil)
    (let [lx (first (:s qt))
          ly (last (:s qt))
          ll (:ll qt)
          lg (:lg qt)
          gl (:gl qt)
          gg (:gg qt)]
      (cond
        (and (<, x lx) (<, y ly))
        (QuadTree. [lx ly]
          (if (nil? ll)
            (QuadTree. [x y] nil nil nil nil)
            (insert-station ll [x y]))
          lg gl gg)

        (and (<, x lx) (>= y ly))
        (QuadTree. [lx ly]
          ll (if (nil? lg)
               (QuadTree. [x y] nil nil nil nil)
               (insert-station lg [x y]))
          gl gg)

        (and (>= x lx) (<, y ly))
        (QuadTree. [lx ly]
          ll lg (if (nil? gl)
                  (QuadTree. [x y] nil nil nil nil)
                  (insert-station gl [x y]))
          gg)

        (and (>= x lx) (>= y ly))
        (QuadTree. [lx ly]
          ll lg gl (if (nil? gg)
                     (QuadTree. [x y] nil nil nil nil)
                     (insert-station gg [x y])))
        ))))

(defn contain-station?
  [qt [x y]]
  (if (nil? qt)
    false
    (let [lx (first (:s qt))
          ly (last (:s qt))
          ll (:ll qt)
          lg (:lg qt)
          gl (:gl qt)
          gg (:gg qt)]
      (cond
        (and (,= x lx) (,= y ly)) true
        (and (<, x lx) (<, y ly)) (contain-station? ll [x y])
        (and (<, x lx) (>= y ly)) (contain-station? lg [x y])
        (and (>= x lx) (<, y ly)) (contain-station? gl [x y])
        (and (>= x lx) (>= y ly)) (contain-station? gg [x y])))
    ))

(defn qt-with-children?
  [qt]
  (let [ll (:ll qt)
        lg (:lg qt)
        gl (:gl qt)
        gg (:gg qt)]
    (if (and (nil? ll) (nil? lg) (nil? gl) (nil? gg)) ; REFACTOR
      false
      true)
    ))

(defn qt-children
  [qt]
  (let [ll (:ll qt)
        lg (:lg qt)
        gl (:gl qt)
        gg (:gg qt)]
    (filter #(not (nil? %)) [ll lg gl gg])))

; TODO rename something like possible branches with...
(defn qt-children-uphill
  [qt [x y]]
  (let [lx (first (:s qt))
        ly (last (:s qt))
        ll (:ll qt)
        lg (:lg qt)
        gl (:gl qt)
        gg (:gg qt)]
    (cond
      (and (<, x lx) (<, y ly)) (filter #(not (nil? %)) [ll lg gl gg])
      (and (<, x lx) (>= y ly)) (filter #(not (nil? %)) [,, lg ,, gg])
      (and (>= x lx) (<, y ly)) (filter #(not (nil? %)) [,, ,, gl gg])
      :else (filter #(not (nil? %)) [gg]))))

(defn retrieve-stations
  ([qt]
    (retrieve-stations qt [-1 -1]))
  ([qt [x y]]
    (letfn
      [(uphill [qt] (qt-children-uphill qt [x y]))]
      (filter
        #(let
           [lx (first %)
            ly (last %)]
           (and (>= lx x) (>= lx y) (not= [lx ly] [x y])))
        (map
          #(:s %)
          (tree-seq qt-with-children? uphill qt))))))
