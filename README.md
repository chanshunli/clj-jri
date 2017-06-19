# clj-jri

A simple wrapper library for JRI, Java/R interface, mostly to draw charts on R.

## Usage (Mac: libjri.jnilib, Ubuntu or Linux: libjri.so)


* check libs path
```bash
➜  clj-jri git:(master) ✗ ls /usr/local/lib/R/3.4/site-library/rJava/jri/JRI.jar
/usr/local/lib/R/3.4/site-library/rJava/jri/JRI.jar
➜  clj-jri git:(master) ✗ ls /usr/local/lib/R/3.4/site-library/rJava/jri/JRIEngine.jar
/usr/local/lib/R/3.4/site-library/rJava/jri/JRIEngine.jar
➜  clj-jri git:(master) ✗ ls /usr/local/lib/R/3.4/site-library/rJava/jri/REngine.jar
/usr/local/lib/R/3.4/site-library/rJava/jri/REngine.jar
➜  clj-jri git:(master) ✗ ls /usr/local/lib/R/3.4/site-library/rJava/jri/libjri.jnilib
/usr/local/lib/R/3.4/site-library/rJava/jri/libjri.jnilib
➜  clj-jri git:(master)
```
* ENV setup
```bash
export R_HOME=/usr/local/Cellar/r/3.4.0_1/R.framework/Resources
```
* project.clj : jvm-opts & resource-paths 
```clojure
  :dependencies [ ...
  [clj-jri "0.1.1-SNAPSHOT"]
  ]

  :jvm-opts [ ...
             ~(str "-Djava.library.path=/usr/local/lib/R/3.4/site-library/rJava/jri/:" (System/getProperty "java.library.path"))]

  :resource-paths [...
                   "/usr/local/lib/R/3.4/site-library/rJava/jri/JRI.jar"
                   "/usr/local/lib/R/3.4/site-library/rJava/jri/JRIEngine.jar"
                   "/usr/local/lib/R/3.4/site-library/rJava/jri/REngine.jar"
                   "/usr/local/lib/R/3.4/site-library/rJava/jri/libjri.jnilib"]
                   
```
* lein 

```bash
lein clean
lein repl
```

* require   
```           
[clj-jri.R :as R]

```

## Preparation

You may need to install R library "JGR/rJava" (http://rforge.net/JGR/) on R by "install.packages('rJava')" and have to set the following environment variables.

```clojure
  :resource-paths ["lib/JRI.jar"
                   "lib/JRIEngine.jar"
                   "lib/REngine.jar"
                   "lib/libjri.jnilib"]
  :jvm-opts [~(str "-Djava.library.path=/usr/local/lib/R/3.4/site-library/rJava/jri/:" (System/getProperty "java.library.path"))]

```

* Usage: 

```clojure
clj-jri.core=>  (ns test (:require [clj-jri.R :as R]))
WARNING: eval already refers to: #'clojure.core/eval in namespace: clj-jri.R, being replaced by: #'clj-jri.R/eval
nil

test=> (R/eval "rnorm(5)")
[-0.8927281724537759 -0.31414492274597694 0.16723344714036312 -0.7862470681485274 0.26787108270930693]
test=>
```

You also need to place REngine.jar, JRI.jar and libjri.so from the original project site onto LD_LIBRARY_PATH.

## Examples and notes

See clj-jri.sample for the example codes and notes on the usage.

## TODOs

Additional features may be supported in the future, when necessary.


## References

For detail, refer to https://github.com/s-u/rJava and to http://www.rosuda.org/r/nightly/javadoc/org/rosuda/JRI/Rengine.html


## License

Copyright © 2014 Takahiro SAWADA

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
