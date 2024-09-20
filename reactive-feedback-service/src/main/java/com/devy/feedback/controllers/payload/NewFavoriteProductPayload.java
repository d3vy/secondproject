package com.devy.feedback.controllers.payload;

import jakarta.validation.constraints.NotNull;

public record NewFavoriteProductPayload(
        @NotNull
        Integer productId
) {
}
