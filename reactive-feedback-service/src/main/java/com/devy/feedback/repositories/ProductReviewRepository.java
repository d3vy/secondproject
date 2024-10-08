package com.devy.feedback.repositories;

import com.devy.feedback.models.ProductReview;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;
@Repository
public interface ProductReviewRepository extends ReactiveCrudRepository<ProductReview, UUID> {

    //Запрос к mongodb
    @Query("{'productId': ?0}")
    Flux<ProductReview> findAllByProductId(Integer productId);

}

