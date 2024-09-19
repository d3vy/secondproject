package com.devy.customer.client;

import com.devy.customer.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsClient {

    Flux<FavoriteProduct> findAllFavoriteProducts();

    Mono<FavoriteProduct> findFavoriteProduct(Integer productId);

    Mono<FavoriteProduct> addProductToFavorites(Integer productId);

    Mono<Void> removeProductFromFavorites(Integer productId);
}
