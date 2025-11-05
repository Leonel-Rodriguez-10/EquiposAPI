# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia el POM y prepara el caché de dependencias
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -B -q -DskipTests dependency:go-offline

# Copia el código y compila
COPY src ./src
# Empaqueta + repackage para asegurar el MANIFEST correcto
RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests clean package spring-boot:repackage \
 && cp target/*.jar app.jar

# ===== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el jar final
COPY --from=build /app/app.jar /app/app.jar

# Render inyecta PORT; lo obedecemos
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-Dserver.port=${PORT}","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]
