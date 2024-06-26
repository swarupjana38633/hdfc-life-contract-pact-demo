package com.hli.pact.consumer.service;

import com.hli.pact.consumer.entity.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumerService {

    private final RestTemplate restTemplate;

    public ConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> getAllProducts() {
        return restTemplate.exchange("/products",
                HttpMethod.GET,
                getRequestEntity(),
                new ParameterizedTypeReference<List<Product>>() {
                }).getBody();
    }

    public Product getProduct(String id) {
        Optional<Product> optional = restTemplate.exchange("/products/{id}",
                HttpMethod.GET,
                getRequestEntity(),
                new ParameterizedTypeReference<Optional<Product>>() {
                }, id).getBody();

        return optional.get();

    }

    private HttpEntity<String> getRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        return new HttpEntity<>(headers);
    }
}
