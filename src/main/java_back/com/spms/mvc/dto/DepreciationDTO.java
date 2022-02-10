package com.spms.mvc.dto;

/**
 * Created by Bcass Sawa on 8/4/2019.
 */
public class    DepreciationDTO {

    private Double cost;

    private Integer qty;

    private Double disposal;

    private String itemName;

    private String particular;

    private Integer depreciationId;

    private Integer voucherId;

    private String dateOfPurchase;

    private Integer additionalQty;

    private Double additional;

    private Integer disposalQty;

    private Double openingBalance;

    private Integer rateOfDepreciation;

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getDisposal() {
        return disposal;
    }

    public void setDisposal(Double disposal) {
        this.disposal = disposal;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Integer getDepreciationId() {
        return depreciationId;
    }

    public void setDepreciationId(Integer depreciationId) {
        this.depreciationId = depreciationId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getAdditionalQty() {
        return additionalQty;
    }

    public void setAdditionalQty(Integer additionalQty) {
        this.additionalQty = additionalQty;
    }

    public Double getAdditional() {
        return additional;
    }

    public void setAdditional(Double additional) {
        this.additional = additional;
    }

    public Integer getDisposalQty() {
        return disposalQty;
    }

    public void setDisposalQty(Integer disposalQty) {
        this.disposalQty = disposalQty;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public Integer getRateOfDepreciation() {
        return rateOfDepreciation;
    }

    public void setRateOfDepreciation(Integer rateOfDepreciation) {
        this.rateOfDepreciation = rateOfDepreciation;
    }
}
