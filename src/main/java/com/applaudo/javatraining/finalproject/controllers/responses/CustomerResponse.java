package com.applaudo.javatraining.finalproject.controllers.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;
    private String FirstName;
    private String LastName;
    private String userName;
    private String email;
}
