package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductItemResponse {
    private Long id;
    private String name;
    private String description;
    private Double price;
}
