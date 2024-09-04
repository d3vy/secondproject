package com.devy.restserver.controllers;

import com.devy.restserver.models.Product;
import com.devy.restserver.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("shop-api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductsRestController {

    private final ProductService productService;

    @GetMapping
    public Iterable<Product> findAllProducts() {
        return this.productService.findAllProducts();
    }

}
