package com.applaudo.javatraining.finalproject.mappers;

import com.applaudo.javatraining.finalproject.controllers.responses.ItemResponse;
import com.applaudo.javatraining.finalproject.models.Item;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {ProductMapper.class})
public interface ItemMapper {
        ItemResponse itemToItemResponse(Item item);
}
