package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;

import java.util.List;

public interface AddItemService {

    OrderResponse addItems(String userName, OrderRequest request);
}
