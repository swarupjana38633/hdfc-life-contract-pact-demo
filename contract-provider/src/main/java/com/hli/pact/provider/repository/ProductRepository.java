package com.hli.pact.provider.repository;

import com.hli.pact.provider.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductRepository {

    private final static Map<String, Product> PRODUCTS = new HashMap<>();

    static {
        PRODUCTS.put("09", new Product("09", "CREDIT_CARD", "Gem Visa", "v1"));
        PRODUCTS.put("10", new Product("10", "CREDIT_CARD", "28 Degrees", "v1"));
        PRODUCTS.put("11", new Product("11", "PERSONAL_LOAN", "MyFlexiPay", "v2"));
    }

    public List<Product> fetchAll() {
        return new ArrayList<>(PRODUCTS.values());
    }

    public Optional<Product> getById(String id) {
        return Optional.ofNullable(PRODUCTS.get(id));
    }

    public void delete(String id) {
        PRODUCTS.remove(id);
    }

    public void save(Product product) {
        PRODUCTS.put(product.getId(), product);
    }
}
