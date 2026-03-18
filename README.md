# country-info-service

Channel Developer Case Study – Country Information Service. A Spring Boot application that receives country names via REST, calls SOAP web services for ISO codes and full country info, and persists data for CRUD operations.

## Project Details

| Item | Value |
|------|-------|
| **Project Name** | country-info-service |
| **Group ID** | com.ncba |
| **Artifact ID** | country-info-service |
| **Java** | 21 |
| **Spring Boot** | 3.4.4 |

## Prerequisites

- Java 21
- Maven 3.9+
- MS SQL Server (local instance)
- SoapUI (for WSDL testing) – see [docs/SOAPUI_SETUP.md](docs/SOAPUI_SETUP.md)

## Quick Start

**1. Run the application:**
```bash
mvn spring-boot:run
```

### Swagger UI

With the application running, open **http://localhost:8080/swagger-ui.html** to explore and test the API interactively.

## Build

```bash
mvn clean package
```

## Database

SQL authentication. Connection configured in `application.yml`:
- **Server:** DESKTOP-K0286QP\SQLEXPRESS
- **Database:** master
- **User:** zentra_app

To use a dedicated database instead, have an admin run `CREATE DATABASE countrydb` and grant `zentra_app` access, then set `databaseName=countrydb` in `application.yml`.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/countries` | Submit country name – normalizes to sentence case, fetches ISO code from SOAP API |

### Example: POST country name

```bash
curl -X POST http://localhost:8080/api/countries \
  -H "Content-Type: application/json" \
  -d '{"name": "tanzania"}'
# Response: {"name":"Tanzania","isoCode":"TZ"}

curl -X POST http://localhost:8080/api/countries \
  -H "Content-Type: application/json" \
  -d '{"name": "KENYA"}'
# Response: {"name":"Kenya","isoCode":"KE"}
```

## Case Study Phases

- **Phase 1** – Project setup (Spring Boot, Git, Maven wrapper)
- **Phase 2** – [SoapUI Setup](docs/SOAPUI_SETUP.md) – Import WSDL, verify CountryISOCode and FullCountryInfo
- **Phase 3** – REST controller with POST endpoint and sentence case conversion
- **Phase 4–5** – SOAP integration (CountryISOCode, FullCountryInfo)
- **Phase 6** – Database persistence
- **Phase 7** – CRUD REST APIs
- **Phase 9** – Docker, documentation

## Project Structure

```
country-info-service/
├── src/main/java/com/ncba/countryinfo/
│   ├── controller/         # REST controllers
│   ├── dto/                # Request/response DTOs
│   ├── model/entity/       # JPA entities
│   ├── repository/         # Spring Data JPA repositories
│   ├── service/            # Business logic layer
│   └── util/               # Utilities (e.g. StringUtils)
├── docs/                   # Setup and run documentation
└── pom.xml
```
