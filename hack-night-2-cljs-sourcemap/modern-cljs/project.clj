(defproject modern-cljs "0.1.0-SNAPSHOT"
  :description "clj-syd hack-night 26/09/2013"
  :url "https://github.com/filippovitale/clj-syd"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

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
