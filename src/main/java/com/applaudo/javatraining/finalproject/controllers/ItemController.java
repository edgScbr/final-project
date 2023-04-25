package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.services.interfaces.AddItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.RemoveItemService;
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

    @PostMapping
    public OrderResponse addItem(@RequestBody @Valid OrderRequest request, Principal principal) {
        return addItemService.addItems(principal.getName(), request);
    }

    @DeleteMapping("{productId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long productId, Principal principal) {
        removeItemService.removeItem(principal.getName(), productId);
    }

}
