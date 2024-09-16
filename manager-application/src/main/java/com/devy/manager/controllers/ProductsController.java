package com.devy.manager.controllers;

import com.devy.manager.RestServer.BadRequestException;
import com.devy.manager.RestServer.ProductsRestServer;
import com.devy.manager.controllers.payload.NewProductPayload;
import com.devy.manager.models.Product;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("shop/products")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsRestServer productsRestServer;

    @GetMapping("catalogue")
    public String getProductsCatalogue(Model model,
                                       @RequestParam(name="filter", required = false) String filter) {
        model.addAttribute("products", productsRestServer.finaAllProducts(filter));
        return "shop/products/catalogue";
    }

    @GetMapping("create")
    public String getCreateProductPage() {
        return "shop/products/create";
    }

    @PostMapping("create")
    public String createProduct(NewProductPayload payload,
                                Model model,
                                HttpServletResponse response) {

        try {
            Product product = this.productsRestServer.createProduct(payload.title(), payload.details());
            return "redirect:/shop/products/%d".formatted(product.id());
        } catch (BadRequestException exception) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            model.addAttribute("payload", payload);
            return "catalogue/products/create";
        }


    }


}
