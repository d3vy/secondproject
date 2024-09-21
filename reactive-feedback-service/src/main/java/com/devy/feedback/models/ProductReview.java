package com.devy.feedback.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product_review")
public class ProductReview {

    @Id
    private UUID id;

    private Integer productId;

    private Integer rating;

    private String review;

    private String userId;

}
