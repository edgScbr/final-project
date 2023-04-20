package com.applaudo.javatraining.finalproject.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
public class ItemKey implements Serializable {

    private Long productId;
    private Long orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemKey itemKey)) return false;
        return getProductId().equals(itemKey.getProductId()) && getOrderId().equals(itemKey.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductId(), getOrderId());
    }
}
