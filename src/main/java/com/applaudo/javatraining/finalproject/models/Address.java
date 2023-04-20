package com.applaudo.javatraining.finalproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Table(name = "addresses")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel userModel;
    private int streetNumber;
    private String streetName;
    private String city;
    private String state;
    private int zip;

}
