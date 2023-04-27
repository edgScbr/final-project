package com.applaudo.javatraining.finalproject.controllers.responses;

import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.models.Address;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Item;
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
    private OrderStatus status;
    private DeliveryStatus deliveryStatus;
    private Set<ItemResponse> items;
    private PaymentMethod paymentMethod;
    private Double total;

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
