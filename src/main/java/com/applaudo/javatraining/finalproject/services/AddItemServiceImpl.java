package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.mappers.OrderMapper;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.ItemRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.AddItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AddItemServiceImpl implements AddItemService {


    private final ItemRepository itemRepository;
    private final UtilityService utilityService;
    private final OrderMapper orderMapper;

    @Override
    public OrderResponse addItems(String userName, OrderRequest request) {
        Optional<Order> optOrder = utilityService.getOrderByUserNameAndStatus(userName, OrderStatus.CHECKOUT);

        if (optOrder.isPresent()) {
            Product product = utilityService.findProduct(request);
            Order order = optOrder.get();
            Item item = new Item();

            Optional<Item> optItem = utilityService.verifyItemAlreadyAdded(request.getProductId(), order);
            if (optItem.isPresent()) {
                item = optItem.get();
                item.setQuantity(request.getQuantity());
                itemRepository.save(item);

            } else {
                item.setOrder(order);
                item.setProduct(product);
                item.setQuantity(request.getQuantity());
                order.getItems().add(itemRepository.save(item));
            }

            return orderMapper.orderToOrderResponse(order);

        } else {
            throw new EntityNotFoundException(
                    "User does not have order with checkout status.");
        }
    }



}
