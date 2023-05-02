package com.applaudo.javatraining.finalproject.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter

@Table(name = "items")
@Entity

public class Item implements Serializable {

    @EmbeddedId
    private ItemKey id = new ItemKey();
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    @Getter
    private Product product;
    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;
    @Getter
    private Integer quantity;
}
