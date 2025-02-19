FROM eclipse-temurin:17-jre-alpine
MAINTAINER tomsean "tom_sean@outlook.com"
LABEL org.opencontainers.image.source="https://github.com/tomseanmy/tushansusu"
WORKDIR /data
COPY target/tushansusu.jar /data/

VOLUME /data/application.yml

ENTRYPOINT java -Xmx100m -Xms100m -jar /data/tushansusu.jar
