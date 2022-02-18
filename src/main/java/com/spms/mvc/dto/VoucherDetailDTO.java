package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by jigmePc on 5/8/2019.
 */
public class VoucherDetailDTO {
    private Integer voucherDetailId;
    private Integer voucherId;
    private String ledgerId;
    private String description;
    private String debitAmount;
    private String creditAmount;
    private Double drcrAmount;
    private Integer voucherTypeId;
    private Date voucherEntryDate;
    private Integer accTypeId;
    private Integer isCash;
    private String bankLedgerId;

    public Integer getVoucherDetailId() {
        return voucherDetailId;
    }

    public void setVoucherDetailId(Integer voucherDetailId) {
        this.voucherDetailId = voucherDetailId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getDrcrAmount() {
        return drcrAmount;
    }

    public void setDrcrAmount(Double drcrAmount) {
        this.drcrAmount = drcrAmount;
    }

    public Integer getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(Integer voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Date getVoucherEntryDate() {
        return voucherEntryDate;
    }

    public void setVoucherEntryDate(Date voucherEntryDate) {
        this.voucherEntryDate = voucherEntryDate;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }
}
