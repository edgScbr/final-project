package com.applaudo.javatraining.finalproject;

import com.applaudo.javatraining.finalproject.config.JwtAuthConverterProperties;
import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.*;
import com.applaudo.javatraining.finalproject.models.PaymentMethod;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TestUtilities {

    @MockBean
    JwtAuthConverterProperties jwtAuthConverterProperties;

    OrderRequest orderRequest = new OrderRequest(
            getRandomLongRange(), 3);

    AddressRequest addressRequest = new AddressRequest(getRandomLongRange());
    AddressResponse addressResponse = AddressResponse.builder()
            .id(getRandomLongRange())
            .streetNumber(1)
            .streetName("Main st")
            .city("Los Gatos")
            .state("CA")
            .zip(95030)
            .build();

    PaymentMethodRequest paymentMethodRequest =
            new PaymentMethodRequest(PaymentOption.CREDIT_CARD);

    PaymentMethodResponse paymentMethodResponse =
            new PaymentMethodResponse(getRandomLongRange(), PaymentOption.CREDIT_CARD);

    CustomerResponse customerResponse = CustomerResponse.builder()
            .id(getRandomLongRange())
            .FirstName("John")
            .LastName("Doe")
            .userName("user1")
            .email("jd@gmail.com")
            .build();


    ProductItemResponse productItemResponse = ProductItemResponse.builder()
            .id(getRandomLongRange())
            .name("Keyboard")
            .description("Mechanical Keyboard")
            .price(15.50)
            .build();

    ItemResponse itemResponse = ItemResponse.builder()
            .product(productItemResponse)
            .quantity(5)
            .build();

    Set<ItemResponse> itemResponseSet =
            new HashSet<>(Collections.singletonList(itemResponse));

    PaymentMethod paymentMethod = new PaymentMethod(1L, PaymentOption.CREDIT_CARD);

    OrderResponse orderResponse = OrderResponse.builder()
            .id(1L)
            .customer(customerResponse)
            .deliveryAddress(addressResponse)
            .status(OrderStatus.CHECKOUT)
            .deliveryStatus(DeliveryStatus.PENDING)
            .items(itemResponseSet)
            .paymentMethod(paymentMethod)
            .build();


    private Long getRandomLongRange() {
        long init = 1;
        long end = 500;
        return init + (long) (Math.random() * (end - init));
    }

}
