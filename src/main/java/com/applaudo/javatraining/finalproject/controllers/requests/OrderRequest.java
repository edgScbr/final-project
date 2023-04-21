package com.applaudo.javatraining.finalproject.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class OrderRequest {

    @NotNull
    @Positive
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;
}
