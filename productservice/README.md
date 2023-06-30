
In the project directory, you can run:

### `mvn spring-boot:run`

Above command will start the product service. 

### `mvn clean test`

Above will run all tests including contract tests. Pact contract json file from consumers of this service should be placed in pacts/ folder.

### swagger doc
    http://localhost:8081/swagger-ui/index.html
    http://localhost:8081/v3/api-docs
    http://localhost:8081/v3/api-docs.yaml
