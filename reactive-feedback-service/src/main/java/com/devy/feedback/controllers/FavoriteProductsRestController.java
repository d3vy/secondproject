package com.devy.feedback.controllers;

import com.devy.feedback.controllers.payload.NewFavoriteProductPayload;
import com.devy.feedback.models.FavoriteProduct;
import com.devy.feedback.services.FavoriteProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("feedback-api/favorite-products")
public class FavoriteProductsRestController {

    private final FavoriteProductsService favoriteProductsService;

    @GetMapping
    public Flux<FavoriteProduct> findFavoriteProducts() {
        return this.favoriteProductsService.findAllFavoriteProducts();
    }

    @GetMapping("by-product-id/{productId:\\d+}")
    public Mono<FavoriteProduct> findFavoriteProductByProductId(@PathVariable Integer productId) {
        return this.favoriteProductsService.findFavoriteProduct(productId);
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteProduct>> addProductToFavorites(
            @Valid @RequestBody Mono<NewFavoriteProductPayload> payloadMono,
            UriComponentsBuilder uriBuilder) {
        return payloadMono
                .flatMap(payload ->
                        this.favoriteProductsService.addProductToFavorites(payload.productId()))
                .map(favoriteProduct ->
                        ResponseEntity
                                .created(uriBuilder
                                        .replacePath("feedback-api/favorite-products/{id}")
                                        .build(favoriteProduct.getId()))
                                .body(favoriteProduct));
    }

    @DeleteMapping("by-product-id/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavorites(@PathVariable Integer productId) {
        return this.favoriteProductsService.removeProductFromFavorites(productId)
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
