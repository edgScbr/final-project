package com.applaudo.javatraining.finalproject.controllers.requests;

import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentMethodRequest {

    PaymentOption paymentOption;
}
