# FxDealsWarehouse

A Spring Boot application for managing Foreign Exchange (FX) deals in a data warehouse

## Features

- Accepts and validates FX deal data via REST API
- Ensures currency codes are valid (ISO 4217 format)
- Prevents saving duplicate deals using unique dealId
- Comprehensive validation and error handling
- No rollback policy - all valid imported rows are persisted
- Dockerized for easy local setup and deployment
- Unit testing with high code coverage

## Technologies Used

- Java 21 – Main development language
- Spring Boot 3.5.4 – Core framework
- PostgreSQL – Relational database
- Maven – Build tool
- MapStruct – DTO to Entity mapping
- Lombok – Reduces boilerplate
- Jakarta Bean Validation – Input validation
- JUnit 5 – Unit testing
- SLF4J – Logging
- Docker & Docker Compose – Containerization

## Setup Instructions

**Prerequisites:**
- Java 21
- Maven
- Docker & Docker Compose
- Git

**Clone the Repository**
```bash
git clone https://github.com/youssefhihi/fx-deals-warehouse.git
cd fx-deals-warehouse
```

**Build and Run**
```bash
make up
```
This starts the Spring Boot app and PostgreSQL DB using Docker Compose.


## Makefile Commands

- `make help`: Show available commands
- `make up`: Start Docker containers in detached mode
- `make down`: Stop and remove Docker containers
- `make test`: Run Maven tests
- `make clean`: Clean Maven project and remove target directory

## API Endpoints

### Insert Deal
- **Endpoint**: `POST /api/v1/deals`
- **URL**: `http://localhost:8080/api/v1/deals`
- **Request JSON Example**:
```json
{
  "id": 12345,
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "amount": 1500.00
}
```
- **Response** (HTTP 201 Created):
```json
{
  "id": 12345,
  "fromCurrency": "USD",
  "toCurrency": "EUR",
  "dealTimestamp": "2025-07-27T19:15:00",
  "dealAmount": 1500.0
}
```

## Request Validation

- **Fields Validated**:
    - `id`: required, unique
    - `fromCurrency` and `toCurrency`: valid ISO 4217 codes, not equal
    - `amount`: Must be positive and not null

## Database Schema

- **Database**: PostgreSQL
- **Table**: `fx_deals` table with columns:
    - `id` (String, PK)
    - `from_currency` (String, 3 chars)
      - `to_currency` (String, 3 chars)
    - `deal_timestamp` (LocalDateTime)
    - `amount` (BigDecimal)
- **JPA**: Spring Data JPA with auditing for timestamp
- **Duplicate Prevention**: Unique constraint on `id` column

## Testing

- **Framework**: JUnit 5 with Spring Test
- **Coverage**:
    - Unit tests for service layer (`FxDealServiceImplTest`)
    - Currency validation tests (`CurrencyValidatorImplTest`, `CurrencyUtilsTest`)
- **Run**: `make test`

## Dockerization

- **Dockerfile**: Multi-stage build
  - **Docker Compose**:
    - runs app (port 8080) and PostgreSQL (port 5432)

## Project Structure

```
    fxDeal/
    ├── docker-compose.yml
    ├── Dockerfile
    ├── Makefile
    ├── pom.xml
    ├── README.md
    ├── src/
    │   ├── main/java/com/progressoft/fxDeal/
    │   │   ├── config/
    │   │   ├── controller/
    │   │   ├── dto/
    │   │   ├── entity/
    │   │   ├── exception/
    │   │   ├── mapper/
    │   │   ├── repository/
    │   │   ├── service/
    │   │       └── validator/
    │   └── resources/
    └── test/
```

## Error Handling

- Global exception handler returns JSON error responses with timestamp, status, and message fields.