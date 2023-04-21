package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductItemResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
