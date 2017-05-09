FROM rocker/r-devel
USER docker
#RUN Rscript -e 'install.packages("Rcpp")'
#RUN Rscript -e 'install.packages("testthat")'
#RUN Rscript -e 'install.packages("data.table")'
COPY rliblist.txt /
RUN cat /rliblist.txt
CMD "/bin/bash"
