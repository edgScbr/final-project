package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.ItemRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.repositories.ProductRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.RemoveItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RemoveItemServiceImpl implements RemoveItemService {


    private final UtilityService utilityService;

    private final OrderRepository orderRepository;

    private final ItemRepository itemRepository;


    @Override
    public void removeItem(String userName, long productId) {
        Optional<Order> optOrder = utilityService.getOrderByUserNameAndStatus(userName, OrderStatus.CHECKOUT);
        if (optOrder.isPresent()) {
            Order order = optOrder.get();
            Optional<Item> optItem = utilityService.verifyItemAlreadyAdded(productId, order);
            if (optItem.isPresent()) {
                Item item = optItem.get();
                itemRepository.delete(optItem.get());
                order.getItems().remove(item);
                if (order.getItems().isEmpty()) {
                    order.setStatus(OrderStatus.CANCELED);
                    orderRepository.save(order);
                }
            } else {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "item not found");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "checkout not found for current user");
        }
    }

}
