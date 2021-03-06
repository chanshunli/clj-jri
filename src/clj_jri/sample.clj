(ns clj-jri.sample
  (:require [clj-jri.R :as R]))

;As long as supported by this library, JRI basically returns a vector so that you need to specify {:conversion :single} as the second argment to get a single number from the return value from R.

;When giving {:conversion false}, then R/eval returns the REXP object without any conversion. {:conversion :vec} and {:conversion raw} gives a Clojure vector of Double values and a Java Double array respectively.

(println "# random numbers")
(println (R/eval "rnorm(5)"))
(println (R/eval "rnorm(5)" {:conversion false}))

(println "# square root")
(println (R/eval "sqrt(36)"))
(println (R/eval "sqrt(36)" {:conversion false}))
(println (R/eval "sqrt(36)" {:conversion :vec}))
(println (R/eval "sqrt(36)" {:conversion :single}))
(println (R/eval "sqrt(36)" {:conversion :raw}))

; R-expressions in a vector, multiple-R-expression with brackets in a String and line-separated R-expressions are acceptable for evaluation.
(println "# multiple statements, vector and array")
(R/eval ["a <- 120" "v <- c(10,60)" "z <- array(0, c(3,4,2))"])
(R/eval "{a <- 120; v <- c(10,60); z <- array(0, c(3,4,2));}")
(R/eval "a <- 120;
         v <- c(10,60);
         z <- array(0, c(3,4,2))")

(println (R/eval "v"))
(println (R/eval "v" {:conversion false}))
(println (R/eval "v" {:conversion :single}))
(println (R/eval "z"))

(println "# string")
(println "pwd:" (R/eval "getwd()"))

; When evaluationg an undefined function, it returns nil.
(println "# undefined function")
(println (R/eval "xxxx(a)"))

; You can assign a number vector to a varibale expressed in String but you can't assign a number and a string to the variable.
(println "# assigning vectors ")
(println (R/assign "x" [1.1 2.2 3.5]))
(println (R/eval "x"))
(println (R/assign "x" [1 2 3]))
(println (R/eval "x"))

(println "# chart test (check /tmp/LineChart.jpg)")
;; 这里输出了R画的图, ok的 ` /tmp/LineChart.jpg `
(println (R/eval [
            "data(cars)"
            "jpeg(file=\"/tmp/LineChart.jpg\",width=800,height=600)"
            "plot(cars, main = \"lowess(cars)\")"
            "lines(lowess(cars), col = 2)"
            "lines(lowess(cars, f = 0.2), col = 3)"
            "legend(5, 120, c(paste(\"f = \", c(\"2/3\", \".2\"))), lty = 1, col = 2:3)"
            "dev.off()"]))

(R/shutdown)


(R/eval "a <- 1 \n a + 100") ;;=> [101.0], 以矩阵运算为核心,向量只是特殊的矩阵
(R/eval "sqrt(36)") ;;=> [6.0]

;; 函数的定义 
(R/eval "fnTest <- function(a, b) { a + b }")
(R/eval "fnTest") ;; => nil
(R/eval "fnTest(1, 2)") ;;=> [3.0]

;; 函数定义用了"\n",不是单行,执行报错
(R/eval "fnTest2 <- function(a, b) {
          a + b
       }") ;;=> nil
(R/eval "fnTest2") ;; => nil
(R/eval "fnTest2(1, 2)") ;;=> nil

;; 函数定义用了";",隔开了多行,ok了
(R/eval "fnTest3 <- function(a, b) { c = a + b; c + 100 }")
(R/eval "fnTest3") ;; => nil
(R/eval "fnTest3(1, 2)")  ;;=> [103.0]

;; R的读取文件是ok的
(R/eval "readLines(\"merchant.txt.gz\", encoding=\"UTF-8\")") ;; OK

;; 定义一个函数的例子: 
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def r-lib
  ["library(tm)"
   "library(wordcloud)"
   "library(memoise)"])

;; load r lib ==>
(R/eval r-lib)

;; `Replace string (default ^J → ; `=> 将回车替换为";"号
(def get-term-matrix
  "getTermMatrix <- memoise(function(book) { text <- readLines(sprintf(\"./%s.txt.gz\", book), encoding=\"UTF-8\"); myCorpus = Corpus(VectorSource(text));    myCorpus = tm_map(myCorpus, content_transformer(tolower));    myCorpus = tm_map(myCorpus, removePunctuation);    myCorpus = tm_map(myCorpus, removeNumbers);    myCorpus = tm_map(myCorpus, removeWords, c(stopwords(\"SMART\"), \"thy\", \"thou\", \"thee\", \"the\", \"and\", \"but\"));    myDTM = TermDocumentMatrix(myCorpus, control = list(minWordLength = 1));    m = as.matrix(myDTM);    sort(rowSums(m), decreasing = TRUE)  })"
  )

(R/eval get-term-matrix)
;; R输出的矩阵,需要Clojure的特殊处理才可以,否则统统压扁成Clj的Vector了
(R/eval "getTermMatrix(\"merchant\")") ;;=> [604.0 320.0 246.0 208.0 171.0 163.0 161.0 156.0 133.0 133.0 ... ]
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

