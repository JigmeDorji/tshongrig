package com.spms.mvc.Enumeration;

/**
 * Created by user on 5/4/2020.
 */
public enum CommonStatus {

    Active('A', "Active"),
    Inactive('I', "Inactive"),
    Submitted('S', "Submitted"),
    Pending('P', "Pending"),
    NOT_APPROVED('N', "Not Approved"),
    Approve('A', "Approved"),
    SUB_ACTIVATED('S', "Subscription Activated");

    private final Character value;
    private final String text;

    CommonStatus(Character value, String text) {
        this.value = value;
        this.text = text;
    }

    public Character getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
