package com.devy.client.RestServer;

import com.devy.client.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRestServer {

    List<Product> finaAllProducts(String filter);

    Product createProduct(String title, String details);

    Optional<Product> findProduct(Integer id);

    void deleteProduct(Integer id);

    void updateProduct(Integer id, String title, String details);

}
