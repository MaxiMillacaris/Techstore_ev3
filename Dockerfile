FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q clean package -DskipTests



FROM eclipse-temurin:21-jre-alpine

WORKDIR /app


RUN addgroup -S techstore && adduser -S techstore -G techstore

COPY --from=builder /app/target/techstore-api-1.0.0.jar app.jar

USER techstore

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]