package com.devy.customer.services;

import com.devy.customer.model.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductsService {

    Mono<FavoriteProduct> addProductToFavorites(Integer productId);

    Mono<Void> removeProductFromFavorites(Integer productId);

    Mono<FavoriteProduct> findFavoriteProduct(Integer productId);

    Flux<FavoriteProduct> findAllFavoriteProducts();
}
