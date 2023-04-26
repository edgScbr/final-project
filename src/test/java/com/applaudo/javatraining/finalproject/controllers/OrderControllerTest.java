package com.applaudo.javatraining.finalproject.controllers;

import com.applaudo.javatraining.finalproject.TestUtilities;
import com.applaudo.javatraining.finalproject.controllers.OrderController;
import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.services.interfaces.*;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.security.Principal;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest extends TestUtilities {

    @Autowired
    private OrderController orderController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    CreateOrderService createOrderService;

    @MockBean
    AddAddressService addAddressService;
    @MockBean
    PaymentMethodService paymentMethodService;

    @MockBean
    PurchaseService purchaseService;

    Principal mockPrincipal;

    @BeforeEach
    void init() {
        mockPrincipal = mock(Principal.class);
        given(mockPrincipal.getName()).willReturn("user");
    }

    @Test
    public void whenPostOrder_thenCreateOrder() throws Exception {

        given(createOrderService.createOrder(anyString(), any())).willReturn(orderResponse);

        mockMvc.perform(post("/store/orders").contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .content(new ObjectMapper().writeValueAsString(orderRequest))
                        .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customer.userName", is(orderResponse.getCustomer().getUserName())));
    }

    @Test
    public void givenInvalidOrderRequest_whenPostOrder_thenThrowException() throws Exception {
        OrderRequest invalidOrderRequest = new OrderRequest(null, null);

        mockMvc.perform(post("/store/orders").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidOrderRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenAddressRequest_whenPatchOrder_thenReturnUpdatedOrder() throws Exception {

        given(addAddressService.addAddressToOrder(anyString(), any())).willReturn(orderResponse);

        mockMvc.perform(patch("/store/orders/addAddress").contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .content(new ObjectMapper().writeValueAsString(addressRequest))
                        .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    public void givenInvalidAddressRequest_whenPatchOrder_thenThrowException() throws Exception {
        AddressRequest invalidAddressRequest = new AddressRequest(null);

        mockMvc.perform(patch("/store/orders/addAddress").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidAddressRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPaymentMethodRequest_whenPatchOrder_thenReturnUpdatedOrder() throws Exception {

        given(paymentMethodService.addPaymentMethodToOrder(anyString(), any())).willReturn(orderResponse);

        mockMvc.perform(patch("/store/orders/addPaymentMethod").contentType(MediaType.APPLICATION_JSON)
                        .principal(mockPrincipal)
                        .content(new ObjectMapper().writeValueAsString(paymentMethodRequest))
                        .characterEncoding("utf-8"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

}
