(defproject clj-syd "0.1.0-SNAPSHOT"
  :description "clj-syd Hack night code"
  :url "https://github.com/filippovitale/clj-syd"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [perforate "0.2.4"]]
  :plugins [[perforate "0.2.4"]]
  :profiles {:clj1.5 {:dependencies [[org.clojure/clojure "1.5.1"]]}}
  :perforate {:environments [{:name :default-1.5
                              :profiles [:clj1.5 ]
                              :namespaces [clj-syd.benchmarks.core]}]}
  :jvm-opts ["-Xmx3g"
             ;"-XX:+UseConcMarkSweepGC"
             ;"-XX:+DisableExplicitGC"
             ]
  :main clj-syd.core)
