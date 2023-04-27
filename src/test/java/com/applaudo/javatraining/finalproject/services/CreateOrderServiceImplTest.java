package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.TestUtilities;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.ItemRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.CreateOrderService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class CreateOrderServiceImplTest extends TestUtilities {


    private final OrderRepository orderRepository = mock(OrderRepository.class);
    private final UtilityService utilityService = mock(UtilityService.class);
    private final ItemRepository itemRepository = mock(ItemRepository.class);
    private final OrderMapper orderMapper = mock(OrderMapper.class);

    CreateOrderService createOrderService;

    @BeforeEach
    void init() {
        createOrderService = new CreateOrderServiceImpl(
                this.orderRepository,
                this.utilityService,
                this.itemRepository,
                this.orderMapper);
    }

    @Test
    public void givenOrderRequest_whenCreateOrder_thenCreateNewOrder() throws Exception {
        given(utilityService.findCustomer(anyString())).willReturn(customerModel);
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.empty());
        given(utilityService.findProduct(any())).willReturn(productModel);
        given(orderRepository.save(any())).willReturn(orderModel);
        given(itemRepository.save(any())).willReturn(itemModel);
        given(orderMapper.orderToOrderResponse(any())).willReturn(orderResponse);

        OrderResponse response = createOrderService.createOrder(customerModel.getUserName(), orderRequest);

        assertThat(response.getCustomer().getId()).isSameAs(customerModel.getId());
        assertThat(response.getCustomer().getFirstName()).isSameAs(customerModel.getFirstName());
        assertThat(response.getCustomer().getLastName()).isSameAs(customerModel.getLastName());
        assertThat(response.getStatus()).isSameAs(OrderStatus.CHECKOUT);
        assertThat(response.getItems()).hasSize(1);
        assertThat(response).isNotNull();
    }

    @Test
    public void givenExistingCheckout_whenCreateOrder_thenThrowException() throws Exception {
        given(utilityService.findCustomer(anyString())).willReturn(customerModel);
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    OrderResponse response = createOrderService.createOrder(customerModel.getUserName(), orderRequest);
                });
        Mockito.verify(orderRepository, never()).save(any(Order.class));
    }
}
