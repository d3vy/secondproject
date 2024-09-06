package com.devy.restserver.controllers;

import com.devy.restserver.controllers.payload.NewProductPayload;
import com.devy.restserver.models.Product;
import com.devy.restserver.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

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

    @PostMapping
    public ResponseEntity<?> crateProduct(@RequestBody NewProductPayload payload,
                                          BindingResult bindingResult,
                                          UriComponentsBuilder uriBuilder)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                log.error("1)Ошибка при создании продукта");
                throw exception;
            } else {
                log.error("2)Ошибка при создании продукта");
                throw new BindException(bindingResult);
            }
        } else {
            Product product = this.productService.createProduct(payload.title(), payload.details());
            return ResponseEntity
                    .created(uriBuilder
                            .replacePath("/shop-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }
    }
}