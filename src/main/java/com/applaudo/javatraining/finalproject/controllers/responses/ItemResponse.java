package com.applaudo.javatraining.finalproject.controllers.responses;

import com.applaudo.javatraining.finalproject.models.Product;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private ProductItemResponse product;
    private Integer quantity;
}
