# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copia o pom.xml e baixa dependências para aproveitar cache
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Builda o JAR e pula os testes (para build mais rápido)
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copia o JAR gerado no build
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]