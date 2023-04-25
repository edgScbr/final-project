package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.TestUtilities;
import com.applaudo.javatraining.finalproject.controllers.ItemController;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.services.interfaces.AddItemService;
import com.applaudo.javatraining.finalproject.services.interfaces.RemoveItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemController.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc(addFilters = false)
public class ItemControllerTest extends TestUtilities {

    @Autowired
    ItemController itemController;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    AddItemService addItemService;
    @MockBean
    RemoveItemService removeItemService;

    Principal mockPrincipal;

    @BeforeEach
    void init() {
        mockPrincipal = mock(Principal.class);
        given(mockPrincipal.getName()).willReturn("user");
    }

    @Test
    public void givenOrderRequestToAddAnItem_whenPostOrder_thenReturnOrderResponse() throws Exception {
        given(addItemService.addItems(anyString(), any())).willReturn(orderResponse);

        mockMvc.perform(post("/store/items").contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .content(new ObjectMapper().writeValueAsString(orderRequest))
                        .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customer.userName", is(orderResponse.getCustomer().getUserName())));
    }

    @Test
    public void givenInvalidOrderRequestToAddAnItem_whenPostOrder_thenThrowException() throws Exception {
        OrderRequest invalidOrderRequest = new OrderRequest(null, null);

        mockMvc.perform(post("/store/items").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidOrderRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenItemId_whenDeleteItem_thenReturnNoContentStatus() throws Exception {
        long productId = 1L;
        willDoNothing().given(removeItemService).removeItem(anyString(), anyLong());

        mockMvc.perform(delete("/store/items/{productId}", productId)
                        .principal(mockPrincipal)
                        .characterEncoding("utf-8"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}
