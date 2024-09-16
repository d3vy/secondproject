package com.devy.customer.models;

import java.util.UUID;

public record FavoriteProduct(
        UUID id,
        Integer productId
) {
}
