# GMtask

### Install and run
$ ./mvnw spring-boot:run (from project directory)

### Start test
$ ./mvnw test

### BASE URL
http://localhost:8080/csv"

### REST API
* POST `http://localhost:8080/csv`  upload the data
* GET  `http://localhost:8080/csv/all` fetch all data
* GET `http://localhost:8080/csv?code={code}` Fetch by code
* DELETE `http://localhost:8080/csv`  Delete all data

### Error Details
Application provides detailed error object in next format
```json
{
  "url": "http://localhost:8080/csv",
  "details": "Csv Item with code '307047009' not found.",
  "timestamp": "2023-11-01T22:24:03.544595"
}
```

### H2 DB access
http://localhost:8080/h2

JDBC URL: jdbc:h2:mem:csvdb

* username: sa
* (empty)