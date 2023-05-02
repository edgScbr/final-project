package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.UtilitiesTest;
import com.applaudo.javatraining.finalproject.controllers.errors.custom.BadRequestResponseStatusException;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.repositories.ProductRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.PurchaseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PurchaseServiceImplTest extends UtilitiesTest {


    PurchaseService purchaseService;
    OrderRepository orderRepository = mock(OrderRepository.class);
    OrderMapper orderMapper = mock(OrderMapper.class);
    ProductRepository productRepository = mock(ProductRepository.class);

    @BeforeEach
    void init() {
        purchaseService = new PurchaseServiceImpl(
                this.orderRepository,
                this.orderMapper,
                this.productRepository);
    }

    @Test
    public void givenUserName_whenProceedToPurchase_thenReturnUpdatedOrderResponse() throws Exception {
        given(orderRepository.findByCustomerUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));
        given(orderRepository.save(any())).willReturn(orderModel);
        given(orderMapper.orderToOrderResponse(any())).willReturn(orderResponse);

        OrderResponse response = orderResponse;
        response.setStatus(OrderStatus.PAID);
        response.setDeliveryStatus(DeliveryStatus.SHIPPED);

        response = purchaseService.proceedToPurchase(orderModel.getCustomer().getUserName());

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isSameAs(OrderStatus.PAID);
        assertThat(response.getDeliveryStatus()).isSameAs(DeliveryStatus.SHIPPED);
    }

    @Test
    public void givenOrderNotFound_whenProceedToPurchase_thenReturnException() throws Exception {
        given(orderRepository.findByCustomerUserNameAndStatus(anyString(), any())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    purchaseService.proceedToPurchase("user1");
                });
    }

    @Test
    public void givenNullPaymentMethodAndNullDeliveryAddress_whenProceedToPurchase_thenReturnException()
            throws Exception {
        given(orderRepository.findByCustomerUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));

        Order invalid = orderModel;
        invalid.setPaymentMethod(null);
        invalid.setDeliveryAddress(null);

        Assertions.assertThrows(BadRequestResponseStatusException.class,
                () -> {
                    purchaseService.proceedToPurchase(invalid.getCustomer().getUserName());
                });

    }
}
