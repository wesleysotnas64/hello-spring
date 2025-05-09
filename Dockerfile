# Etapa 1: Build da aplicação usando Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

# Etapa 2: Imagem final com JDK mais leve
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY --from=build /app/target/hello-spring-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
