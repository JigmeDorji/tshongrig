package com.spms.mvc.Enumeration;

/**
 * Description: PaidForTypeEnum
 * Date:  2021-May-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-08
 * Change Description:
 * Search Tag:
 */
public enum PaidForTypeEnum {

    COST(1, "Cost"),
    ADVANCE(2, "Advance"),
    RECEIPT(3, "Receipt"),
    REPAYMENT(4, "Repayment");

    Integer value;
    String text;

    PaidForTypeEnum(Integer value, String text) {
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
