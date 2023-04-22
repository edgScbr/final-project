package com.applaudo.javatraining.finalproject.mappers;

import com.applaudo.javatraining.finalproject.controllers.responses.PaymentMethodResponse;
import com.applaudo.javatraining.finalproject.models.PaymentMethod;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodResponse paymentMethodToPaymentMethodResponse(PaymentMethod paymentMethod);
}
