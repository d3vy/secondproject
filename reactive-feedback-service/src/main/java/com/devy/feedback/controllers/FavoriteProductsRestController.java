package com.devy.feedback.controllers;

import com.devy.feedback.controllers.payload.NewFavoriteProductPayload;
import com.devy.feedback.models.FavoriteProduct;
import com.devy.feedback.services.FavoriteProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
    public Flux<FavoriteProduct> findFavoriteProducts(Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono.flatMapMany(token ->
                this.favoriteProductsService.findAllFavoriteProducts(token.getToken().getSubject()));
    }

    @GetMapping("by-product-id/{productId:\\d+}")
    public Mono<FavoriteProduct> findFavoriteProductByProductId(
            @PathVariable Integer productId,
            Mono<JwtAuthenticationToken> authenticationTokenMono
    ) {
        return authenticationTokenMono.flatMap(token ->
                this.favoriteProductsService.findFavoriteProduct(productId, token.getToken().getSubject()));
    }

    @PostMapping
    public Mono<ResponseEntity<FavoriteProduct>> addProductToFavorites(
            @RequestBody Mono<NewFavoriteProductPayload> payloadMono,
            UriComponentsBuilder uriBuilder,
            Mono<JwtAuthenticationToken> authenticationTokenMono) {
        // Zip метод нужен для объединения двух mono, после чего мы получаем класс tuple, с которым работаем.
        // В этом случае мы работаем с двумя mono одновременно.
        return Mono.zip(authenticationTokenMono, payloadMono)
                .flatMap(tuple -> this.favoriteProductsService
                        .addProductToFavorites(tuple.getT2().productId(), tuple.getT1().getToken().getSubject()))
                .map(favoriteProduct -> ResponseEntity
                        .created(uriBuilder
                                .replacePath("feedback-api/favorite-products/{id}")
                                .build(favoriteProduct.getId()))
                        .body(favoriteProduct));
    }


    @DeleteMapping("by-product-id/{productId:\\d+}")
    public Mono<ResponseEntity<Void>> removeProductFromFavorites(
            @PathVariable Integer productId,
            Mono<JwtAuthenticationToken> authenticationTokenMono) {
        return authenticationTokenMono
                .flatMap(token -> this.favoriteProductsService
                        .removeProductFromFavorites(productId, token.getToken().getSubject()))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

}
