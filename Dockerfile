FROM openjdk:8 AS main-build
VOLUME /tmp
RUN mkdir /app
COPY . /app
COPY --from=dependency-build /app/target/dependency-layer-0.0.1-SNAPSHOT.jar /app
FROM adoptopenjdk:8-jdk-hotspot
WORKDIR /app
RUN /app/mvn clean install -DskipTests
COPY --from=main-build /app/target/mcTesty-0.0.1-SNAPSHOT.jar /app
RUN mv /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]