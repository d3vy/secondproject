package com.devy.customer.controller;

import com.devy.customer.client.ProductsClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/customer/products/{productId:\\d+}")
@Slf4j
public class ProductController {

    private final ProductsClient productsClient;

    @GetMapping
    public Mono<String> getProductPage(@PathVariable("productId") Integer productId,
                                 Model model) {
        return productsClient.findProduct(productId)
                .doOnNext(product -> model.addAttribute("product", product))
                .then(Mono.just("shop/customer/products/product"));
    }
}
