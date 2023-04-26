package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;

public interface CreateOrderService {

    OrderResponse createOrder(String userName, OrderRequest request);

    OrderResponse getOrderById(Long id, String userName);

}
