package com.spms.mvc.dto;

/**
 * Created by jigmePc on 7/30/2019.
 */
public class AccTrialBalanceDTO {
    private String particular;
    private Double drAmount;
    private Double crAmount;
    private Double amount;
    private Integer groupId;
    private Integer groupLevel;
    private Integer accTypeId;
    private Boolean isTopParent;
    private String ledgerId;

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Boolean getIsTopParent() {
        return isTopParent;
    }

    public void setIsTopParent(Boolean isTopParent) {
        this.isTopParent = isTopParent;
    }

    public Double getDrAmount() {
        return drAmount;
    }

    public void setDrAmount(Double drAmount) {
        this.drAmount = drAmount;
    }

    public Double getCrAmount() {
        return crAmount;
    }

    public void setCrAmount(Double crAmount) {
        this.crAmount = crAmount;
    }

    public Boolean getTopParent() {
        return isTopParent;
    }

    public void setTopParent(Boolean topParent) {
        isTopParent = topParent;
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

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }
}
