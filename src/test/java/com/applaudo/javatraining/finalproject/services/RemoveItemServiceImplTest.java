package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.UtilitiesTest;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.ItemRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.RemoveItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

public class RemoveItemServiceImplTest extends UtilitiesTest {

    RemoveItemService removeItemService;

    UtilityService utilityService = mock(UtilityService.class);
    OrderRepository orderRepository = mock(OrderRepository.class);
    ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeEach
    void init() {
        removeItemService = new RemoveItemServiceImpl(
                this.utilityService,
                this.orderRepository,
                this.itemRepository);
    }

    @Test
    public void givenProductId_whenRemoveItem_thenDeleteItem() throws Exception {
        //Arrange
        Order order = orderModel;
        order.setItems(new HashSet<>(Collections.singletonList(itemModel)));

        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(order));
        given(utilityService.verifyItemAlreadyAdded(anyLong(), any())).willReturn(Optional.of(itemModel));
        doNothing().when(itemRepository).delete(isA(Item.class));

        //Act
        removeItemService.removeItem(customerModel.getUserName(), productModel.getId());

        //Assert
        assertThat(order.getStatus()).isSameAs(OrderStatus.CANCELED);
        assertThat(order.getItems().size()).isEqualTo(0);
    }

    @Test
    public void givenCheckoutNotFound_whenRemoveItem_thenThrowException() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> {
                    removeItemService.removeItem(customerModel.getUserName(), productModel.getId());
                });
    }

    @Test
    public void givenItemNotFound_whenRemoveItem_thenThrowException() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));
        given(utilityService.verifyItemAlreadyAdded(anyLong(), any())).willReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> {
                    removeItemService.removeItem(customerModel.getUserName(), productModel.getId());
                });

    }
}
