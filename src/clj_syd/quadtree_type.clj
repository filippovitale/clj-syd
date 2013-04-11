(ns clj-syd.quadtree-type)

(definterface IQuadTree
  ;(xy [])
  )

(deftype QuadTree [^int x ^int y ll lg gl gg]
  IQuadTree
  ;(xy [this] [x y]) ; that was an example, gonna delete this soon

  Object
  (toString [this]
    (let [template "[%d %d]\n"]
      (format template x y))))
; (.x (.ll (QuadTree. 3 3 nil nil nil nil)))
; => 1
; (.x (.lg (QuadTree. 3 3 nil (QuadTree. 2 2 nil nil nil nil) nil nil)))
;  => 2
; (nil? (.gg (QuadTree. 3 3 nil (QuadTree. 2 2 nil nil nil nil) nil nil)))
; true

;(defn insert-station
;  [qt [x y]]
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
