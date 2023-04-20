package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
