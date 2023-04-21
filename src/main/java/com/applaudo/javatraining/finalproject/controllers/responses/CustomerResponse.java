package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

    private Long id;
    private String FirstName;
    private String LastName;
    private String userName;
    private String email;
}
