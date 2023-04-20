package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
