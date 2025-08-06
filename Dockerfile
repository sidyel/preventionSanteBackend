FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app

# Copier tous les fichiers sauf application.properties
COPY pom.xml .
COPY src ./src

# Supprimer le fichier application.properties problématique s'il existe
RUN rm -f /app/src/main/resources/application.properties

# Créer un nouveau fichier application.properties propre
RUN mkdir -p /app/src/main/resources && \
    echo "spring.application.name=prevention-platform" > /app/src/main/resources/application.properties && \
    echo "" >> /app/src/main/resources/application.properties && \
    echo "# Serveur" >> /app/src/main/resources/application.properties && \
    echo "server.port=8080" >> /app/src/main/resources/application.properties && \
    echo "" >> /app/src/main/resources/application.properties && \
    echo "# Datasource PostgreSQL" >> /app/src/main/resources/application.properties && \
    echo "spring.datasource.url=\${DATABASE_URL:jdbc:postgresql://dpg-d29sluuuk2gs73e38v00-a.oregon-postgres.render.com:5432/prevention_8vgh}" >> /app/src/main/resources/application.properties && \
    echo "spring.datasource.username=\${DATABASE_USERNAME:prevention_8vgh_user}" >> /app/src/main/resources/application.properties && \
    echo "spring.datasource.password=\${DATABASE_PASSWORD:pcni09v1gdbbHmEb3PVE2mXGBWyvLg38}" >> /app/src/main/resources/application.properties && \
    echo "spring.datasource.driver-class-name=org.postgresql.Driver" >> /app/src/main/resources/application.properties && \
    echo "" >> /app/src/main/resources/application.properties && \
    echo "# JPA & Hibernate" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.hibernate.ddl-auto=update" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.show-sql=true" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.properties.hibernate.format_sql=true" >> /app/src/main/resources/application.properties && \
    echo "spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect" >> /app/src/main/resources/application.properties && \
    echo "" >> /app/src/main/resources/application.properties && \
    echo "# Upload de fichiers" >> /app/src/main/resources/application.properties && \
    echo "spring.servlet.multipart.enabled=true" >> /app/src/main/resources/application.properties && \
    echo "spring.servlet.multipart.max-file-size=10MB" >> /app/src/main/resources/application.properties && \
    echo "spring.servlet.multipart.max-request-size=10MB" >> /app/src/main/resources/application.properties && \
    echo "" >> /app/src/main/resources/application.properties && \
    echo "# Logging" >> /app/src/main/resources/application.properties && \
    echo "logging.level.com.sante.prevention=DEBUG" >> /app/src/main/resources/application.properties && \
    echo "logging.level.org.springframework.web=DEBUG" >> /app/src/main/resources/application.properties && \
    echo "logging.level.org.hibernate.SQL=DEBUG" >> /app/src/main/resources/application.properties && \
    echo "" >> /app/src/main/resources/application.properties && \
    echo "# Actuator" >> /app/src/main/resources/application.properties && \
    echo "management.endpoints.web.exposure.include=health,info,metrics" >> /app/src/main/resources/application.properties

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /app/target/prevention-platform-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]