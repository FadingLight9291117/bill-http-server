FROM openjdk

WORKDIR /app
ADD build/libs/bill-http-server-v1.0.0-beta-all.jar .

CMD java -jar bill-http-server-v1.0.0-beta-all.jar
