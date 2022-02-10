package com.spms.mvc.Enumeration;

/**
 * Created by Bcass Sawa on 10/26/2019.
 */
public enum VoucherTypeEnum {

    PAYMENT(1, "Payment"),
    RECEIPT(2, "Receipt"),
    CONTRA(3, "Contra"),
    JOURNAL(4, "Journal"),
    SALES(5, "Sales"),
    PURCHASE(6, "Purchase");

    private final Integer value;
    private final String text;

    VoucherTypeEnum(Integer value, String text) {
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
