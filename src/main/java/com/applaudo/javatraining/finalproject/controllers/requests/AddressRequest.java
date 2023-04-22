package com.applaudo.javatraining.finalproject.controllers.requests;

import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class AddressRequest {

    @Positive(message = "address id must be a positive integer")
    private Long addressId;
}
