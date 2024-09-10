package com.devy.customer.client;

import com.devy.customer.model.Product;
import reactor.core.publisher.Flux;

public interface ProductsClient {

    Flux<Product> findAllProducts(String filter);

}
