(defproject clj-syd "0.1.0-SNAPSHOT"
  :description "clj-syd Hack night code"
  :url "https://github.com/filippovitale/clj-syd"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.0-RC17"]
                 [perforate "0.2.3"]]
  :plugins [[perforate "0.2.3"]]
  :eval-in :leiningen :profiles {:clj1.5 {:dependencies [[org.clojure/clojure "1.5.0-RC17"]]}
                                 :clj1.4 {:dependencies [[org.clojure/clojure "1.4.0"]]}}
  :perforate {:environments [{:profiles [:clj1.5 ] :namespaces [clj-syd.benchmarks.core]}
                             {:profiles [:clj1.4 ] :namespaces [clj-syd.benchmarks.core]}]}
  ;;:jvm-opts ["-Xmx1g"]
  :main clj-syd.core)
