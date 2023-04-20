package com.applaudo.javatraining.finalproject.models;

import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@Table(name = "payment_methods")
@Entity
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    private PaymentOption paymentOption;

}
