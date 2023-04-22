package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.PaymentMethod;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.PaymentMethodService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final UtilityService utilityService;

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse addPaymentMethodToOrder(String userName, PaymentMethodRequest request) {
        Optional<Order> optOrder = utilityService.getOrderByUserNameAndStatus(userName, OrderStatus.CHECKOUT);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            Customer customer = order.getCustomer();

            Optional<PaymentMethod> optPayment = customer.getPaymentMethods().stream()
                    .filter(e -> e.getPaymentOption().equals(request.getPaymentOption())).findFirst();
            if (optPayment.isPresent()) {
                order.setPaymentMethod(optPayment.get());
                return orderMapper.orderToOrderResponse(orderRepository.save(order));
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Payment method not available for customer");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "order not found for current user");
        }
    }
}
