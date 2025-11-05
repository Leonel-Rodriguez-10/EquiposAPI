# ===== Build stage =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos solo el POM para cachear dependencias
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

# Ahora el código fuente
COPY src ./src

# Compila el JAR (Spring repackage)
RUN mvn -B -DskipTests clean package

# ===== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copiamos el jar generado. Si hay uno solo en target, esto sirve.
# (Si prefieres, puedes fijar <finalName>app</finalName> en tu pom y cambiar a /app/target/app.jar)
COPY --from=build /app/target/*.jar /app/app.jar

# Render inyecta PORT; lo pasamos a Spring Boot
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-Dserver.port=${PORT}","-XX:MaxRAMPercentage=75","-jar","/app/app.jar"]
