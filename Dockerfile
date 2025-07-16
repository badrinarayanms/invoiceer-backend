# Build stage - using official Maven image with Temurin JDK
FROM maven:3.8.6-eclipse-temurin-17 AS build-stage
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage - using official Temurin JRE
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=build-stage /app/target/invoice-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]