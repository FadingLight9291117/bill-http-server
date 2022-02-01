FROM openjdk

WORKDIR /app
RUN ./gradlew wrapper
RUN ./gradlew clean
RUN ./gradlew shadowJar
ADD build/libs/bill-http-server-v1.0.0-beta-all.jar .

CMD java -jar bill-http-server-v1.0.0-beta-all.jar
