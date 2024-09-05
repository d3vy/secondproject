package com.devy.client.config;

import com.devy.client.RestServer.ProductsRestServerImpl;
import com.devy.client.security.OAuthClientHttpRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;

@Configuration
public class ClientBeans {

    @Bean
    public ProductsRestServerImpl productsRestServerImpl(
            @Value("${shop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUri,
            @Value("${shop.services.catalogue.registration-id:keycloak}") String registrationId,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository oauth2AuthorizedClientRepository
    ) {
        return new ProductsRestServerImpl(RestClient.builder()
                .baseUrl(catalogueBaseUri)
                .requestInterceptor(
                        new OAuthClientHttpRequestInterceptor(
                                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository,
                                        oauth2AuthorizedClientRepository), registrationId))
                .build());
    }
}
