package com.devy.customer.repositories;

import com.devy.customer.model.FavoriteProduct;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Repository
public class FavoriteProductRepositoryImpl implements FavoriteProductRepository {

    private final List<FavoriteProduct> favoriteProductList =
            Collections.synchronizedList(new LinkedList<>());

    @Override
    public Mono<FavoriteProduct> save(FavoriteProduct favoriteProduct) {
        this.favoriteProductList.add(favoriteProduct);
        return Mono.just(favoriteProduct);
    }

    @Override
    public Mono<Void> deleteByProductId(Integer productId) {
        this.favoriteProductList.removeIf(favoriteProduct -> favoriteProduct.getProductId() == productId);
        return Mono.empty();
    }

    @Override
    public Mono<FavoriteProduct> findById(Integer productId) {
        return Flux.fromIterable(this.favoriteProductList)
                .filter(favoriteProduct -> favoriteProduct.getProductId() == productId)
                .singleOrEmpty();
    }

    @Override
    public Flux<FavoriteProduct> findAll() {
        return Flux.fromIterable(this.favoriteProductList);
    }
}
