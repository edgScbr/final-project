package com.applaudo.javatraining.finalproject.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@Table(name = "users")
@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany(mappedBy = "userModel", fetch = FetchType.EAGER)
    private Set<Address> addresses;

    //private Set<PaymentMethod> paymentMethods;


}
