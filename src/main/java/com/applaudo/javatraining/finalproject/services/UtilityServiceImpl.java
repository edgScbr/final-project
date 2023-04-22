package com.applaudo.javatraining.finalproject.services;

import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.models.Customer;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import com.applaudo.javatraining.finalproject.models.Product;
import com.applaudo.javatraining.finalproject.models.enums.OrderStatus;
import com.applaudo.javatraining.finalproject.repositories.CustomerRepository;
import com.applaudo.javatraining.finalproject.repositories.OrderRepository;
import com.applaudo.javatraining.finalproject.repositories.ProductRepository;
import com.applaudo.javatraining.finalproject.services.interfaces.UtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UtilityServiceImpl implements UtilityService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Product findProduct(OrderRequest request) {
        Optional<Product> optProduct = productRepository.findById(request.getProductId());
        if (optProduct.isPresent()) {
            Product product = optProduct.get();
            if (request.getQuantity() <= product.getStock()) {
                return product;
            } else {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Insufficient stock");
            }
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "product not found with id: " + request.getProductId());
        }
    }

    @Override
    public Customer findCustomer(String userName) {
        Optional<Customer> optCustomer = customerRepository.findByUserName(userName);
        if (optCustomer.isPresent()) {
            return optCustomer.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "user not found with user name " + userName);
        }
    }

    @Override
    public Order getOrderById(long id) {
        Optional<Order> optOrder = orderRepository.findById(id);
        if (optOrder.isPresent()) {
            return optOrder.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "order not found");
        }
    }

    @Override
    public Optional<Order> getOrderByUserNameAndStatus(String userName, OrderStatus status) {
        return orderRepository
                .findByCustomerUserNameAndStatus(userName, status);
    }

    @Override
    public Optional<Item> verifyItemAlreadyAdded(Long productId, Order order) {
        Optional<Item> optionalItem = Optional.empty();
        for (Item added : order.getItems()) {
            if (Objects.equals(productId, added.getProduct().getId())) {
                optionalItem = Optional.of(added);
                break;
            }
        }
        return optionalItem;
    }



}
