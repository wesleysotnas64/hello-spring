FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-21.0.7-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:21.0.7-slim

EXPOSE 8080

COPY --from=build /target/hello-spring-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-jar", "app.jar"]