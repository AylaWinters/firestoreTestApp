FROM openjdk:8
RUN mkdir /app
COPY . /app
WORKDIR /app
RUN apt-get update && apt-get install -y maven
RUN mvn clean install -DskipTests
RUN mv /app/target/mcTesty-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]