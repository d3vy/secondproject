package com.devy.client.controllers;

import com.devy.client.RestServer.ProductsRestServer;
import com.devy.client.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/products/{productId:\\d+}")
public class ProductController {

    private final ProductsRestServer productsRestServer;


    @ModelAttribute("product")
    public Product product(@PathVariable("productId") Integer productId) {
        return productsRestServer.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    @GetMapping
    public String getProduct() {
        return "shop/products/product";
    }


}
