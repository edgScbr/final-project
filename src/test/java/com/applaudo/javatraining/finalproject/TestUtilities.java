package com.applaudo.javatraining.finalproject;

import com.applaudo.javatraining.finalproject.config.JwtAuthConverterProperties;
import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.*;
import com.applaudo.javatraining.finalproject.models.*;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

public class TestUtilities {

    @MockBean
    JwtAuthConverterProperties jwtAuthConverterProperties;

    public OrderRequest orderRequest = new OrderRequest(
            getRandomLongRange(), 3);


    public AddressResponse addressResponse = AddressResponse.builder()
            .id(getRandomLongRange())
            .streetNumber(1)
            .streetName("Main st")
            .city("Los Gatos")
            .state("CA")
            .zip(95030)
            .build();

    public PaymentMethodRequest paymentMethodRequest =
            new PaymentMethodRequest(PaymentOption.CREDIT_CARD);

    public PaymentMethodResponse paymentMethodResponse =
            new PaymentMethodResponse(getRandomLongRange(), PaymentOption.CREDIT_CARD);

    public CustomerResponse customerResponse = CustomerResponse.builder()
            .id(getRandomLongRange())
            .firstName("John")
            .lastName("Doe")
            .userName("user1")
            .email("jd@gmail.com")
            .build();


    public ProductItemResponse productItemResponse = ProductItemResponse.builder()
            .id(getRandomLongRange())
            .name("Keyboard")
            .description("Mechanical Keyboard")
            .price(15.50)
            .build();

    public ItemResponse itemResponse = ItemResponse.builder()
            .product(productItemResponse)
            .quantity(5)
            .build();

    public Set<ItemResponse> itemResponseSet =
            new HashSet<>(Collections.singletonList(itemResponse));

    public PaymentMethod paymentMethod = new PaymentMethod(1L, PaymentOption.CREDIT_CARD);

    public OrderResponse orderResponse = OrderResponse.builder()
            .id(1L)
            .customer(customerResponse)
            .deliveryAddress(addressResponse)
            .status(OrderStatus.CHECKOUT)
            .deliveryStatus(DeliveryStatus.PENDING)
            .items(itemResponseSet)
            .paymentMethod(paymentMethod)
            .build();

    public Address addressModel =
            new Address(1L, new Customer(), 1, "Main st",
                    "Los Gatos", "CA", 12345);

    public AddressRequest addressRequest = new AddressRequest(1L);

   //models
    public Customer customerModel = new Customer(1L, "John", "Doe", "user1",
            "jd@gmail.com", new HashSet<>(Collections.singletonList(addressModel)),
            new HashSet<>(Collections.singletonList(paymentMethod)));

    public Product productModel = new Product(1L, "Keyboard", "Mechanical Keyboard",
            15.50, 25);

    public ItemKey itemKey = new ItemKey(1L, 1L);
    public Item itemModel = new Item(itemKey, productModel, null, 5);
    public Order orderModel = new Order(1L, customerModel, addressModel, OrderStatus.CHECKOUT,
            DeliveryStatus.PENDING, new HashSet<>(List.of(itemModel)), paymentMethod);


    private Long getRandomLongRange() {
        long init = 1;
        long end = 500;
        return init + (long) (Math.random() * (end - init));
    }

}
