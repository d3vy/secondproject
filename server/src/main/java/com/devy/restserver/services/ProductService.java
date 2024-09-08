package com.devy.restserver.services;

import com.devy.restserver.models.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ProductService {

    Iterable<Product> findAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(Integer id);

    void deleteProduct(Integer id);

    void updateProduct(Integer id, String title, String details);
}
