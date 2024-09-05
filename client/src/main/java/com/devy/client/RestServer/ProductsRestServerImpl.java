package com.devy.client.RestServer;

import com.devy.client.controllers.payload.NewProductPayload;
import com.devy.client.models.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.authorization.client.util.Http;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Product createProduct(String title, String details) {
        try {
            return this.restClient
                    .post()
                    .uri("/shop-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductPayload(title, details))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest exception) {
            log.error("Ошибка при создании продукта");
            throw exception;
        }
    }

    @Override
    public Optional<Product> findProduct(Integer id) {
        try {
            return Optional.ofNullable(this.restClient
                    .get()
                    .uri("/shop-api/products/{id}", id)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound exception) {
            log.error("Продукт с id: {} не найден", id);
            return Optional.empty();
        }
    }
}
