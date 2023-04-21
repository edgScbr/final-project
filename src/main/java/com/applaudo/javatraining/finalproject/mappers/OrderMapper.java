package com.applaudo.javatraining.finalproject.mappers;

import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import com.applaudo.javatraining.finalproject.models.Item;
import com.applaudo.javatraining.finalproject.models.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {CustomerMapper.class, ItemMapper.class})
public interface OrderMapper {

    @Mapping(source = "order", target = "total", qualifiedByName = "orderTotal")
    OrderResponse orderToOrderResponse(Order order);


    @Named("orderTotal")
    public static double orderTotal(Order order){
        double total = 0.00;
        for(Item item: order.getItems()){
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        return total;
    }
}
