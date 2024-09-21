package com.devy.feedback.repositories;

import com.devy.feedback.models.FavoriteProduct;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface FavoriteProductRepository extends ReactiveCrudRepository<FavoriteProduct, UUID> {

    Flux<FavoriteProduct> findAllByUserId(String userId);

    Mono<FavoriteProduct> findByProductIdAndUserId(Integer productId, String userId);

    Mono<Void> deleteByProductIdAndUserId(Integer productId, String userId);
}
