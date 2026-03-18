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

With the application running (via `mvn spring-boot:run` or Docker), use Swagger to explore and test the API interactively.

| Resource | URL |
|----------|-----|
| **Swagger UI** | http://localhost:8081/swagger-ui.html |
| **OpenAPI JSON** | http://localhost:8081/v3/api-docs |

**How to use:**
1. Open **http://localhost:8081/swagger-ui.html** in your browser.
2. Expand an endpoint (e.g. `POST /api/countries`).
3. Click **Try it out**, edit the request body if needed.
4. Click **Execute** to send the request.
5. View the response (status code and body) below.

The same URLs apply when running via Docker (`-p 8081:8081`).

## Build

```bash
mvn clean package
```

## Docker

```bash
# Build image
docker build -t country-info-service .

# Create .env from template (edit with your credentials)
cp .env.example .env

# Run using .env for credentials
docker run -p 8081:8081 --env-file .env country-info-service
```

Use `host.docker.internal` in the datasource URL to reach MSSQL on the host from the container. `.env` is gitignored.

## Database

SQL authentication. Connection configured in `application.yml`:
- **Server:** DESKTOP-K0286QP\SQLEXPRESS
- **Database:** master
- **User:** zentra_app

To use a dedicated database instead, have an admin run `CREATE DATABASE countrydb` and grant `zentra_app` access, then set `databaseName=countrydb` in `application.yml`.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/countries` | Submit country name – normalizes, fetches ISO + full info from SOAP, persists to DB |
| GET | `/api/countries` | List all persisted countries |
| GET | `/api/countries/{id}` | Get country by database ID |
| PUT | `/api/countries/{id}` | Update country by ID |
| DELETE | `/api/countries/{id}` | Delete country by ID |
| GET | `/api/countries/full-info/{isoCode}` | Fetch full country details from SOAP FullCountryInfo |

### Example: POST country name

```bash
curl -X POST http://localhost:8081/api/countries \
  -H "Content-Type: application/json" \
  -d '{"name": "tanzania"}'
# Response: {"name":"Tanzania","isoCode":"TZ","id":1}

curl -X POST http://localhost:8081/api/countries \
  -H "Content-Type: application/json" \
  -d '{"name": "KENYA"}'
# Response: {"name":"Kenya","isoCode":"KE","id":1}
```

### Example: GET full country info by ISO code

```bash
curl http://localhost:8081/api/countries/full-info/KE
# Response: {"isoCode":"KE","name":"Kenya","capitalCity":"Nairobi","phoneCode":"254","continentCode":"AF","currencyIsoCode":"KES","countryFlag":"http://..."}
```

### Example: CRUD operations

```bash
# List all countries
curl http://localhost:8081/api/countries

# Get country by ID
curl http://localhost:8081/api/countries/1

# Update country
curl -X PUT http://localhost:8081/api/countries/1 -H "Content-Type: application/json" \
  -d '{"name":"Kenya","capitalCity":"Nairobi","phoneCode":"254","continentCode":"AF","currencyIsoCode":"KES","countryFlag":"http://..."}'

# Delete country
curl -X DELETE http://localhost:8081/api/countries/1
```

## Case Study Phases

Reference: [Step-by-Step Resolution Plan](docs/RESOLUTION_PLAN.md) – full phased implementation guide.

- **Phase 1** – Project setup (Spring Boot, Git, Maven wrapper)
- **Phase 2** – [SoapUI Setup](docs/SOAPUI_SETUP.md) – Import WSDL, verify CountryISOCode and FullCountryInfo
- **Phase 3** – REST controller with POST endpoint and sentence case conversion
- **Phase 4–5** – SOAP integration (CountryISOCode, FullCountryInfo)
- **Phase 6** – Database persistence
- **Phase 7** – CRUD REST APIs
- **Phase 8** – Logging (SLF4J), error handling (GlobalExceptionHandler), validation
- **Phase 9** – [Docker](#docker), [SoapUI setup](docs/SOAPUI_SETUP.md)
- **Phase 10** – [Version control & sharing](#version-control--sharing)

## Version control & sharing

1. **Push to remote** (GitHub/GitLab/Bitbucket):
   ```bash
   git add -A && git commit -m "Complete country-info-service (Phases 1-10)"
   git push origin main
   ```

2. **Grant access** – Add `appdevelopment@ncbagroup.com` as collaborator:
   - **GitHub:** Repository → Settings → Collaborators → Add people
   - **GitLab:** Project → Members → Invite member
   - **Bitbucket:** Repository settings → User and group access

3. **Verify** – Ensure all work is committed and pushed before handover.

## Error Handling

The API returns consistent error responses:

| Status | When |
|--------|------|
| 400 Bad Request | Validation failed (e.g. empty name, name too long) |
| 404 Not Found | Country not found by ID or ISO code |
| 502 Bad Gateway | SOAP service unavailable or returned error |
| 500 Internal Server Error | Unexpected server error |

All error responses use a consistent `ErrorResponse` structure with `statusCode`, `error`, `detail`, and optional `fieldErrors`:
```json
{
  "statusCode": 404,
  "error": "Not found",
  "detail": "Country not found: 999",
  "timestamp": "2026-03-18T10:00:00.000Z",
  "fieldErrors": null
}
```

Validation errors include field-level details:
```json
{
  "statusCode": 400,
  "error": "Validation failed",
  "detail": "Invalid request body",
  "timestamp": "...",
  "fieldErrors": [{"field": "name", "message": "Country name is required"}]
}
```

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
├── Dockerfile              # Multi-stage build
└── pom.xml
```
