package com.learning.orderservice.contracttests;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final String NAME = "butter";
    private final String VARIANT = "100 gms";
    private final double PRICE = 20.12;
    private final long ID = 1l ;

    @Autowired
    ProductServiceClient productServiceClient;

    @Pact(provider = "productservice", consumer = "orderservice")
    public RequestResponsePact searchForExistingProductId(PactDslWithProvider pactDsl) throws JsonProcessingException {

//        we can define dslbody as below, but there is a high chance of missing model changes
//        DslPart dslBody = LambdaDsl.newJsonBody(
//                (object) -> object.stringType("name", NAME)
//                        .numberValue("id", ID)
//                        .decimalType("price", 1100.01)
//                        .stringType("variant", "25kg")
//                        )
//                .build();

        ProductModel butter = ProductModel.builder().id(ID).name(NAME).variant(VARIANT).price(PRICE).build();
        ObjectMapper objectMapper = new ObjectMapper();


        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");

        return pactDsl.given("product with id exist", "product", objectMapper.writeValueAsString(butter))
                .uponReceiving("a request to /product/search/id/1")
                .path("/product/search/id/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body(objectMapper.writeValueAsString(butter))
                .toPact();
    }

    @Pact(provider = "productservice", consumer = "orderservice")
    public RequestResponsePact searchForNonExistingProductId(PactDslWithProvider pactDsl){

        return pactDsl.given("product with id does not exist", "id", 2)
                .uponReceiving("a request to /product/search/id/2")
                .path("/product/search/id/2")
                .method("GET")
                .willRespondWith()
                .status(404)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "searchForExistingProductId")
    public void shouldGetProductDetailsWhenSearchWithExistingProductId(){
        ProductModel product = productServiceClient.getProduct(1l);
        Assertions.assertEquals(ID, product.getId());
        Assertions.assertEquals(NAME, product.getName());
    }

    @Test
    @PactTestFor(pactMethod = "searchForNonExistingProductId")
    public void shouldGetNotFoundWhenSearchWithNonExistingProductId(){
        HttpClientErrorException actualException = Assertions.assertThrows(HttpClientErrorException.class, () -> productServiceClient.getProduct(2l));
        assertEquals(HttpStatus.NOT_FOUND, actualException.getStatusCode());
    }

}
