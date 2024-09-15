package com.devy.customer.services;

import com.devy.customer.model.ProductReview;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ProductReviewsService {

    Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review);

    Flux<ProductReview> findAllProductReviewsForProduct(Integer productId);

}
