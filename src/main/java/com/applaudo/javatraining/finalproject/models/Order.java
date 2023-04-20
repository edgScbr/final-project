package com.applaudo.javatraining.finalproject.models;

import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address deliveryAddress;
    private OrderStatus status;
    private DeliveryStatus deliveryStatus;
    @OneToMany(mappedBy = "order")
    private Set<Item> items;
}
