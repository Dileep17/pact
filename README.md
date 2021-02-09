Repo set for spiking on PACT with java 8, springboot and Junit5

Picked below from [PACT](https://docs.pact.io/)

    Contract testing is a technique for testing an integration point by checking each application in isolation to ensure the messages it sends or receives conform to a shared understanding that is documented in a "contract".

    Pact is a code-first tool for testing HTTP and message integrations using contract tests.


<h4> Consumer :- order service </h4>

    Has following endpoints
        1. /getOrder -> fetches product information from productservice for product with id 1 & 2 (hardcoded) and returns order

Consumer contract tests are in ProductServiceContractTest.

To run tests run following command.

    $ mvn clean test

This will generate pact contract files @ <project root>/target/pacts/*.json


<h4> Producer :- product service </h4>

    Has following endpoints
        1. /product/new -> used to setup data
        2. /product/search/name/{name} -> returns list of products with matching name
        3. /product/search/id/{id} -> returns product with id

ProductServiceApplication creates two products at boot time.
ContractTest class contains product contract tests (generated by TestTemplate). Copy the pact folder generated by consumer pact tests into productservice root.
As pact broker is not setup, manually copy pact folder. @PactFolder on ContractTest maps the location of pact contract files.

To run tests run following command.

    $ mvn clean test