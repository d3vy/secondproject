package com.devy.customer.controller;

import com.devy.customer.client.FavoriteProductsClient;
import com.devy.customer.client.ProductReviewsClient;
import com.devy.customer.client.ProductsClient;
import com.devy.customer.client.exception.ClientBadRequestException;
import com.devy.customer.controller.payload.NewProductReviewPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.reactive.result.view.CsrfRequestDataValueProcessor;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/customer/products/{productId:\\d+}")
@Slf4j
public class ProductController {

    private final ProductsClient productsClient;
    private final FavoriteProductsClient favoriteProductsClient;
    private final ProductReviewsClient productReviewsClient;

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId,
                                       Model model) {
        model.addAttribute("inFavorite", false);
        return this.productReviewsClient.findProductReviewsByProductId(productId)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(this.favoriteProductsClient.findFavoriteProduct(productId)
                        .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true)))
                .then(this.productsClient.findProduct(productId)
                        .doOnNext(product -> model.addAttribute("product", product))
                        .thenReturn("/shop/customer/products/product"));
    }

    @PostMapping("add-to-favorites")
    public Mono<String> addProductToFavorites(@PathVariable("productId") Integer productId) {
        return this.favoriteProductsClient
                .addProductToFavorites(productId)
                .thenReturn("redirect:/shop/customer/products/%d".formatted(productId))
                .onErrorResume(ex -> {
                    log.error("Failed to add product to favorites", ex);
                    return Mono.just("redirect:/shop/customer/products/%d".formatted(productId));
                });
    }

    @PostMapping("remove-from-favorites")
    public Mono<String> removeProductFromFavorites(@PathVariable("productId") Integer productId) {
        return this.favoriteProductsClient.removeProductFromFavorites(productId)
                .thenReturn("redirect:/shop/customer/products/%d".formatted(productId));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("productId") Integer productId,
                                     NewProductReviewPayload payload,
                                     Model model) {
        return this.productReviewsClient
                .createProductReview(productId, payload.rating(), payload.review())
                .thenReturn("redirect:/shop/customer/products/%d".formatted(productId))
                .onErrorResume(ClientBadRequestException.class, exception -> {
                    model.addAttribute("inFavorite", false);
                    model.addAttribute("payload", payload);
                    model.addAttribute("errors", exception.getErrors());
                    return this.favoriteProductsClient
                            .findFavoriteProduct(productId)
                            .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true))
                            .thenReturn("shop/customer/products/product");
                });
    }

    @ModelAttribute
    public Mono<CsrfToken> loadCsrfToken(ServerWebExchange exchange) {
        return Objects.requireNonNull(exchange.<Mono<CsrfToken>>getAttribute(CsrfToken.class.getName()))
                .doOnSuccess(token -> exchange.getAttributes()
                        .put(CsrfRequestDataValueProcessor.DEFAULT_CSRF_ATTR_NAME, token));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "errors/404";
    }

}
