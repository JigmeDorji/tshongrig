package com.spms.mvc.dto;

/**
 * Created by jigmePc on 5/6/2019.
 */
public class AccTypeDTO {
    private Integer accTypeId;
    private String accTypeName;
    private Boolean isBankAccLedger;

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public String getAccTypeName() {
        return accTypeName;
    }

    public void setAccTypeName(String accTypeName) {
        this.accTypeName = accTypeName;
    }

    public Boolean getIsBankAccLedger() {
        return isBankAccLedger;
    }

    public void setIsBankAccLedger(Boolean isBankAccLedger) {
        this.isBankAccLedger = isBankAccLedger;
    }
}
