package com.applaudo.javatraining.finalproject;

import com.applaudo.javatraining.finalproject.config.JwtAuthConverterProperties;
import com.applaudo.javatraining.finalproject.controllers.requests.OrderRequest;
import com.applaudo.javatraining.finalproject.controllers.responses.OrderResponse;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;

public class TestUtilities {

    @MockBean
    JwtAuthConverterProperties jwtAuthConverterProperties;

    OrderRequest orderRequest = new OrderRequest(
            getRandomLongRange(100L,500L), 3);
    OrderResponse orderResponse = OrderResponse.builder()
            .id(1L)
            .items(new HashSet<>())
            .build();

    private Long getRandomLongRange(Long init, Long end){
        return  init + (long) (Math.random() * (end - init));
    }

}
