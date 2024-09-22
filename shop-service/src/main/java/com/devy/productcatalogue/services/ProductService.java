package com.devy.productcatalogue.services;

import com.devy.productcatalogue.models.Product;
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
