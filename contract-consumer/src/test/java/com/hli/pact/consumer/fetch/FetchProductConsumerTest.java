package com.hli.pact.consumer.fetch;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.hli.pact.consumer.entity.Product;
import com.hli.pact.consumer.service.ConsumerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonArrayMinLike;
import static au.com.dius.pact.consumer.dsl.LambdaDsl.newJsonBody;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProductService", hostInterface = "localhost", port = "8000")
public class FetchProductConsumerTest {

    private final Map<String, String> headers = Map.of("Content-Type", "application/json");

    @Pact(consumer = "FetchConsumerApplication", provider = "ProductService")
    RequestResponsePact getAllProducts(PactDslWithProvider builder) {
        return builder.given("products exists")
                .uponReceiving("get all products")
                .method("GET")
                .path("/products")
                .willRespondWith()
                .status(200)
                .headers(headers())
                .body(newJsonArrayMinLike(2, array ->
                        array.object(object -> {
                            object.stringType("id", "09");
                            object.stringType("type", "CREDIT_CARD");
                            object.stringType("name", "Gem Visa");
                            object.stringType("version", "v1");
                        })
                ).build())
                .toPact();
    }

    @Pact(consumer = "FetchConsumerApplication", provider = "ProductService")
    RequestResponsePact noProductsExist(PactDslWithProvider builder) {
        return builder.given("no products exist")
                .uponReceiving("get all products")
                .method("GET")
                .path("/products")
                .willRespondWith()
                .status(200)
                .headers(headers())
                .body("[]")
                .toPact();
    }

    @Pact(consumer = "FetchConsumerApplication", provider = "ProductService")
    RequestResponsePact getOneProduct(PactDslWithProvider builder) {
        return builder.given("product with ID 10 exists")
                .uponReceiving("get product with ID 10")
                .method("GET")
                .path("/products/10")
                .willRespondWith()
                .status(200)
                .headers(headers())
                .body(newJsonBody(object -> {
                    object.stringType("id", "10");
                    object.stringType("type", "CREDIT_CARD");
                    object.stringType("name", "28 Degrees");
                    object.stringType("version", "v1");
                }).build())
                .toPact();
    }

    @Pact(consumer = "FetchConsumerApplication", provider = "ProductService")
    RequestResponsePact productDoesNotExist(PactDslWithProvider builder) {
        return builder.given("product with ID 11 does not exist")
                .uponReceiving("get product with ID 11")
                .method("GET")
                .path("/products/11")
                .willRespondWith()
                .status(500)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getAllProducts", pactVersion = PactSpecVersion.V3)
    void getAllProducts_whenProductsExist(MockServer mockServer) {
        Product product = new Product();
        product.setId("09");
        product.setType("CREDIT_CARD");
        product.setName("Gem Visa");
        product.setVersion("v1");
        List<Product> expected = Arrays.asList(product, product);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        List<Product> products = new ConsumerService(restTemplate).getAllProducts();

        assertEquals(expected, products);
    }

    @Test
    @PactTestFor(pactMethod = "noProductsExist", pactVersion = PactSpecVersion.V3)
    void getAllProducts_whenNoProductsExist(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        List<Product> products = new ConsumerService(restTemplate).getAllProducts();

        assertEquals(Collections.emptyList(), products);
    }

    @Test
    @PactTestFor(pactMethod = "getOneProduct", pactVersion = PactSpecVersion.V3)
    void getProductById_whenProductWithId10Exists(MockServer mockServer) {
        Product expected = new Product();
        expected.setId("10");
        expected.setType("CREDIT_CARD");
        expected.setName("28 Degrees");
        expected.setVersion("v1");

        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();
        Product product = new ConsumerService(restTemplate).getProduct("10");

        assertEquals(expected, product);
    }

    @Test
    @PactTestFor(pactMethod = "productDoesNotExist", pactVersion = PactSpecVersion.V3)
    void getProductById_whenProductWithId11DoesNotExist(MockServer mockServer) {
        RestTemplate restTemplate = new RestTemplateBuilder()
                .rootUri(mockServer.getUrl())
                .build();

        HttpServerErrorException e = assertThrows(HttpServerErrorException.class,
                () -> new ConsumerService(restTemplate).getProduct("11"));
        assertEquals(500, e.getStatusCode().value());

        /*Product product = new ConsumerService(restTemplate).getProduct("10");

        assertNull(product.getId());*/
    }

    private Map<String, String> headers() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        return headers;
    }
}
