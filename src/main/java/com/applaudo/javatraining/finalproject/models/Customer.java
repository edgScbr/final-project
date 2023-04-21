package com.applaudo.javatraining.finalproject.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@Table(name = "customers")
@Entity
@NoArgsConstructor
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String userName;
    private String email;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    private Set<Address> addresses;
    @ManyToMany
    @JoinTable(
            name = "customers_payment_methods",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "payment_method_id")
    )
    private Set<PaymentMethod> paymentMethods;


}
