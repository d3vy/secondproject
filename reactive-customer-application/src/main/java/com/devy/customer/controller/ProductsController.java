package com.devy.customer.controller;

import com.devy.customer.client.FavoriteProductsClient;
import com.devy.customer.client.ProductsClient;
import com.devy.customer.models.FavoriteProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/customer/products")
public class ProductsController {

    private final ProductsClient productsClient;
    private final FavoriteProductsClient favoriteProductsClient;

    @GetMapping("catalogue")
    public Mono<String> getProductsCataloguePage(Model model,
                                                 @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.productsClient.findAllProducts(filter)
                .collectList()
                .doOnNext(products -> model.addAttribute("products", products))
                .thenReturn("shop/customer/products/catalogue");
    }

    @GetMapping("favorites")
    public Mono<String> getFavoriteProductsPage(Model model,
                                                @RequestParam(name = "filter", required = false) String filter) {
        model.addAttribute("filter", filter);
        return this.favoriteProductsClient
                .findAllFavoriteProducts()
                .map(FavoriteProduct::productId)
                .collectList()
                .flatMap(favoriteProducts -> this.productsClient.findAllProducts(filter)
                        .filter(product -> favoriteProducts.contains(product.id()))
                        .collectList()
                        .doOnNext(products -> model.addAttribute("products", products))
                        .thenReturn("shop/customer/products/favorites"));
    }
}
