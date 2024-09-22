package com.devy.customer.config;

import com.devy.customer.client.FavoriteProductsClientImpl;
import com.devy.customer.client.ProductReviewsClientImpl;
import com.devy.customer.client.ProductsClientImpl;
import de.codecentric.boot.admin.client.config.ClientProperties;
import de.codecentric.boot.admin.client.registration.ReactiveRegistrationClient;
import de.codecentric.boot.admin.client.registration.RegistrationClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientBeans {


    // Scope("prototype") отвечает за то, что каждый раз, когда этот компонент будет внедряться в новый метод,
    //будет создаваться новый экземпляр.
    @Bean
    @Scope("prototype")
    public WebClient.Builder secondprojectServicesWebClientBuilder(
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ServerOAuth2AuthorizedClientRepository authorizedClientRepository
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrationRepository,
                        authorizedClientRepository
                );
        filter.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                .filter(filter);
    }

    @Bean
    public ProductsClientImpl productsClientImpl(
            @Value("${shop.services.catalogue.uri:http://localhost:8081}") String catalogueBaseUrl,
            WebClient.Builder secondprojectServicesWebClientBuilder
    ) {
        return new ProductsClientImpl(secondprojectServicesWebClientBuilder
                .baseUrl(catalogueBaseUrl)
                .build());
    }

    @Bean
    public FavoriteProductsClientImpl favoriteProductsClientImpl(
            @Value("${shop.services.feedback.uri:http://localhost:8085}") String feedbackBaseUrl,
            WebClient.Builder secondprojectServicesWebClientBuilder
    ) {
        return new FavoriteProductsClientImpl(secondprojectServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    public ProductReviewsClientImpl productReviewsClientImpl(
            @Value("${shop.services.feedback.uri:http://localhost:8085}") String feedbackBaseUrl,
            WebClient.Builder secondprojectServicesWebClientBuilder
    ) {
        return new ProductReviewsClientImpl(secondprojectServicesWebClientBuilder
                .baseUrl(feedbackBaseUrl)
                .build());
    }

    @Bean
    @ConditionalOnProperty(name = "spring.boot.admin.client.enabled", havingValue = "true")
    public RegistrationClient registrationClient(
            ClientProperties clientProperties,
            ReactiveClientRegistrationRepository clientRegistrationRepository,
            ReactiveOAuth2AuthorizedClientService authorizedClientService
    ) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction filter =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
                                clientRegistrationRepository,
                                authorizedClientService));
        filter.setDefaultClientRegistrationId("metrics");

        return new ReactiveRegistrationClient(WebClient.builder()
                .filter(filter)
                .build(), clientProperties.getReadTimeout());
    }
}
