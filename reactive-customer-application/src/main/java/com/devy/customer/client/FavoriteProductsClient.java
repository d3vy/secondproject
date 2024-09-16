package com.devy.customer.client;

import com.devy.customer.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsClient {

    Mono<FavoriteProduct> findFavoriteProduct(Integer productId);

    Flux<FavoriteProduct> findAllFavoriteProducts();

    Mono<FavoriteProduct> addProductToFavorites(Integer productId);

    Mono<Void> removeProductFromFavorites(Integer productId);
}
