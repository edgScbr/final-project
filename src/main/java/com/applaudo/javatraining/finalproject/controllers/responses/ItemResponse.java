package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemResponse {

    private ProductItemResponse product;
    private Integer quantity;
}
