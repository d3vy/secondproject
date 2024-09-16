package com.devy.customer.controller.payload;

public record NewProductReviewPayload(
        Integer productId,
        Integer rating,
        String review
) {
}
