package com.applaudo.javatraining.finalproject.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ItemKey implements Serializable {

    private Long productId;
    private Long orderId;

}
