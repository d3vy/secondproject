package com.devy.customer.controller;

import com.devy.customer.client.ProductsClient;
import com.devy.customer.controller.payload.NewProductReviewPayload;
import com.devy.customer.services.FavoriteProductsService;
import com.devy.customer.services.ProductReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/customer/products/{productId:\\d+}")
@Slf4j
public class ProductController {

    private final ProductsClient productsClient;
    private final FavoriteProductsService favoriteProductsService;
    private final ProductReviewsService productReviewsService;

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId,
                                       Model model) {
        model.addAttribute("inFavorite", false);
        return this.productReviewsService.findAllProductReviewsForProduct(productId)
                .collectList()
                .doOnNext(productReviews -> model.addAttribute("reviews", productReviews))
                .then(this.favoriteProductsService.findFavoriteProduct(productId))
                .doOnNext(favoriteProduct -> model.addAttribute("inFavorite", true))
                .then(this.productsClient.findProduct(productId)
                        .switchIfEmpty(Mono.error(new NoSuchElementException("Product not found"))))
                .doOnNext(product -> model.addAttribute("product", product))
                .then(Mono.just("shop/customer/products/product"));
    }

    @PostMapping("add-to-favorites")
    public Mono<String> addProductToFavorites(@PathVariable("productId") Integer productId) {
        return this.favoriteProductsService.addProductToFavorites(productId)
                .thenReturn("redirect:/shop/customer/products/%d".formatted(productId));
    }

    @PostMapping("remove-from-favorites")
    public Mono<String> removeProductFromFavorites(@PathVariable("productId") Integer productId) {
        return this.favoriteProductsService.removeProductFromFavorites(productId)
                .thenReturn("redirect:/shop/customer/products/%d".formatted(productId));
    }

    @PostMapping("create-review")
    public Mono<String> createReview(@PathVariable("productId") Integer productId,
                                     NewProductReviewPayload payload) {
        return this.productReviewsService
                .createProductReview(productId, payload.rating(), payload.review())
                .thenReturn("redirect:/shop/customer/products/%d".formatted(productId));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return "templates/errors/404";
    }

}
