(ns clj-jri.core
  (:require [clj-jri.R :as R]))

;This is a simple Clojure wrapper for JRI, Java-to-R bridge.
;refer to https://github.com/s-u/rJava
;refer to http://www.rosuda.org/r/nightly/javadoc/org/rosuda/JRI/Rengine.html

;You have to set the following environment variables.
;(example) export R_HOME=/usr/lib/R/
;(example) export LD_LIBRARY_PATH=./lib
;You may need to install R library "JGR/rJava" (https://rforge.net/JGR/linux.html)
;on R by "install.packages('rJava')"

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def r-lib
  ["library(tm)"
   "library(wordcloud)"
   "library(memoise)"])

;; load R lib ==>
(R/eval r-lib)

(def get-term-matrix
  "getTermMatrix <- memoise(function(book) { text <- readLines(sprintf(\"./%s.txt.gz\", book), encoding=\"UTF-8\"); myCorpus = Corpus(VectorSource(text));    myCorpus = tm_map(myCorpus, content_transformer(tolower));    myCorpus = tm_map(myCorpus, removePunctuation);    myCorpus = tm_map(myCorpus, removeNumbers);    myCorpus = tm_map(myCorpus, removeWords, c(stopwords(\"SMART\"), \"thy\", \"thou\", \"thee\", \"the\", \"and\", \"but\"));    myDTM = TermDocumentMatrix(myCorpus, control = list(minWordLength = 1));    m = as.matrix(myDTM);    sort(rowSums(m), decreasing = TRUE)  })"
  )

(R/eval get-term-matrix)

;;(R/eval "getTermMatrix(\"merchant\")")

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn -main[& args]
  (use 'clj-jri.sample))
