package com.applaudo.javatraining.finalproject.controllers.requests;

import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import lombok.Getter;

@Getter
public class PaymentMethodRequest {

    PaymentOption paymentOption;
}
