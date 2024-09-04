package com.devy.client.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("shop/products")
@RequiredArgsConstructor
public class ProductsController {

    @GetMapping("catalogue.html")
    public String getProductsCatalogue(Model model){
        return "shop/products/catalogue.html";
    }

}
