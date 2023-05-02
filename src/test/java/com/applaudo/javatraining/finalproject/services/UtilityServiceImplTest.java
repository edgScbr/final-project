package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.UtilitiesTest;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.CustomerRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.repositories.ProductRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;


public class UtilityServiceImplTest extends UtilitiesTest {

    UtilityService utilityService;

    OrderRepository orderRepository = mock(OrderRepository.class);
    ProductRepository productRepository = mock(ProductRepository.class);
    CustomerRepository customerRepository = mock(CustomerRepository.class);

    @BeforeEach
    void init() {
        utilityService = new UtilityServiceImpl(
                this.orderRepository,
                this.productRepository,
                this.customerRepository);
    }

    @Test
    public void givenOrderRequest_whenFindProduct_thenReturnProduct() throws Exception {
        given(productRepository.findById(anyLong())).willReturn(Optional.of(productModel));

        Product product = utilityService.findProduct(orderRequest);

        assertThat(product).isNotNull();
        assertThat(product.getName()).isSameAs(productModel.getName());
        assertThat(product.getDescription()).isSameAs(productModel.getDescription());
        assertThat(product.getPrice()).isSameAs(productModel.getPrice());
    }

    @Test
    public void givenBadOrderRequest_whenFindProduct_thenReturnException() throws Exception {
        Product noStock = productModel;
        noStock.setStock(0);

        given(productRepository.findById(anyLong())).willReturn(Optional.of(noStock));

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    Product product = utilityService.findProduct(orderRequest);
                });
    }

    @Test
    public void givenNonExistingProductId_whenFindProduct_thenReturnException() throws Exception {
        given(productRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    Product product = utilityService.findProduct(orderRequest);
                });
    }

    @Test
    public void givenUserName_whenFindCustomer_thenReturnCustomer() throws Exception {
        given(customerRepository.findByUserName(anyString())).willReturn(Optional.of(customerModel));

        Customer customer = utilityService.findCustomer(customerModel.getUserName());

        assertThat(customer).isNotNull();
        assertThat(customer.getFirstName()).isSameAs(customerModel.getFirstName());
        assertThat(customer.getLastName()).isSameAs(customerModel.getLastName());
        assertThat(customer.getEmail()).isSameAs(customerModel.getEmail());
    }

    @Test
    public void givenNonExistingUserName_whenFindCustomer_thenReturnCustomer() throws Exception {
        given(customerRepository.findByUserName(anyString())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    Customer customer = utilityService.findCustomer(customerModel.getUserName());
                });
    }

    @Test
    public void givenId_whenGetOrderById_thenReturnOrder() throws Exception {
        given(orderRepository.findById(anyLong())).willReturn(Optional.of(orderModel));

        Order order = utilityService.getOrderById(orderModel.getId());

        assertThat(order).isNotNull();
        assertThat(order.getCustomer()).isSameAs(orderModel.getCustomer());
        assertThat(order.getStatus()).isSameAs(orderModel.getStatus());
        assertThat(order.getItems()).isSameAs(orderModel.getItems());
    }

    @Test
    public void givenNonExistingId_whenGetOrderById_thenReturnException() throws Exception {
        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());

        Assertions.assertThrows(ResponseStatusException.class,
                () -> {
                    Order order = utilityService.getOrderById(0);
                });
    }

    @Test
    public void givenProductIdAndOrder_whenVerifyItemAlreadyAdded_thenReturnOptionalOfItem() throws Exception {
        Optional<Item> optItem = utilityService
                .verifyItemAlreadyAdded(productModel.getId(), orderModel);

        assertThat(optItem).isNotNull();
    }

    @Test
    public void givenUserNameAndOrderStatus_whenGetOrderByUserNameAndStatus_thenReturnOptionalOfOrder() throws Exception {
        Optional<Order> order = utilityService
                .getOrderByUserNameAndStatus(customerModel.getUserName(), OrderStatus.CHECKOUT);
        assertThat(order).isNotNull();
    }

}
