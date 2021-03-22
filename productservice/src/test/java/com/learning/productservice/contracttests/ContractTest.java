package com.learning.productservice.contracttests;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
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

@Provider("productservice")
@ExtendWith(SpringExtension.class)
//@PactFolder("pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@PactBroker(scheme = "http", host = "localhost", port = "9292", authentication = @PactBrokerAuth(username = "webadmin", password = "password@312"))
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
//        System.setProperty("pact.verifier.publishResults", "true");
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("products does not exist")
    public void removeProductsFromDB() {
        productRepository.deleteAll();
    }

    @State("products exist")
    public void createProduct(){
        Product someProduct = Product.builder().name("milk").variant("25 ml").price(12.12).build();
        productRepository.save(someProduct);
        someProduct = Product.builder().name("milk").variant("25 ml").price(12.12).build();
        productRepository.save(someProduct);
    }

}
