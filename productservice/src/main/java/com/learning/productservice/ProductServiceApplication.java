package com.learning.productservice;

import com.learning.productservice.entity.Product;
import com.learning.productservice.repository.ProductRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(ProductServiceApplication.class, args);
		ProductRepository productRepository = context.getBean(ProductRepository.class);
		productRepository.save(Product.builder().name("milk").variant("1 ltr").price(14.5).build());
		productRepository.save(Product.builder().name("sugar").variant("500 gms").price(30.0).build());
	}

}
