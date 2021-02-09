package com.learning.orderservice.controller;

import com.learning.orderservice.model.OrderModel;
import com.learning.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/getOrder")
    public OrderModel getOrder(){
        return orderService.getOrder();
    }

}
