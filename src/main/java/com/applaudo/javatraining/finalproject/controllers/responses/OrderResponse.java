package com.applaudo.javatraining.finalproject.controllers.responses;

import com.applaudo.javatraining.finalproject.models.PaymentMethod;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import lombok.*;

import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private CustomerResponse customer;
    private AddressResponse deliveryAddress;
    @Setter
    private OrderStatus status;
    @Setter
    private DeliveryStatus deliveryStatus;
    private Set<ItemResponse> items;
    private PaymentMethod paymentMethod;
    private Double total;
}
