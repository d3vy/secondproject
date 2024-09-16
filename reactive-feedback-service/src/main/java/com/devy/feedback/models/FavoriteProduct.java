package com.devy.feedback.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteProduct {

    private UUID id;

    private Integer productId;
}