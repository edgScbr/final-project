package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.repositories.ProductRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public OrderResponse proceedToPurchase(String userName) {
        Optional<Order> optOrder = orderRepository.findByCustomerUserNameAndStatus(userName, OrderStatus.CHECKOUT);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            if (!(order.getPaymentMethod() == null) && !(order.getDeliveryAddress() == null)) {
                order.setStatus(OrderStatus.PAID);
                order.setDeliveryStatus(DeliveryStatus.SHIPPED);
                updateStock(order);
                return orderMapper.orderToOrderResponse(orderRepository.save(order));
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "payment method and delivery address must be set up for current order");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "checkout not found for current user");
        }
    }

    private void updateStock(Order order) {
        Set<Item> items = order.getItems();
        for (Item item : items) {
            Product product = item.getProduct();
            int currentStock = product.getStock();
            if (item.getQuantity() <= currentStock) {
                product.setStock(currentStock - item.getQuantity());
                productRepository.save(product);
            } else {
                throw new ResponseStatusException(
                        HttpStatus.UNPROCESSABLE_ENTITY, "insufficient stock for product " + item.getProduct().getName());
            }
        }
    }
}
