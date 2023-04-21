package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.ItemRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.CreateOrderService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CreateOrderServiceImpl implements CreateOrderService {

    private final OrderRepository orderRepository;
    private final UtilityService utilityService;
    private final ItemRepository itemRepository;
    private final OrderMapper orderMapper;


    @Override
    @Transactional
    public OrderResponse createOrder(String userName, OrderRequest request) {
        Customer customer = utilityService.findCustomer(userName);
        Optional<Order> optOrder = utilityService.getOrderByUserNameAndStatus(
                customer.getUserName(), OrderStatus.CHECKOUT);
        if (optOrder.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.UNPROCESSABLE_ENTITY, "checkout already exists for user: " + userName);
        } else {

            Product product = utilityService.findProduct(request);

            //Order
            Order order = new Order();
            order.setCustomer(customer);
            order.setStatus(OrderStatus.CHECKOUT);
            order.setDeliveryStatus(DeliveryStatus.PENDING);
            Order savedOrder = orderRepository.save(order);

            //Item
            Item item = new Item();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(request.getQuantity());
            savedOrder.getItems().add(itemRepository.save(item));

            return orderMapper.orderToOrderResponse(savedOrder);
        }
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.map(orderMapper::orderToOrderResponse).orElse(null);

    }


}
