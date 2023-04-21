package com.applaudo.javatraining.finalproject.controllers.responses;

import com.applaudo.javatraining.finalproject.models.Product;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemResponse {

    private ProductItemResponse product;
    private Integer quantity;
}
