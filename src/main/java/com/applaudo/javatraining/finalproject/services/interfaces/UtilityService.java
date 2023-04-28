package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;

import java.util.Optional;

public interface UtilityService {

    Product findProduct(OrderRequest request);

    Customer findCustomer(String userName);

    Order getOrderById(long id);

    Optional<Order> getOrderByUserNameAndStatus(String userName, OrderStatus status);

    Optional<Item> verifyItemAlreadyAdded(Long productId, Order order);

}
