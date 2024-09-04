package com.devy.client.RestServer;

import com.devy.client.models.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRestServer {

    List<Product> finaAllProducts();

}
