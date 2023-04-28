package com.applaudo.javatraining.finalproject.models;

import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@Table(name = "payment_methods")
@Entity
@NoArgsConstructor
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PaymentOption paymentOption;

}
