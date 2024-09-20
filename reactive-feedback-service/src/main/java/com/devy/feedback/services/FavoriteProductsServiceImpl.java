package com.devy.feedback.services;

import com.devy.feedback.models.FavoriteProduct;
import com.devy.feedback.repositories.FavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteProductsServiceImpl implements FavoriteProductsService {

    private final FavoriteProductRepository favoriteProductRepository;

    @Override
    public Mono<FavoriteProduct> addProductToFavorites(Integer productId) {
        log.info("The product was added to favorites");
        return this.favoriteProductRepository
                .save(new FavoriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(Integer productId) {
        log.info("The product was removed from favorites");
        return this.favoriteProductRepository.deleteByProductId(productId);
    }

    @Override
    public Mono<FavoriteProduct> findFavoriteProduct(Integer productId) {
        return this.favoriteProductRepository.findById(productId);
    }

    @Override
    public Flux<FavoriteProduct> findAllFavoriteProducts() {
        return this.favoriteProductRepository.findAll();
    }
}
