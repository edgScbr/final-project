package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.services.interfaces.*;
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

    private final AddAddressService addAddressService;

    private final PaymentMethodService paymentMethodService;

    private final PurchaseService purchaseService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request, Principal principal) {
        return createOrderService.createOrder(principal.getName(), request);
    }

    @GetMapping("{id}")
    public OrderResponse getOrderById(@PathVariable Long id, Principal principal) {
        return createOrderService.getOrderById(id, principal.getName());
    }

    @PatchMapping("addAddress")
    public OrderResponse addAddress(@RequestBody @Valid AddressRequest addressRequest, Principal principal) {
        return addAddressService.addAddressToOrder(principal.getName(), addressRequest);
    }

    @PatchMapping("addPaymentMethod")
    public OrderResponse addPaymentMethod(@RequestBody PaymentMethodRequest request, Principal principal) {
        return paymentMethodService.addPaymentMethodToOrder(principal.getName(), request);
    }

    @PatchMapping("purchase")
    public OrderResponse proceedToPurchase(Principal principal) {
        return purchaseService.proceedToPurchase(principal.getName());
    }
}
