package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;

public interface PurchaseService {

    OrderResponse proceedToPurchase(String userName);
}
