package com.spms.mvc.Enumeration;

/**
 * Created by jigme.dorji on 5/1/2021.
 */
public enum BusinessType {
    Trading(1, "Trading"),
    Service(2, "Service"),
    Manufacturing(3, "Manufacturing"),
    Construction(4, "Construction"),
    Hotel(5, "Hotel"),
    Restaurant(6, "Restaurant");

    Integer value;
    String text;

    BusinessType(Integer value, String text) {
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

