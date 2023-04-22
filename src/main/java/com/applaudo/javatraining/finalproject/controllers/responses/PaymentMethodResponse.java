package com.applaudo.javatraining.finalproject.controllers.responses;

import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import jakarta.persistence.*;

public class PaymentMethodResponse {

    private Long id;
    private PaymentOption paymentOption;
}
