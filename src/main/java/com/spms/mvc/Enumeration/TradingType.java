package com.spms.mvc.Enumeration;

/**
 * Created by jigme.dorji on 5/30/2020.
 */
public enum TradingType {

    GARMENT(1, "Garment"),
    WORK_SHOP(2, "Work Shop");


    Integer value;
    String text;

    TradingType(Integer value, String text) {
        this.value = value;
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
