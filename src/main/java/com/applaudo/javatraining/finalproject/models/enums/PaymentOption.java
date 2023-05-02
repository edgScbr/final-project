package com.applaudo.javatraining.finalproject.models.enums;

public enum PaymentOption {
    CREDIT_CARD("CREDIT_CARD"),
    DEBIT_CARD("DEBIT_CARD"),
    PAYPAL("PAYPAL"),
    CRYPTO("CRYPTO");

    public final String label;

    PaymentOption(String label) {
        this.label = label;
    }
}
