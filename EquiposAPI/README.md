# EquiposAPI — API simple de resultados de fútbol
Spring Boot 3.5.6 · Java 25 · Maven

## Endpoints
- `GET /health`
- `GET /api/leagues` (Basic)
- `GET /api/matches?leagueId=39&date=YYYY-MM-DD` (Basic)
Swagger: `/swagger-ui.html`

## Ejecutar
```bash
mvn spring-boot:run
# o
mvn clean package && java -jar target/EquiposAPI-0.0.1-SNAPSHOT.jar
```

## Auth (por defecto)
```
APP_BASIC_USER=app
APP_BASIC_PASS=1234
```
Define `FOOTBALL_API_KEY` para resultados reales, o deja vacío para mock.
