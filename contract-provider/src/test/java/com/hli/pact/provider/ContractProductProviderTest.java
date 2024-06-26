package com.hli.pact.provider;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.hli.pact.provider.entity.Product;
import com.hli.pact.provider.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@WebMvcTest
@Provider("ProductService")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@PactBroker
//@ExtendWith(SpringExtension.class)
public class ContractProductProviderTest {

    /*@LocalServerPort
    int port;*/

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MockMvcTestTarget(mockMvc));
    }

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void verifyPact(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("products exists")
    void toProductsExistState() {
        when(productRepository.fetchAll()).thenReturn(Arrays.asList(new Product("09", "CREDIT_CARD", "Gem Visa", "v1"),
                new Product("10", "CREDIT_CARD", "28 Degrees", "v1")));
    }

    @State("no products exist")
    void toNoProductsExistState() {
        when(productRepository.fetchAll()).thenReturn(Collections.EMPTY_LIST);
    }

    @State("product with ID 11 does not exist")
    void toProductWithIDElevenDoesNotExistsState() {
        when(productRepository.getById("11")).thenReturn(Optional.ofNullable(null));
    }

    @State("product with ID 10 exists")
    void toProductWithIDTenExistsState() {
        when(productRepository.getById("10")).thenReturn(Optional.of(new Product("10", "CREDIT_CARD", "28 Degrees", "v1")));
    }

}
