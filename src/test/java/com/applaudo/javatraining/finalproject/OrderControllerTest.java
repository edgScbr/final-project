package com.applaudo.javatraining.finalproject;

import com.applaudo.javatraining.finalproject.controllers.OrderController;
import com.applaudo.javatraining.finalproject.models.*;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.services.interfaces.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@ActiveProfiles(value = "test")
@AutoConfigureMockMvc(addFilters = false)
public class OrderControllerTest extends TestUtilities{

    @Autowired
    private OrderController orderController;
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    CreateOrderService createOrderService;
    @MockBean
    AddItemService addItemService;
    @MockBean
    RemoveItemService removeItemService;
    @MockBean
    AddAddressService addAddressService;
    @MockBean
    PaymentMethodService paymentMethodService;

    //

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
                .andExpect(status().isCreated());
    }

    private Order getOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setCustomer(getCustomer());
        order.setDeliveryAddress(null);
        order.setDeliveryStatus(DeliveryStatus.PENDING);
        order.setItems(getItemSet());
        order.setPaymentMethod(null);
        order.setStatus(OrderStatus.CHECKOUT);

        return order;
    }



    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setUserName("user1");
        customer.setEmail("jd@gmail.com");
        customer.setAddresses(null);
        customer.setPaymentMethods(null);
        return customer;
    }
//
//    private Set<Address> getSetAddress() {
//        Address address = new Address(1L, getCustomer(), 1, "Main st",
//                "Los Gatos", "CA", 12345);
//        Set<Address> addressSet = new HashSet<>();
//        addressSet.add(address);
//        return addressSet;
//    }
//
//    private Set<PaymentMethod> getPaymentMethodSet() {
//        PaymentMethod paymentMethod = new PaymentMethod(1L, PaymentOption.CREDIT_CARD);
//        Set<PaymentMethod> paymentMethodSet = new HashSet<>();
//        paymentMethodSet.add(paymentMethod);
//        return paymentMethodSet;
//    }
//
    private Set<Item> getItemSet() {
        Set<Item> itemSet = new HashSet<>();
        Item item = new Item(getItemKey(), getProduct(), null, 5);
        itemSet.add(item);
        return itemSet;
    }

    private Product getProduct() {
        return new Product(1L, "Keyboard", "Mechanical keyboard", 15.50, 25);
    }

    private ItemKey getItemKey() {
        return new ItemKey(1L, 1L);
    }


}
