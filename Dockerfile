FROM eclipse-temurin:17-jre-alpine
LABEL author=tomseanmy
LABEL homepage=https://github.com/tomseanmy

WORKDIR /data
COPY target/tushansusu.jar /data/

VOLUME /data/application.yml

ENTRYPOINT java -Xmx100m -Xms100m -jar /data/tushansusu.jar
