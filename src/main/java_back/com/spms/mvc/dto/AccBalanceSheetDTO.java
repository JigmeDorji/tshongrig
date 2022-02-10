package com.spms.mvc.dto;

import java.math.BigDecimal;

/**
 * Created by jigme.dorji on 10/25/2020.
 */
public class AccBalanceSheetDTO {
    private String particular;
    private Double amount;
    private Integer groupLevel;
    private Integer accTypeId;
    private Integer groupId;

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

    public Integer getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(Integer groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
