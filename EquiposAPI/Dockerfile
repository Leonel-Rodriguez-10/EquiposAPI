# ===== Build =====
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY . .
# Usa wrapper si existe; si no, instala maven y compila
RUN ./mvnw -B -DskipTests clean package || \
    (apt-get update && apt-get install -y maven && mvn -B -DskipTests clean package)

# ===== Run =====
FROM eclipse-temurin:21-jre
WORKDIR /app
# Copia el JAR sin depender del nombre exacto
ARG JAR_FILE=/app/target/*-SNAPSHOT.jar
COPY --from=build ${JAR_FILE} /app/app.jar

ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=${PORT}","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]
