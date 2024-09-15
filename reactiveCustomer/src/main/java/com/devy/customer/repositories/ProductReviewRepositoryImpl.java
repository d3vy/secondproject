package com.devy.customer.repositories;

import com.devy.customer.model.ProductReview;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductReviewRepositoryImpl implements ProductReviewRepository {

    private final List<ProductReview> productReviews = Collections.synchronizedList(new LinkedList<>());


    @Override
    public Mono<ProductReview> save(ProductReview productReview) {
        this.productReviews.add(productReview);
        return Mono.just(productReview);
    }

    @Override
    public Flux<ProductReview> findAllByProductId(Integer productId) {
        return Flux.fromIterable(this.productReviews)
                .filter(productReview -> Objects.equals(productReview.getProductId(), productId));
    }
}
