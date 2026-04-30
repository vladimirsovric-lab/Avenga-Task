# API Automation Testing – Online Bookstore

## Overview

This project contains automated API tests for the FakeRestAPI Bookstore.

The goal was to design a clean, maintainable, and scalable API test suite with strong coverage of both positive and negative scenarios.

---

## Tech Stack

* Java 17
* Maven
* JUnit 5
* RestAssured
* Docker
* GitHub Actions (CI/CD)

---

## Test Coverage

The Books API is fully covered with:

* GET all books
* GET book by valid ID
* GET book by non-existing ID
* GET book by invalid ID format
* POST create book (valid & invalid payloads)
* PUT update book (valid & invalid scenarios)
* DELETE book (valid & invalid cases)

Special attention is given to negative scenarios such as:

* invalid payloads
* empty request bodies
* invalid and non-existing IDs

---

## Notes on API Behavior

FakeRestAPI is a mock API and does not persist data.

Some endpoints behave differently from real-world APIs. For example:

* Updating a non-existing book returns `200 OK` instead of `404 Not Found`

Tests are written to reflect actual API behavior while documenting these inconsistencies.

---

## Running Tests Locally

```
mvn test
```

---

## Running Tests with Docker

Build the image:

```
docker build -t bookstore-api-tests .
```

Run tests:

```
docker run --rm bookstore-api-tests
```

Run with environment variable:

```
docker run --rm -e BASE_URL=https://fakerestapi.azurewebsites.net bookstore-api-tests
```

---

## Test Reporting

The project provides two types of reports:

* **Allure Report** – primary, detailed, and human-readable report
* **Maven Surefire Report** – basic execution summary

---

### Allure Report (Primary)

Allure provides a rich and interactive test report with execution details, steps, and statistics.

#### Generate report locally

```
mvn clean test
mvn allure:report
```

#### Report location

```text
target/site/allure-maven-plugin/index.html
```

#### How to view the report

To properly view the report, start a local server:

```bash
cd target/site/allure-maven-plugin
python3 -m http.server 8080
```

Then open:

```text
http://localhost:8080
```

> Opening the file directly may not work correctly due to browser security restrictions.

#### CI/CD

In GitHub Actions, the Allure report is:

* Generated during the pipeline execution
* Uploaded as a workflow artifact

To view it:

1. Download the artifact from the GitHub Actions run
2. Extract it locally
3. Serve it using the same method described above

---

### Maven Surefire Report (Secondary)

Surefire provides a simple summary of test execution results.

#### Generate report

```
mvn test surefire-report:report
```

#### Report location

```text
target/site/surefire-report.html
```

This report is mainly used for quick verification and as a fallback, while Allure serves as the primary reporting tool.


## CI/CD Pipeline

GitHub Actions is used to:

* Build the Docker image
* Execute tests inside a container
* Generate and upload test reports

The pipeline runs on every push and pull request.

---

## Design Decisions

* The framework is intentionally kept simple and readable
* Minimal abstraction is used to avoid overengineering
* Focus is on test coverage, clarity, and maintainability
* Environment-based configuration allows easy CI integration
