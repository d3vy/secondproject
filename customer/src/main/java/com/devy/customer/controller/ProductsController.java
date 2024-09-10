package com.devy.customer.controller;

import com.devy.customer.client.ProductsClient;
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

    @GetMapping("catalogue")
    public Mono<String> getProductsCataloguePage(Model model,
                                                 @RequestParam(name = "filter", required = false) String filter) {
        return this.productsClient.findAllProducts(filter)
                .collectList()
                .doOnNext(products -> model.addAttribute("products", products))
                .thenReturn("shop/customer/products/catalogue");
    }
}
