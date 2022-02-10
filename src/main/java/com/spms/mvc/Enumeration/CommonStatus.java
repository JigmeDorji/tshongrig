package com.spms.mvc.Enumeration;

/**
 * Created by user on 5/4/2020.
 */
public enum CommonStatus {

    Active('A', "Active"),
    Inactive('I', "Inactive"),
    Submitted('S', "Submitted"),
    Pending('P', "Pending"),
    Reject('R', "Rejected"),
    Approve('A', "Approved by Reviewer"),
    ApproveByDirector('D', "Approved by Director"),
    ApproveByCeo('C', "Approved by Director");

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
