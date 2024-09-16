package com.devy.feedback.services;

import com.devy.feedback.models.FavoriteProduct;
import com.devy.feedback.repositories.FavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FavoriteProductsServiceImpl implements FavoriteProductsService {

    private final FavoriteProductRepository favoriteProductRepository;

    @Override
    public Mono<FavoriteProduct> addProductToFavorites(Integer productId) {
        return this.favoriteProductRepository
                .save(new FavoriteProduct(UUID.randomUUID(), productId));
    }

    @Override
    public Mono<Void> removeProductFromFavorites(Integer productId) {
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
