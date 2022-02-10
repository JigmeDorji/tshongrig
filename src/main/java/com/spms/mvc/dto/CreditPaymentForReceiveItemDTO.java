package com.spms.mvc.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Bcass Sawa on 2/13/2018.
 */
public class CreditPaymentForReceiveItemDTO {
    private String agencyId;
    private String bill_reference;
    private Double total_amount_paid;
    private Double total_amount;
    private Double amount;
    private String agencyName;
    private Double balanceAmount;
    private String status;
    private String partNumber;
    private String partName;
    private Date itemReceivedDate;
    private BigDecimal quantityReceived;
    private Double rate;
    private Integer qty;

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getBill_reference() {
        return bill_reference;
    }

    public void setBill_reference(String bill_reference) {
        this.bill_reference = bill_reference;
    }

    public Double getTotal_amount_paid() {
        return total_amount_paid;
    }

    public void setTotal_amount_paid(Double total_amount_paid) {
        this.total_amount_paid = total_amount_paid;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Date getItemReceivedDate() {
        return itemReceivedDate;
    }

    public void setItemReceivedDate(Date itemReceivedDate) {
        this.itemReceivedDate = itemReceivedDate;
    }

    public BigDecimal getQuantityReceived() {
        return quantityReceived;
    }

    public void setQuantityReceived(BigDecimal quantityReceived) {
        this.quantityReceived = quantityReceived;
    }
}
