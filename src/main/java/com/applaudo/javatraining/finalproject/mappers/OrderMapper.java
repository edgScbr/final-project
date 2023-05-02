package com.applaudo.javatraining.finalproject.mappers;

import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CustomerMapper.class, ItemMapper.class, AddressMapper.class, PaymentMethodMapper.class})
public interface OrderMapper {

    @Mapping(source = "items", target = "total", qualifiedByName = "orderTotal")
    OrderResponse orderToOrderResponse(Order order);


    @Named("orderTotal")
    static double orderTotal(Set<Item> items){
        double total = 0.00;
        for(Item item: items){
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
