package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private Integer streetNumber;
    private String streetName;
    private String city;
    private String state;
    private Integer zip;
}
