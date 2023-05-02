package com.applaudo.javatraining.finalproject.repositories;

import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.ItemKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, ItemKey> {

}
