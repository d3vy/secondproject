package com.devy.feedback.controllers.payload;

import jakarta.validation.constraints.NotNull;

public record NewFavoriteProductPayload(
        @NotNull(message = "{feedback.products.favorites.create.errors.product_id_is_null}")
        Integer productId
) {
}
