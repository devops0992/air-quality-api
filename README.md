# Air Quality API

Base application for the **Real-Time Air Quality Intelligence Platform**.

This repository intentionally contains only the application. AWS ECR, Terraform, EKS, Jenkins, Kubernetes and monitoring will be added as separate DevOps phases.

## Requirements

- Java 17+
- Maven 3.9+

## Run locally

```bash
mvn clean test
mvn spring-boot:run
```

The API starts on:

```text
http://localhost:8080
```

## Endpoints

### Health

```text
GET /api/v1/health
```

Example response:

```json
{
  "status": "UP",
  "service": "air-quality-api"
}
```

### Info

```text
GET /api/v1/info
```

### Version

```text
GET /api/v1/version
```

### Spring Boot health

```text
GET /actuator/health
```

### Prometheus metrics

```text
GET /actuator/prometheus
```

## Configuration

Environment variables:

```text
SERVER_PORT=8080
APP_VERSION=0.0.1
APP_ENV=local
```

## Planned DevOps phases

```text
Phase 1
Spring Boot application
        |
        v
Docker
        |
        v
AWS ECR
        |
        v
Terraform
        |
        v
AWS EKS
        |
        v
Jenkins CI/CD
```

Later:

```text
OpenAQ
   |
   v
Kafka
   |
   v
Stream Processing
   |
   +----> S3
   +----> PostgreSQL
   +----> OpenSearch
   |
   v
ML / AI
```
