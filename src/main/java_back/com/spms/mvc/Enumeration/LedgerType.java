package com.spms.mvc.Enumeration;

/**
 * Created by jigme.dorji on 4/10/2021.
 */
public enum LedgerType {
    SALARY_admin(1, "Salary Admin"),
    PF_EMPLOYEE(2, "PF(Employee)"),
    GIS(3, "GIS"),
    ADVANCE(4, "Advance"),
    HC(5, "HC"),
    SALARY_TDS(6, "Salary TDS"),
    SALARY_PAYABLE(7, "Salary Payable"),
    PURCHASE(8, "Purchase"),
    MATERIAL(9, "Material"),
    PF_EMPLOYER(10, "PF(Employer)"),
    SALARY_Production(11, "Salary Production"),
    TDS(12, "TDS");

    Integer value;
    String text;

    LedgerType(Integer value, String text) {
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
