package com.devy.client.RestServer;

import com.devy.client.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class ProductsRestServerImpl implements ProductsRestServer {

    private final RestClient restClient;
    private static final ParameterizedTypeReference<List<Product>> PRODUCTS_TYPE_REFERENCE
            = new ParameterizedTypeReference<>() {
    };

    @Override
    public List<Product> finaAllProducts() {
        return this.restClient
                .get()
                .uri("/shop-api/products")
                .retrieve()
                .body(PRODUCTS_TYPE_REFERENCE);
    }
}
