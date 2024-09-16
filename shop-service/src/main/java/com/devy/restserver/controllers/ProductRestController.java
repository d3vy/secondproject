package com.devy.restserver.controllers;

import com.devy.restserver.controllers.payload.UpdateProductPayload;
import com.devy.restserver.models.Product;
import com.devy.restserver.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("shop-api/products/{productId:\\d+}")
public class ProductRestController {

    private final ProductService productService;

    @ModelAttribute("product")
    public Product getProduct(@PathVariable("productId") Integer productId) {
        return productService.findProduct(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    @GetMapping
    public Product findProduct(@ModelAttribute("product") Product product) {
        return product;
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") Integer productId) {
        this.productService.deleteProduct(productId);
        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateProduct(@PathVariable("productId") Integer productId,
                                              @RequestBody UpdateProductPayload payload,
                                              BindingResult bindingResult)
            throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException exception) {
                log.error("1) Binding error in updateProduct method");
                throw exception;
            } else {
                log.error("2) Binding error in updateProduct method");
                throw new BindException(bindingResult);
            }
        } else {
            this.productService.updateProduct(productId, payload.title(), payload.details());
            return ResponseEntity.noContent()
                    .build();
        }

    }


}
