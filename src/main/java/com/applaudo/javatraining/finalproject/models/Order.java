package com.applaudo.javatraining.finalproject.models;

import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "orders")
@Entity
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address deliveryAddress;
    private OrderStatus status;
    private DeliveryStatus deliveryStatus;
    @OneToMany(mappedBy = "order")
    private Set<Item> items = new HashSet<>();
    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethod paymentMethod;

    public void addToOrder(Item item){
        item.setOrder(this);
        this.items.add(item);
    }
}
