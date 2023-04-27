package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.services.interfaces.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final CreateOrderService createOrderService;

    private final AddAddressService addAddressService;

    private final PaymentMethodService paymentMethodService;

    private final PurchaseService purchaseService;

    @Operation(summary = "Create an order with checkout status and at least one item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Order successfully created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Request body contained invalid parameters, productId and quantity cannot be null " +
                            "and must be positive numbers",
                    content = @Content),
            @ApiResponse(responseCode = "422",
                    description = "If order with checkout status already exists, a new checkout cannot be created",
                    content = @Content)
    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public OrderResponse createOrder(@RequestBody @Valid OrderRequest request, Principal principal) {
        return createOrderService.createOrder(principal.getName(), request);
    }


    @Operation(summary = "Search for order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Order successfully retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Order does not exist for user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Order not found with id",
                    content = @Content)

    })
    @GetMapping("{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponse getOrderById(@PathVariable Long id, Principal principal) {
        return createOrderService.getOrderById(id, principal.getName());
    }

    @Operation(summary = "Add or change delivery address to order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Delivery address successfully added to order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Address id provided cannot be null and must be a positive integer",
                    content = @Content),
            @ApiResponse(responseCode = "422",
                    description = "Address provided does not belong to user",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Order with checkout status not found for current user",
                    content = @Content)

    })
    @PatchMapping("addAddress")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponse addAddress(@RequestBody @Valid AddressRequest addressRequest, Principal principal) {
        return addAddressService.addAddressToOrder(principal.getName(), addressRequest);
    }


    @Operation(summary = "Add or change payment method to order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Payment method successfully updated for checkout order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Payment method provided cannot be null or a negative number",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Order with checkout status not found for current user",
                    content = @Content),
            @ApiResponse(responseCode = "422",
                    description = "Payment method provided does not belong to user",
                    content = @Content)
    })
    @PatchMapping("addPaymentMethod")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponse addPaymentMethod(@RequestBody PaymentMethodRequest request, Principal principal) {
        return paymentMethodService.addPaymentMethodToOrder(principal.getName(), request);
    }


    @Operation(summary = "Proceed to purchase, changing order status to paid and delivery status to shipped")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Order status successfully updated to paid and delivery status successfully " +
                            "updated to shipped",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Payment method and delivery address cannot be null",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Order with checkout status not found for current user",
                    content = @Content),
            @ApiResponse(responseCode = "422",
                    description = "Stock for each product will be checked to proceed to purchase",
                    content = @Content)
    })
    @PatchMapping("purchase")
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponse proceedToPurchase(Principal principal) {
        return purchaseService.proceedToPurchase(principal.getName());
    }
}
