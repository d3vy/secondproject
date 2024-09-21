package com.devy.feedback.services;

import com.devy.feedback.models.ProductReview;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductReviewsService {

    Mono<ProductReview> createProductReview(Integer productId, Integer rating, String review, String userId);

    Flux<ProductReview> findAllProductReviewsForProduct(Integer productId);

}
