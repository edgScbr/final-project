package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {
    private Long id;
    private Integer streetNumber;
    private String streetName;
    private String city;
    private String state;
    private Integer zip;
}
