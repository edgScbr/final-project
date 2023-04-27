package com.applaudo.javatraining.finalproject;

import com.applaudo.javatraining.finalproject.config.JwtAuthConverterProperties;
import com.applaudo.javatraining.finalproject.controllers.requests.AddressRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.requests.PaymentMethodRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.*;
import com.applaudo.javatraining.finalproject.mappers.*;
import com.applaudo.javatraining.finalproject.models.*;
import com.applaudo.javatraining.finalproject.models.enums.DeliveryStatus;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.models.enums.PaymentOption;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UtilitiesTest {

    AddressMapper addressMapper = new AddressMapperImpl();
    CustomerMapper customerMapper = new CustomerMapperImpl();
    ProductMapper productMapper = new ProductMapperImpl();
    ItemMapper itemMapper = new ItemMapperImpl(this.productMapper);
    OrderMapper orderMapper = new OrderMapperImpl(this.customerMapper, this.itemMapper, this.addressMapper);
    PaymentMethodMapper paymentMethodMapper = new PaymentMethodMapperImpl();


    @MockBean
    JwtAuthConverterProperties jwtAuthConverterProperties;

    //models
    public Product productModel = new Product(1L, "Keyboard", "Mechanical Keyboard",
            15.50, 25);
    public Address addressModel =
            new Address(1L, new Customer(), 1, "Main st",
                    "Los Gatos", "CA", 95030);
    public PaymentMethod paymentMethod = new PaymentMethod(1L, PaymentOption.CREDIT_CARD);
    public Customer customerModel = new Customer(1L, "John", "Doe", "user1",
            "jd@gmail.com", new HashSet<>(Collections.singletonList(addressModel)),
            new HashSet<>(Collections.singletonList(paymentMethod)));
    public ItemKey itemKey = new ItemKey(1L, 1L);
    public Item itemModel = new Item(itemKey, productModel, null, 5);
    public Order orderModel = new Order(1L, customerModel, addressModel, OrderStatus.CHECKOUT,
            DeliveryStatus.PENDING, new HashSet<>(List.of(itemModel)), paymentMethod);


    //Requests
    public OrderRequest orderRequest = new OrderRequest(
            1L, 3);
    public PaymentMethodRequest paymentMethodRequest =
            new PaymentMethodRequest(PaymentOption.CREDIT_CARD);
    public AddressRequest addressRequest = new AddressRequest(1L);


    //Responses
    public AddressResponse addressResponse = addressMapper.addressToAddressResponse(addressModel);
    public PaymentMethodResponse paymentMethodResponse =
            paymentMethodMapper.paymentMethodToPaymentMethodResponse(paymentMethod);
    public CustomerResponse customerResponse = customerMapper.customerToCostumerResponse(customerModel);
    public ProductItemResponse productItemResponse = productMapper
            .productToProductItemResponse(productModel);
    public ItemResponse itemResponse = itemMapper.itemToItemResponse(itemModel);
    public Set<ItemResponse> itemResponseSet =
            new HashSet<>(Collections.singletonList(itemResponse));
    public OrderResponse orderResponse = orderMapper.orderToOrderResponse(orderModel);
}
