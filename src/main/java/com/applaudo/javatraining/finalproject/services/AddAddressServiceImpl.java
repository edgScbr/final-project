package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Address;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.AddressRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.AddAddressService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AddAddressServiceImpl implements AddAddressService {

    private final UtilityService utilityService;

    private final OrderMapper orderMapper;

    private final OrderRepository orderRepository;

    @Override
    public OrderResponse addAddressToOrder(String userName, AddressRequest request) {
        Optional<Order> optOrder = utilityService.getOrderByUserNameAndStatus(userName, OrderStatus.CHECKOUT);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            Customer customer = order.getCustomer();

            Optional<Address> optAddress =
                    customer.getAddresses().stream()
                    .filter(e -> Objects.equals(e.getId(), request.getAddressId())).findFirst();
            if (optAddress.isPresent()) {
                order.setDeliveryAddress(optAddress.get());
                order.setStatus(OrderStatus.ORDERED);
                return orderMapper.orderToOrderResponse(orderRepository.save(order));
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Address does not belong to user");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "checkout not found for current user");
        }
    }
}
