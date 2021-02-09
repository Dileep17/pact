package com.learning.orderservice.service;

import com.learning.orderservice.model.ProductModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductServiceClient {

    RestTemplate restTemplate;

    public ProductServiceClient(@Value("${productService}") String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public ProductModel getProduct(long id){
        ResponseEntity<String> response = restTemplate.getForEntity("/product/search/id/" + id, String.class);
        return restTemplate.getForObject("/product/search/id/" + id, ProductModel.class);
    }

}
