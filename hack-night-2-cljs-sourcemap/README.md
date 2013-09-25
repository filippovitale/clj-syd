# clj-syd Hack night 26 Sep 2013

clj-syd Hack night code

## Steps
* `lein new modern-cljs`
* create missing folders
```
  +-- doc
  +-- resources
  ¦   +-- public
  ¦       +-- css
  ¦       +-- js
  +-- src
  ¦   +-- clj
  ¦   ¦   +-- modern_cljs
  ¦   +-- cljs
  ¦       +-- modern_cljs
  +-- test
      +-- modern_cljs
```

* update the `project.clj` with:
```
  :source-paths ["src/clj"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1889"]]

  :plugins [[lein-cljsbuild "0.3.3"]]

  :cljsbuild 
  {:builds
    [{:id "advanced"
      :source-paths ["src/cljs"]
      :compiler {:optimizations :advanced
                :pretty-print false
                :output-dir "resources/public/js"
                :output-to "modern.js"
                :source-map "modern.js.map"}}]})
```

* `src/cljs/modern_cljs/modern.cljs`
* `resources/public/simple.html`
* `lein cljsbuild once`
* `lein trampoline cljsbuild repl-listen`
* `(.log js/console "clj-syd")`
* `modern-cljs\resources\public>python -m SimpleHTTPServer 8888`


## Resources
* https://github.com/magomimmo/modern-cljs/blob/master/doc/tutorial-01.md
* ```//@``` is deprecated. ```//#``` should be used https://codereview.chromium.org/15832007
* Introduction to sourcemap (21 March 2012) http://www.html5rocks.com/en/tutorials/developertools/sourcemaps/
* Source Maps 101 Tutorial (16 Jan 2013) http://net.tutsplus.com/tutorials/tools-and-tips/source-maps-101/

## License

Copyright � 2013
Distributed under the Eclipse Public License, the same as Clojure.
