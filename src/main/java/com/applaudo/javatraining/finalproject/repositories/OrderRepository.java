package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
