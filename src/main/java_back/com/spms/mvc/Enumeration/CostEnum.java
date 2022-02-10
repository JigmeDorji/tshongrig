package com.spms.mvc.Enumeration;

/**
 * Created by jigme.dorji on 5/1/2021.
 */
public enum CostEnum {
    GENERAL(1,"General"),
    PRODUCTION(2,"Production");

    Integer value;
    String text;

    CostEnum(Integer value, String text) {
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
