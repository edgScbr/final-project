package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findByCustomerUserNameAndStatus(String userName, OrderStatus status);
}
