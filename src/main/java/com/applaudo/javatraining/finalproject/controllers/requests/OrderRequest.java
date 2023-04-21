package com.applaudo.javatraining.finalproject.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class OrderRequest {

    @NotNull(message = "product id cannot be null")
    @Positive(message = "product id must be a positive number")
    private Long productId;

    @NotNull(message = "quantity cannot be null")
    @Positive(message = "quantity must be a positive number")
    private Integer quantity;
}
