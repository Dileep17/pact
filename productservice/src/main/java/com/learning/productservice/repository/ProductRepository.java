package com.learning.productservice.repository;

import com.learning.productservice.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByName(String name);
    Product findById(long id);
}
