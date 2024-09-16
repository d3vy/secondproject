package com.devy.feedback.repositories;

import com.devy.feedback.models.FavoriteProduct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FavoriteProductRepository {

    Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct);

    Mono<Void> deleteByProductId(Integer productId);

    Mono<FavoriteProduct> findById(Integer productId);

    Flux<FavoriteProduct> findAll();
}
