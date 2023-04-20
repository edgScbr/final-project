package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
}
