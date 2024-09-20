package com.devy.feedback.controllers;

import com.devy.feedback.controllers.payload.NewProductReviewPayload;
import com.devy.feedback.models.ProductReview;
import com.devy.feedback.services.ProductReviewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("feedback-api/product-reviews")
@RequiredArgsConstructor
public class ProductReviewsRestController {

    //Можно искать товары и таким образом, напрямую обращаясь к mongodb.
//    private final ReactiveMongoTemplate reactiveMongoTemplate;
//
//    @GetMapping("by-product-id/{productId:\\d+}")
//    public Flux<ProductReview> findProductReviewsByProductId(@PathVariable("productId") Integer productId) {
//       return this.reactiveMongoTemplate
//               .find(Query.query(Criteria.where("productId").is(productId)) , ProductReview.class);
//    }

    private final ProductReviewsService productReviewsService;

    @GetMapping("by-product-id/{productId:\\d+}")
    public Flux<ProductReview> findProductReviewsByProductId(@PathVariable("productId") Integer productId) {
        return this.productReviewsService.findAllProductReviewsForProduct(productId);
    }

    //Создание отзыва о товаре.
    @PostMapping
    public Mono<ResponseEntity<ProductReview>> createProductReview(
            @Valid @RequestBody Mono<NewProductReviewPayload> payloadMono,
            UriComponentsBuilder uriBuilder) {
        return payloadMono
                .flatMap(payload ->
                        this.productReviewsService
                                .createProductReview(
                                        payload.productId(),
                                        payload.rating(),
                                        payload.review()))
                .map(productReview ->
                        ResponseEntity.created(uriBuilder.replacePath("/ feedback-api/product-reviews/{id}")
                                        .build(productReview.getId()))
                                .body(productReview));
    }

}
