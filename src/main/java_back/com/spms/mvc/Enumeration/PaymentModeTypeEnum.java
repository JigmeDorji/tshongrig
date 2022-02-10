package com.spms.mvc.Enumeration;

/**
 * Created by Bcass Sawa on 2/27/2020.
 */
public enum PaymentModeTypeEnum {
    CASH(1, "Cash"),
    BANK(2, "Bank"),
    CREDIT(3, "CreditCredit"),
    BANK_AND_CASH(4, "Both (Bank & Cash)");

    private final Integer value;
    private final String text;

    PaymentModeTypeEnum(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
