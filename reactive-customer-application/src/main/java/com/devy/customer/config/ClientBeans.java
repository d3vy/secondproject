package com.devy.customer.config;

import com.devy.customer.client.FavoriteProductsClientImpl;
import com.devy.customer.client.ProductReviewsClientImpl;
import com.devy.customer.client.ProductsClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsClientImpl productsClientImpl(
            @Value("${shop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl
    ) {
        return new ProductsClientImpl(WebClient.builder()
                .baseUrl(catalogueBaseUrl)
                .build());
    }

    @Bean
    public FavoriteProductsClientImpl favoriteProductsClientImpl(
            @Value("${shop.services.feedback.uri:http://localhost:8085}") String feedbackBaseUrl
    ) {
        return new FavoriteProductsClientImpl(WebClient.builder()
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public ProductReviewsClientImpl productReviewsClientImpl(
            @Value("${shop.services.feedback.uri:http://localhost:8085}") String feedbackBaseUrl
    ) {
        return new ProductReviewsClientImpl(WebClient.builder()
                .baseUrl(feedbackBaseUrl)
                .build());
    }
}
