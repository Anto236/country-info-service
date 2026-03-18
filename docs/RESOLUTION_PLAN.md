# Step-by-Step Resolution Plan

Channel Developer Case Study – Country Information Service. Reference document for the phased implementation approach.

---

## Phase 1: Project setup (~15–20 min)

| Step | Task | Details |
|------|------|---------|
| 1.1 | Create new application | Use Spring Boot (Java) or .NET Core based on preference |
| 1.2 | Initialize Git repository | `git init`, create `.gitignore` |
| 1.3 | First commit | Add base project files and commit |

---

## Phase 2: SoapUI setup (~10 min)

| Step | Task | Details |
|------|------|---------|
| 2.1 | Install SoapUI | Download from SmartBear, install |
| 2.2 | Import SOAP project | Import WSDL: `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL` |
| 2.3 | Verify project | Confirm operations like **CountryISOCode** and **FullCountryInfo** |

---

## Phase 3: REST controller – country name ingestion (~15–20 min)

| Step | Task | Details |
|------|------|---------|
| 3.1 | Create POST endpoint | Path: `/api/country` or similar |
| 3.2 | Request body model | JSON: `{ "name": "Tanzania" }` |
| 3.3 | Sentence case conversion | Normalize input (e.g. `"kenya"` → `"Kenya"`) |

---

## Phase 4: SOAP integration – ISO code (~20–25 min)

| Step | Task | Details |
|------|------|---------|
| 4.1 | Add SOAP client | Spring: `spring-boot-starter-web-services`; .NET: ServiceReference or similar |
| 4.2 | Call CountryISOCode | SOAP request parameter: `sCountryName`; response: `countryIsoCodeResult` |
| 4.3 | Extract ISO code | Use the ISO value from the response |

---

## Phase 5: SOAP integration – full country info (~15–20 min)

| Step | Task | Details |
|------|------|---------|
| 5.1 | Call FullCountryInfo | SOAP request parameter: `sCountryISOCode` |
| 5.2 | Extract full details | Use `FullCountryInfoResult` (name, capital, currency, etc.) |

---

## Phase 6: Persistence (~25–30 min)

| Step | Task | Details |
|------|------|---------|
| 6.1 | Configure database | H2, PostgreSQL, MySQL, SQL Server, etc. |
| 6.2 | Design entity | Map SOAP response fields to entity (e.g. `CountryInfo`) |
| 6.3 | Create repository | Spring Data JPA / Entity Framework |
| 6.4 | Save data | Store full country info after successful SOAP call |

---

## Phase 7: REST CRUD APIs (~25–30 min)

| Step | Task | Endpoint / method |
|------|------|-------------------|
| 7.1 | Fetch all | `GET /api/countries` |
| 7.2 | Fetch by ID | `GET /api/countries/{id}` |
| 7.3 | Update | `PUT /api/countries/{id}` |
| 7.4 | Delete | `DELETE /api/countries/{id}` |

---

## Phase 8: Quality and robustness (~15–20 min)

| Step | Task | Details |
|------|------|---------|
| 8.1 | Logging | Use SLF4J/Log4j (Spring) or ILogger (.NET); log key steps and errors |
| 8.2 | Error handling | Global exception handler, validation, clear error responses |
| 8.3 | Validation | Validate name (e.g. non-empty); handle SOAP failures |

---

## Phase 9: Docker & documentation (~15–20 min)

| Step | Task | Details |
|------|------|---------|
| 9.1 | Dockerfile | Multi-stage build (build + runtime image) |
| 9.2 | docker-compose (optional) | App + DB for local runs |
| 9.3 | Documentation | README with: run steps, API examples, SoapUI usage, Docker commands |

---

## Phase 10: Version control & sharing (~5 min)

| Step | Task | Details |
|------|------|---------|
| 10.1 | Push frequently | Push to GitHub/GitLab/etc. during development |
| 10.2 | Grant access | Add `appdevelopment@ncbagroup.com` as collaborator |
| 10.3 | Final commit | Ensure all work is committed and pushed |

---

## Suggested Flow (5 hours total)

| Time | Phases |
|------|--------|
| 0:00–0:20 | Phase 1 + 2 (project + SoapUI) |
| 0:20–0:45 | Phase 3 + 4 (REST ingestion + first SOAP call) |
| 0:45–1:15 | Phase 5 + 6 (full info + persistence) |
| 1:15–1:45 | Phase 7 (CRUD APIs) |
| 1:45–2:05 | Phase 8 (logging + error handling) |
| 2:05–2:25 | Phase 9 (Docker + README) |
| 2:25–5:00 | Testing, refinement, pushes, and final handover |

---

## Important technical notes

| Topic | Details |
|-------|---------|
| **WSDL URL** | `http://webservices.oorsprong.org/websamples.countryinfo/CountryInfoService.wso?WSDL` |
| **SOAP operations** | `CountryISOCode` and `FullCountryInfo` |
| **Name normalization** | Use sentence case for country names before calling SOAP (e.g. `"tanzania"` → `"Tanzania"`) |

---

## Related docs

- [README](../README.md) – Project overview, quick start, API examples
- [SOAPUI_SETUP.md](SOAPUI_SETUP.md) – SoapUI configuration and WSDL import (if available)
