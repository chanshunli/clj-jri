(defproject clj-jri "0.1.0-SNAPSHOT"
  :description "Wrapper for Java/R interface"
  :url "https://github.com/fanannan/clj-jri"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :resource-paths ["lib/JRI.jar"
                   "lib/JRIEngine.jar"
                   "lib/REngine.jar"
                   "lib/libjri.jnilib"]
  :jvm-opts [~(str "-Djava.library.path=/usr/local/lib/R/3.4/site-library/rJava/jri/:" (System/getProperty "java.library.path"))]
  :main clj-jri.core)

;lein clean; lein deps; lein compile; lein jar; lein uberjar; lein localrepo install ./target/clj-jri-0.0.1-standalone.jar clj-jri 0.0.1-standalone;
