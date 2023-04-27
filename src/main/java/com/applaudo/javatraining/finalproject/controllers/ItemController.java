package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.services.interfaces.AddItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.RemoveItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("store/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final AddItemService addItemService;

    private final RemoveItemService removeItemService;

    @Operation(summary = "Add Item to order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Item successfully added to order",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Product id and quantity cannot be null",
                    content = @Content),
            @ApiResponse(responseCode = "422",
                    description = "User does not have order with checkout status",
                    content = @Content)

    })
    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public OrderResponse addItem(@RequestBody @Valid OrderRequest request, Principal principal) {
        return addItemService.addItems(principal.getName(), request);
    }

    @Operation(summary = "Delete an Item from order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Item successfully deleted from order ",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Item or order not found",
                    content = @Content)

    })
    @DeleteMapping("{productId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long productId, Principal principal) {
        removeItemService.removeItem(principal.getName(), productId);
    }

}
