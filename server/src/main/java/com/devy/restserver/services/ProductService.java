package com.devy.restserver.services;

import com.devy.restserver.models.Product;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Iterable<Product> findAllProducts();

}
