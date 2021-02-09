package com.learning.productservice.contracttests;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.productservice.entity.Product;
import com.learning.productservice.model.HibernateSequence;
import com.learning.productservice.repository.ProductRepository;
import com.learning.productservice.repository.SequenceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigInteger;
import java.util.Map;

@Provider("productservice")
@ExtendWith(SpringExtension.class)
@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ContractTest {

    @LocalServerPort
    private int port;

    @Autowired
    SequenceRepository sequenceRepository;

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void before(PactVerificationContext context) {
        sequenceRepository.deleteAll();
        sequenceRepository.save(HibernateSequence.builder().next_val(1).build());
        productRepository.deleteAll();
        context.setTarget(new HttpTestTarget("localhost", port, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("product with id exist")
    public void createProductWithId(Map<String, Object> params) throws JsonProcessingException {
        String productJson = (String) params.get("product");
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = objectMapper.readValue(productJson, Product.class);
        product.setId(null);
        productRepository.save(product);
    }

    @State("product with id does not exist")
    public void removeProductWithId(Map<String, Object> params) {
        long id = ((BigInteger)params.get("id")).longValue();
        if(productRepository.existsById(id))
            productRepository.deleteById(id);
    }

}
