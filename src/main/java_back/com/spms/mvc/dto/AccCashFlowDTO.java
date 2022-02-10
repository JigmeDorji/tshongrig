package com.spms.mvc.dto;

/**
 * Created by jigmePc on 8/4/2019.
 */
public class AccCashFlowDTO {
    private String particular;
    private Double amount;
    private String isTopParent;

    public String getIsTopParent() {
        return isTopParent;
    }

    public void setIsTopParent(String isTopParent) {
        this.isTopParent = isTopParent;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
