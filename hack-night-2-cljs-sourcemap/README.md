# clj-syd Hack night 26 Sep 2013

clj-syd Hack night code

## Steps
* ```lein new modern-cljs```
* Ctrl-Shift-N in explorer
* update the ```project.clj``` with:
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
* ```src/cljs/modern_cljs/modern.cljs```
* ```resources/public/simple.html```

## License

Copyright © 2013
Distributed under the Eclipse Public License, the same as Clojure.
