# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia lo mínimo (mejores cachés)
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests clean package

# ===== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia el jar sin depender del nombre exacto
COPY --from=build /app/target/*-SNAPSHOT.jar /app/app.jar

ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=${PORT}","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]

