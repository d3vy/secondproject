package com.devy.customer.controller.payload;

public record NewProductReviewPayload(
        Integer rating,
        String review
) {
}
