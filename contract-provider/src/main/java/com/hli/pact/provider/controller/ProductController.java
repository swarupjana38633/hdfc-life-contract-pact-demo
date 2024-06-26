package com.hli.pact.provider.controller;

import com.hli.pact.provider.entity.Product;
import com.hli.pact.provider.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("products")
public class ProductController {

    private final ProductRepository productRepository;

    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.fetchAll();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") String id) {
        productRepository.delete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Void> saveProduct(@RequestParam Product product) {
        productRepository.save(product);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") String id) {
        Optional<Product> product = productRepository.getById(id);

        if (!product.isPresent()) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(product.get());
    }
}
