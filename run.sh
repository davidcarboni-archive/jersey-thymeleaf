#!/bin/bash
#export MONGOLAB_URI=mongodb://localhost/listpoint
mvn clean package && \
java -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=n -jar target/dependency/jetty-runner.jar --port 8080 target/jersey-thymeleaf.war
