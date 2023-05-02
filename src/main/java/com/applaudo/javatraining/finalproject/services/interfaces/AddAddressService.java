package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;

public interface AddAddressService {

    OrderResponse addAddressToOrder(String userName, AddressRequest request);

}
