package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.UtilitiesTest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.PaymentMethodService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PaymentMethodServiceImplTest extends UtilitiesTest {

    PaymentMethodService paymentMethodService;
    UtilityService utilityService = mock(UtilityService.class);
    OrderMapper orderMapper = mock(OrderMapper.class);
    OrderRepository orderRepository = mock(OrderRepository.class);

    @BeforeEach
    void init() {
        paymentMethodService = new PaymentMethodServiceImpl(
                this.utilityService,
                this.orderMapper,
                this.orderRepository);
    }

    @Test
    public void givenPaymentMethodRequest_whenAddPaymentMethodToOrder_thenReturnOrderResponse() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));
        given(orderRepository.save(any())).willReturn(orderModel);
        given(orderMapper.orderToOrderResponse(any())).willReturn(orderResponse);

        OrderResponse response = paymentMethodService.
                addPaymentMethodToOrder(customerModel.getUserName(), paymentMethodRequest);

        assertThat(response).isNotNull();
        assertThat(response.getPaymentMethod()).isSameAs(paymentMethod);
    }

    @Test
    public void givenInvalidPaymentMethod_whenAddPaymentMethodToOrder_thenReturnException() throws Exception {
        Customer invalidPaymentMethod = customerModel;
        invalidPaymentMethod.setPaymentMethods(new HashSet<>());
        Order order = orderModel;
        order.setCustomer(invalidPaymentMethod);

        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(order));

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    OrderResponse response = paymentMethodService
                            .addPaymentMethodToOrder(invalidPaymentMethod.getUserName(), paymentMethodRequest);
                });
    }

    @Test
    public void givenNoCheckout_whenAddPaymentMethodToOrder_thenReturnException() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    OrderResponse response = paymentMethodService
                            .addPaymentMethodToOrder(customerModel.getUserName(), paymentMethodRequest);
                });
    }
}
