package com.applaudo.javatraining.finalproject.mappers;

import com.applaudo.javatraining.finalproject.controllers.responses.ProductItemResponse;
import com.applaudo.javatraining.finalproject.models.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductItemResponse productToProductItemResponse(Product product);

}
