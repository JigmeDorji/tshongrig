package com.spms.mvc.Enumeration;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
public enum UserRoleType {
    Administrator(1, "Administrator"),
    INITIATOR_COMPANY(2, "Initiator"),
    REVIEWER_DHI(3, "Reviewer"),
    OTHERS(4, "Others");

    private final Integer value;
    private final String text;

    UserRoleType(Integer value, String text) {
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
