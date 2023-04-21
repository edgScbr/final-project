package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.models.Order;

public interface RemoveItemService {

   void removeItem(String userName, long itemId);
}
