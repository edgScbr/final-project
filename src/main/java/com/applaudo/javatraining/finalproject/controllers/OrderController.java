package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.services.interfaces.AddItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.CreateOrderService;
import com.applaudo.javatraining.finalproject.services.interfaces.RemoveItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("store/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final CreateOrderService createOrderService;

    private final AddItemService addItemService;

    private final RemoveItemService removeItemService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request, Principal principal ) {
        return createOrderService.createOrder(principal.getName(), request);
    }

    @GetMapping("{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {
        return createOrderService.getOrderById(id);
    }

    @PostMapping("/addProduct")
    public OrderResponse addItem(@RequestBody @Valid OrderRequest request, Principal principal) {
        return addItemService.addItems(principal.getName(), request);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long productId, Principal principal) {
        removeItemService.removeItem(principal.getName(), productId);
    }
}
