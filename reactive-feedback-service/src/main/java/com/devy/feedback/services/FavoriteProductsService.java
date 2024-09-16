package com.devy.feedback.services;

import com.devy.feedback.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<FavoriteProduct> addProductToFavorites(Integer productId);

    Mono<Void> removeProductFromFavorites(Integer productId);

    Mono<FavoriteProduct> findFavoriteProduct(Integer productId);

    Flux<FavoriteProduct> findAllFavoriteProducts();
}
