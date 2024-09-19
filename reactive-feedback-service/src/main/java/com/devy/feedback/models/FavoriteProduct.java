package com.devy.feedback.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProduct {

    @Id
    private UUID id;

    private Integer productId;
}