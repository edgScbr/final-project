package com.applaudo.javatraining.finalproject.controllers.requests;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    @Positive(message = "address id must be a positive integer")
    private Long addressId;
}
