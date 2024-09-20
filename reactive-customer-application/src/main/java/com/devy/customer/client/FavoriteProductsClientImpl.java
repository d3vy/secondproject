package com.devy.customer.client;

import com.devy.customer.client.exception.ClientBadRequestException;
import com.devy.customer.client.payload.NewFavoriteProductPayload;
import com.devy.customer.models.FavoriteProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class FavoriteProductsClientImpl implements FavoriteProductsClient {

    private final WebClient webClient;

    @Override
    public Mono<FavoriteProduct> findFavoriteProduct(Integer productId) {
        return this.webClient
                .get()
                .uri("/feedback-api/favorite-products/by-product-id/{productId}", productId)
                .retrieve()
                .bodyToMono(FavoriteProduct.class)
                .onErrorComplete(WebClientResponseException.NotFound.class);
    }

    @Override
    public Flux<FavoriteProduct> findAllFavoriteProducts() {
        return this.webClient
                .get()
                .uri("/feedback-api/favorite-products")
                .retrieve()
                .bodyToFlux(FavoriteProduct.class);
    }

    @Override
    public Mono<FavoriteProduct> addProductToFavorites(Integer productId) {
        return this.webClient
                .post()
                .uri("/feedback-api/favorite-products")
                .bodyValue(new NewFavoriteProductPayload(productId))
                .retrieve()
                .bodyToMono(FavoriteProduct.class)
                .onErrorMap(WebClientResponseException.BadRequest.class,
                        exception -> new ClientBadRequestException(exception,
                                ((List<String>) exception.getResponseBodyAs(ProblemDetail.class)
                                        .getProperties().get("errors"))));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(Integer productId) {
        return this.webClient
                .delete()
                .uri("/feedback-api/favorite-products/by-product-id/{productId}", productId)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}
