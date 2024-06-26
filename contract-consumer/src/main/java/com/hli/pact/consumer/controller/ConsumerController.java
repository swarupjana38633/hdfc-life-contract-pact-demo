package com.hli.pact.consumer.controller;

import com.hli.pact.consumer.service.ConsumerService;
import com.hli.pact.consumer.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/consume")
public class ConsumerController {

    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return consumerService.getAllProducts();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") String id) {

        Optional<Product> product = Optional.ofNullable(consumerService.getProduct(id));

        if (!product.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.of(product);
    }

}
