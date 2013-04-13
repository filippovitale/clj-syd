(ns clj-syd.quadtree-type)

(definterface IQuadTree
  (xy [])
  (children [])
  (uphillchildren [[x y]])
  )

(deftype QuadTree [^int x ^int y ll lg gl gg]
  IQuadTree
  (xy [this] [x y])
  (children [this]
    (filter #(not (nil? %)) [ll lg gl gg]))
  (uphillchildren [this [^int rx ^int ry]]
    (cond
      (and (<, rx x) (<, ry y)) (filter #(not (nil? %)) [ll lg gl gg])
      (and (<, rx x) (>= ry y)) (filter #(not (nil? %)) [,, lg ,, gg])
      (and (>= rx x) (<, ry y)) (filter #(not (nil? %)) [,, ,, gl gg])
      :else (filter #(not (nil? %)) [gg])))
  Object
  (toString [this]
    (let [template "[%d %d] %s%s%s%s"
          b->a #(if (nil? %) "." "|")]
      (format template x y (b->a ll) (b->a lg) (b->a gl) (b->a gg)))))

(def empty-stations nil)

;(defn ^:static insert-station ; TODO try
(defn insert-station
  [qt [^int x ^int y]]
  (if (nil? qt)
    (QuadTree. x y nil nil nil nil)
    (let [lx (int (.x qt))
          ly (int (.y qt))
          ll (.ll qt)
          lg (.lg qt)
          gl (.gl qt)
          gg (.gg qt)
          branch (fn [child]
                   (if (nil? child)
                     (QuadTree. x y nil nil nil nil)
                     (insert-station child [x y])))
          ]
      (cond
        (and (<, x lx) (<, y ly)) (QuadTree. lx ly (branch ll) lg gl gg)
        (and (<, x lx) (>= y ly)) (QuadTree. lx ly ll (branch lg) gl gg)
        (and (>= x lx) (<, y ly)) (QuadTree. lx ly ll lg (branch gl) gg)
        (and (>= x lx) (>= y ly)) (QuadTree. lx ly ll lg gl (branch gg))
        ))))

(defn contain-station?
  [qt [x y]]
  (if (nil? qt)
    false
    (let [lx (int (.x qt))
          ly (int (.y qt))
          ll (.ll qt)
          lg (.lg qt)
          gl (.gl qt)
          gg (.gg qt)]
      (cond
        (and (,= x lx) (,= y ly)) true
        (and (<, x lx) (<, y ly)) (recur ll [x y])
        (and (<, x lx) (>= y ly)) (recur lg [x y])
        (and (>= x lx) (<, y ly)) (recur gl [x y])
        (and (>= x lx) (>= y ly)) (recur gg [x y])))
    ))

; TODO why don't semplify EVERYTHING into ISeq?
; that way I can:
; 1) search for a node in O(log n)
; 2) get all the children from there as ISeq
; see http://www.quantisan.com/unlock-lisp-sorcery-in-your-data-structure-by-implementing-clojure-iseq/
; see http://pepijndevos.nl/how-reify-works-and-how-to-write-a-custom-typ/index.html
; see http://bpeirce.me/clojure-sequence-implementations.html
; "You should have a look at clojure.lang.StringSeq and
; clojure.lang.IteratorSeq. Deriving from ASeq as they do is the way to
; go. The interface is stable, and no, there are no other optimizations
; needed." --Rich Hickey

;user=> (.children (QuadTree. 1 2 nil nil nil nil))
;()
;user=> (.children (QuadTree. 1 2
;                    (QuadTree. 0 0 nil nil nil nil)
;                    nil
;                    nil
;                    (QuadTree. 3 3 nil nil nil nil)))
;(#<QuadTree [0 0] ....> #<QuadTree [3 3] ....>)
;(.uphillchildren (QuadTree. 1 2
;                   (QuadTree. 0 0 nil nil nil nil)
;                   nil
;                   nil
;                   (QuadTree. 3 3 nil nil nil nil)) [1 1])
;(#<QuadTree [3 3] ....>)
;user=> (empty? (.children (QuadTree. 1 2
;                            (QuadTree. 0 0 nil nil nil nil)
;                            nil
;                            nil
;                            (QuadTree. 3 3 nil nil nil nil))))
;false
;user=> (empty? (.children (QuadTree. 1 2 nil nil nil nil)))
;true

; minimum example
;(definterface IQuadTree
;  (xy []))
;(deftype QuadTree [^int x ^int y ll lg gl gg]
;  IQuadTree
;  (xy [this] [x y]))

;10.6 definterface
; As we mentioned in section 9.3, Clojure was built on abstractions in the host platform Java.
; Types and protocols help to provide a foundation for defining your own abstractions in Clojure
; itself, for use within a Clojure context. But when interoperating with Java code, protocols
; and types won’t always suffice. Therefore, you need to be able to generate interfaces in some
; interop scenarios, and also for performance in cases involving primitive argument and return
; types. In this section, we’ll talk briefly about generating Java interfaces as the syntax, use
; cases, and purposes are likely familiar.


;user=> (defrecord QuadTree [s ll lg gl gg])
;user.QuadTree
;user=> (supers QuadTree)
;  #{clojure.lang.Counted clojure.lang.IKeywordLookup clojure.lang.IRecord java.util.Map clojure.lang.IPersistentMap clojure.lang.IPersistentCollection java.lang.Iterable clojure.lang.IHashEq java.io.Serializable clojure.lang.IObj clojure.lang.IMeta clojure.lang.Associative clojure.lang.Seqable java.lang.Object clojure.lang.ILookup}
;user=> (bases QuadTree)
;(java.lang.Object clojure.lang.IRecord clojure.lang.IHashEq clojure.lang.IObj clojure.lang.ILookup clojure.lang.IKeywordLookup clojure.lang.IPersistentMap java.util.Map java.io.Serializable)

;user=> (deftype QuadTree [^int x ^int y ll lg gl gg])
;user.QuadTree
;user=> (supers QuadTree)
;  #{clojure.lang.IType java.lang.Object}
;user=> (bases QuadTree)
;(java.lang.Object clojure.lang.IType)

;https://raw.github.com/Chouser/clojure-classes/master/graph-w-legend.png


; "defprotocol is dynamic, has no special compile-time effect, and defines no new types or classes.
; Implementations of the protocol methods can be provided using extend."
; "(definterface) Creates a new Java interface with the given name and method sigs. The method
; return types and parameter types may be specified with type hints, defaulting to Object if omitted.
;https://github.com/clojure/clojure/blame/master/src/clj/clojure/core_deftype.clj

; Q:  Why is it that defprotocol ignores type hints, while definterface deals with them?
; A:  Protocols are for consumption by Clojure functions, which aren't supposed to be statically
;     typed; interfaces are for consumption by Java classes, which are required to be statically typed.
;https://groups.google.com/forum/?fromgroups=#!topic/clojure/JxYHE4eqDv4

; "Using definterface to create something else than a "Marker Interface" is bad for your karma" -- Filippo Vitale
; e.g.
; (definterface INonStorable)
; (defn non-storable? [x]
;     (instance? INonStorable x))

; maybe investigate on this trick http://stackoverflow.com/questions/1976423/nested-types-in-clojure

;(for [x (range 5) y (range 5)] [x y])
;([0 0] [0 1] [0 2] [0 3] [0 4] [1 0] [1 1] [1 2] [1 3] [1 4] [2 0] [2 1] [2 2] [2 3] [2 4] [3 0] [3 1] [3 2] [3 3] [3 4] [4 0] [4 1] [4 2] [4 3] [4 4])

;(def s1 (shuffle (for [x (range 5) y (range 5)] [x y])))
;(def q1 (reduce insert-station nil s1))

;(tree-seq
;  #(not (empty? (.children %)))
;  #(.children %)
;  q1)

;(map #(.xy %) (tree-seq
;                #(not (empty? (.children %)))
;                #(.uphillchildren % [3 3])
;                q1))

(defn uphill?
  [[x y]]
  (let [rx 3 ry 3]
    (and (>= x rx) (>= x ry) (not= [x y] [rx ry]))))

(let [qt q1 rx 3 ry 3]
  (filter uphill?
    (map
      #(.xy %) (tree-seq
                 #(not (empty? (.children %)))
                 #(.uphillchildren % [rx ry])
                 qt))))


(defn retrieve-stations
  ([qt] (retrieve-stations qt [-1 -1]))
  ([qt [x y]]
    (letfn
      [(uphill [qt] (qt-children-uphill qt [x y]))]
      (filter
        #(let
           [lx (first %)
            ly (last %)]
           (and (>= lx x) (>= lx y) (not= [lx ly] [x y])))
        (map #(:s %) (tree-seq qt-with-children? uphill qt))))))
