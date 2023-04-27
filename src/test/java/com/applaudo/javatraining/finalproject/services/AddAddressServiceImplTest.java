package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.UtilitiesTest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.AddAddressService;
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

public class AddAddressServiceImplTest extends UtilitiesTest {

    AddAddressService addAddressService;


    UtilityService utilityService = mock(UtilityService.class);
    OrderMapper orderMapper = mock(OrderMapper.class);
    OrderRepository orderRepository = mock(OrderRepository.class);

    @BeforeEach
    void init() {
        addAddressService = new AddAddressServiceImpl(
                this.utilityService,
                this.orderMapper,
                this.orderRepository);
    }

    @Test
    public void givenAddressRequest_whenAddAddress_thenReturnOrderResponse() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(orderModel));
        given(orderRepository.save(any())).willReturn(orderModel);
        given(orderMapper.orderToOrderResponse(any())).willReturn(orderResponse);

        OrderResponse response = addAddressService.addAddressToOrder(customerModel.getUserName(), addressRequest);

        assertThat(response).isNotNull();
        assertThat(response.getDeliveryAddress().getId()).isSameAs(addressResponse.getId());
        assertThat(response.getDeliveryAddress().getStreetNumber()).isSameAs(addressResponse.getStreetNumber());
        assertThat(response.getDeliveryAddress().getStreetName()).isSameAs(addressResponse.getStreetName());
        assertThat(response.getDeliveryAddress().getCity()).isSameAs(addressResponse.getCity());
        assertThat(response.getDeliveryAddress().getState()).isSameAs(addressResponse.getState());
        assertThat(response.getDeliveryAddress().getZip()).isSameAs(addressResponse.getZip());
    }

    @Test
    public void givenInvalidAddress_whenAddAddress_thenReturnException() throws Exception {
        Customer withNoAddress = customerModel;
        withNoAddress.setAddresses(new HashSet<>());
        Order order = orderModel;
        order.setCustomer(withNoAddress);

        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.of(order));

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    OrderResponse response = addAddressService
                            .addAddressToOrder(withNoAddress.getUserName(), addressRequest);
                });
    }

    @Test
    public void givenNoCheckout_whenAddAddress_thenReturnException() throws Exception {
        given(utilityService.getOrderByUserNameAndStatus(anyString(), any())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    OrderResponse response = addAddressService
                            .addAddressToOrder(customerModel.getUserName(), addressRequest);
                });
    }
}
