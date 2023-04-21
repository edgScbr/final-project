package com.applaudo.javatraining.finalproject.services.interfaces;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;

import java.util.Optional;
import java.util.Set;

public interface UtilityService {

    public Product findProduct(OrderRequest request);

    public Customer findCustomer(String userName);

    public Order getOrderById(long id);

    public Optional<Order> getOrderByUserNameAndStatus(String userName, OrderStatus status);

    public Optional<Item> verifyItemAlreadyAdded(Long productId, Order order);

}
