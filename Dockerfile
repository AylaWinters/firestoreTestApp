FROM openjdk:8 AS main-build
#VOLUME /tmp
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN mkdir /libs
COPY --from=deplayer /app/target/dependency-layer-0.0.1-SNAPSHOT.jar /libs
RUN ls
#FROM adoptopenjdk:8-jdk-hotspot
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests
##COPY --from=main-build /app/target/mcTesty-0.0.1-SNAPSHOT.jar /app
RUN mv /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]