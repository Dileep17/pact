package com.learning.orderservice.contracttests;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.learning.orderservice.model.ProductModel;
import com.learning.orderservice.service.ProductServiceClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(PactConsumerTestExt.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
                properties = "productService:http://localhost:8081",
                classes = ProductServiceClient.class)
@PactTestFor(providerName = "productservice", port = "8081")
public class ProductServiceContractTest {

    private final long ID = 1l ;
    PactDslJsonBody pactDslGetProductByIdBody;
    ProductModel expectedGetProductByIdBodyResponse;


    @Autowired
    ProductServiceClient productServiceClient;

    @Pact(provider = "productservice", consumer = "orderservice")
    public RequestResponsePact searchForExistingProductId(PactDslWithProvider pactDsl) throws JsonProcessingException {

        pactDslGetProductByIdBody = new PactDslJsonBody()
                .numberType("id", ID)
                .stringType("name")
                .decimalType("price")
                .stringType("variant");

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        RequestResponsePact requestResponsePact = pactDsl.given("products exist")
                .uponReceiving("a request to search product by Id")
                .path("/product/search/id/" + ID)
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(pactDslGetProductByIdBody)
                .toPact();

        return requestResponsePact;
    }

    @Pact(provider = "productservice", consumer = "orderservice")
    public RequestResponsePact searchForNonExistingProductId(PactDslWithProvider pactDsl){

        return pactDsl.given("products does not exist")
                .uponReceiving("a request to /product/search/id/2")
                .path("/product/search/id/2")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "searchForExistingProductId")
    public void shouldGetProductDetailsWhenSearchWithExistingProductId() throws JsonProcessingException {
        ProductModel actualProduct = productServiceClient.getProduct(ID);
        Assertions.assertNotNull(actualProduct);
    }

    @Test
    @PactTestFor(pactMethod = "searchForNonExistingProductId")
    public void shouldGetNotFoundWhenSearchWithNonExistingProductId(){
        HttpClientErrorException actualException = Assertions.assertThrows(HttpClientErrorException.class, () -> productServiceClient.getProduct(2l));
        assertEquals(HttpStatus.NOT_FOUND, actualException.getStatusCode());
    }

}
