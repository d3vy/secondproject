package com.devy.customer.controller;

import com.devy.customer.client.ProductsClient;
import com.devy.customer.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/customer/products/{productId:\\d+}")
public class ProductController {

    private final ProductsClient productsClient;

    @ModelAttribute(name = "product", binding = false)
    public Mono<Product> loadProduct(@PathVariable("productId") Integer productId) {
        return this.productsClient.findProduct(productId);
    }

    @GetMapping
    public String getProductPage(@ModelAttribute("product") Product product) {
        return "shop/customer/products/product";
    }
}
