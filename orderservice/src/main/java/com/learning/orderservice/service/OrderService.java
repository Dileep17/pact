package com.learning.orderservice.service;

import com.learning.orderservice.model.OrderModel;
import com.learning.orderservice.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderService {

    @Autowired
    ProductServiceClient productService;

    public OrderModel getOrder(){
        List<ProductModel> products = new ArrayList<>();
        products.add(productService.getProduct(1l));
        products.add(productService.getProduct(2l));
        return OrderModel.builder().id(1L).products(products).build();
    }
}
