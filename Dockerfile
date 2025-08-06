FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/prevention-platform-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]