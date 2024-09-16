package com.devy.manager.controllers;

import com.devy.manager.RestServer.BadRequestException;
import com.devy.manager.RestServer.ProductsRestServer;
import com.devy.manager.controllers.payload.UpdateProductPayload;
import com.devy.manager.models.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("shop/products/{productId:\\d+}")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
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

    @PostMapping("delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        this.productsRestServer.deleteProduct(product.id());
        return "redirect:/shop/products/catalogue";
    }

    @GetMapping("edit")
    public String getEditProductPage() {
        return "shop/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                UpdateProductPayload payload,
                                Model model,
                                HttpServletResponse response) {
        try {
            this.productsRestServer.updateProduct(product.id(), payload.title(), payload.details());
            return "redirect:/shop/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            log.error("ошибка при обновлении продукта в контроллере");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("payload", payload);
            return "catalogue/products/edit";
        }
    }

}
