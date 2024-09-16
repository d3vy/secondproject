package com.devy.restserver.controllers.payload;

public record UpdateProductPayload(
        String title,
        String details
) {
}
