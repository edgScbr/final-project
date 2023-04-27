package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.UtilitiesTest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.ItemRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.AddItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

public class AddItemServiceImplTest extends UtilitiesTest {

    AddItemService addItemService;
    ItemRepository itemRepository = mock(ItemRepository.class);
    UtilityService utilityService = mock(UtilityService.class);
    OrderMapper orderMapper = mock(OrderMapper.class);
    @BeforeEach
    void init() {
        addItemService = new AddItemServiceImpl(
                this.itemRepository,
                this.utilityService,
                this.orderMapper);
    }

    @Test
    public void givenOrderRequest_whenAddItem_thenReturnOrderResponse() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));
        given(utilityService.findProduct(any())).willReturn(productModel);
        given(utilityService.verifyItemAlreadyAdded(anyLong(), any())).willReturn(Optional.empty());
        given(itemRepository.save(any())).willReturn(itemModel);
        given(orderMapper.orderToOrderResponse(any())).willReturn(orderResponse);

        OrderResponse response = addItemService.addItems(orderModel.getCustomer().getUserName(), orderRequest);

        assertThat(response).isNotNull();
        assertThat(response.getCustomer().getFirstName()).isSameAs(customerModel.getFirstName());
        assertThat(response.getStatus()).isSameAs(OrderStatus.CHECKOUT);
        assertThat(response.getItems()).hasSize(1);
    }

    @Test
    public void givenItemAlreadyAdded_whenAddItem_thenUpdateQuantity() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));
        given(utilityService.findProduct(any())).willReturn(productModel);
        given(utilityService.verifyItemAlreadyAdded(anyLong(), any())).willReturn(Optional.of(itemModel));
        given(itemRepository.save(any())).willReturn(itemModel);
        given(orderMapper.orderToOrderResponse(any())).willReturn(orderResponse);

        OrderResponse response = addItemService.addItems(customerModel.getUserName(), orderRequest);

        assertThat(response).isNotNull();
        assertThat(response.getCustomer().getFirstName()).isSameAs(customerModel.getFirstName());
        assertThat(response.getStatus()).isSameAs(OrderStatus.CHECKOUT);
        assertThat(response.getItems()).hasSize(1);
    }

    @Test
    public void givenOrderWithNoCheckoutStatus_whenAddItem_thenThrowException() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    OrderResponse response = addItemService.addItems(customerModel.getUserName(), orderRequest);
                });
        Mockito.verify(itemRepository, never()).save(any(Item.class));
    }
}
