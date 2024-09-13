package com.devy.customer.client;

import com.devy.customer.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
public class ProductsClientImpl implements ProductsClient {

    private final WebClient webClient;

    @Override
    public Flux<Product> findAllProducts(String filter) {
        return this.webClient
                .get()
                .uri("/shop-api/products?filter={filter}", filter)
                .retrieve()
                .bodyToFlux(Product.class);
    }

    @Override
    public Mono<Product> findProduct(Integer id) {
        return this.webClient
                .get()
                .uri("/shop-api/products/{productId}", id)
                .retrieve()
                .bodyToMono(Product.class);

    }
}
