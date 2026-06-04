FROM maven:3.8-openjdk-17-slim
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
EXPOSE 8086
CMD ["mvn", "spring-boot:run"]
