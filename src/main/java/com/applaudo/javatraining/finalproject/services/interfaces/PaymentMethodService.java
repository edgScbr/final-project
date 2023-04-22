package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;

public interface PaymentMethodService {

    OrderResponse addPaymentMethodToOrder(String userName, PaymentMethodRequest request);
}
