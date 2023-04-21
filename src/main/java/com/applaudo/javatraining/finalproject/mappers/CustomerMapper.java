package com.applaudo.javatraining.finalproject.mappers;

import com.applaudo.javatraining.finalproject.controllers.responses.CustomerResponse;
import com.applaudo.javatraining.finalproject.models.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse customerToCostumerResponse(Customer customer);

}
