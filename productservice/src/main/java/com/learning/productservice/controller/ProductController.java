package com.learning.productservice.controller;

import com.learning.productservice.NotFoundException;
import com.learning.productservice.entity.Product;
import com.learning.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value = "/product/new", consumes = "application/json", produces = "application/json")
    public Product create(@RequestBody Product product){
        return productRepository.save(product);
    }

    @GetMapping(value = "/product/search/name/{name}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Product> search(@PathVariable String name){
        List<Product> products = productRepository.findByName(name);
        if(products == null){
            throw new NotFoundException();
        }
        return products;
    }

    @GetMapping(value = "/product/search/id/{id}", produces = "application/json")
    @CrossOrigin(origins = "http://localhost:3000")
    public Product search(@PathVariable long id){
        Product product = productRepository.findById(id);
        if(product == null){
            throw new NotFoundException();
        }
        return product;
    }

}
