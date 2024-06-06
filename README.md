# Cost Management API

## Features

- **Total Cost Calculation**: Calculate the total cost based on optional filters (time, location, SKU).
- **Grouped Cost Calculation**: Group costs by specified fields (date, country, service) and calculate the total for
  each group.
- **Search by Label and Country**: Search cost records by label key-value pairs and country with pagination support.
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **Dockerized**: Containerized using Docker for easy setup and deployment.
- **CSV Heading Validation**: Validate the CSV file headings to ensure the correct format.

## Endpoints

### 1. Get Total Cost

**Endpoint**: `/total-cost`  
**Method**: `GET`

**Parameters**:

- `startTime` (optional): Start time filter (format: `yyyy-MM-dd` or `yyyy-MM-dd HH:mm:ss`)
- `endTime` (optional): End time filter (format: `yyyy-MM-dd` or `yyyy-MM-dd HH:mm:ss`)
- `location` (optional): Location filter
- `skuId` (optional): SKU ID filter

**Response**: `200 OK` with total cost as `BigDecimal`.

### 2. Get Cost Grouped

**Endpoint**: `/cost-grouped`  
**Method**: `GET`

**Parameters**:

- `fields` (optional): List of fields to group by (date, country, service)
- `isSorted` (optional): Whether to sort the grouped results (default: false)

**Response**: `200 OK` with grouped cost data.

### 3. Search by Label and Country

**Endpoint**: `/search`  
**Method**: `GET`

**Parameters**:

- `labelKey` (optional): Label key filter
- `labelValue` (optional): Label value filter
- `country` (optional): Country filter
- `page` (required): Page number (minimum: 1)
- `size` (required): Page size (maximum: 20)

**Response**: `200 OK` with a paged list of cost records.

## Docker

The application is containerized using Docker. The Dockerfile is set up to use a multi-stage build to ensure a clean
final image.

### Dockerfile

```Dockerfile
# Use a multi-stage build to create a clean final image
FROM openjdk:23-jdk-slim AS build

# Update package lists and install Maven
RUN apt-get update && apt-get install -y maven

COPY pom.xml ./
COPY src src
RUN mvn dependency:resolve
RUN mvn package

FROM openjdk:23-jdk-slim
WORKDIR demo

# Expose port 8080
EXPOSE 8080

COPY --from=build target/*.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]
```

## Setup and Run

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/bonda29/Zetta-Java-Assignment.git
   cd Zetta-Java-Assignment
   ```

2. **Build the Docker Image**:
   ```sh
   docker build -t cost-management-api .
   ```

3. **Run the Docker Container**:
   ```sh
   docker run -p 8080:8080 cost-management-api
   ```

The API will be accessible at `http://localhost:8080`.

## API Documentation

Interactive API documentation is available at `http://localhost:8080/swagger-ui.html` once the application is running.

## Notes

- Ensure the CSV file is placed in the correct directory `src/main/resources/`.
- The application handles large CSV files efficiently to ensure performance and memory optimization.
