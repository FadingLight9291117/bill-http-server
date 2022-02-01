FROM openjdk:15-alpine
WORKDIR /app

COPY build/libs/bill-http-server-v1.0.0-beta-all.jar /server.jar

CMD java -jar /server.jar
