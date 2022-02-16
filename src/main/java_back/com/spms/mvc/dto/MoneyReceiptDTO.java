package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by jigme.dorji on 10/27/2020.
 */
public class MoneyReceiptDTO {
    private String receiptNo;
    private Date receiptDate;
    private String partyLedgerId;
    private  Double amount;
    private  Double tDSAmount;
    private  Double retentionAmount;
    private  Double mobilizationAdvAmount;
    private  String mobilizationAdvPartyLedgerId;
    private  Double materialAdvAmount;
    private  String materialAdvPartyLedgerId;
    private Integer isCash;
    private String bankLedgerId;
    private String ledgerName;

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getPartyLedgerId() {
        return partyLedgerId;
    }

    public void setPartyLedgerId(String partyLedgerId) {
        this.partyLedgerId = partyLedgerId;
    }

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public Double gettDSAmount() {
        return tDSAmount;
    }

    public void settDSAmount(Double tDSAmount) {
        this.tDSAmount = tDSAmount;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public Double getRetentionAmount() {
        return retentionAmount;
    }

    public void setRetentionAmount(Double retentionAmount) {
        this.retentionAmount = retentionAmount;
    }

    public Double getMobilizationAdvAmount() {
        return mobilizationAdvAmount;
    }

    public void setMobilizationAdvAmount(Double mobilizationAdvAmount) {
        this.mobilizationAdvAmount = mobilizationAdvAmount;
    }

    public String getMobilizationAdvPartyLedgerId() {
        return mobilizationAdvPartyLedgerId;
    }

    public void setMobilizationAdvPartyLedgerId(String mobilizationAdvPartyLedgerId) {
        this.mobilizationAdvPartyLedgerId = mobilizationAdvPartyLedgerId;
    }

    public Double getMaterialAdvAmount() {
        return materialAdvAmount;
    }

    public void setMaterialAdvAmount(Double materialAdvAmount) {
        this.materialAdvAmount = materialAdvAmount;
    }

    public String getMaterialAdvPartyLedgerId() {
        return materialAdvPartyLedgerId;
    }

    public void setMaterialAdvPartyLedgerId(String materialAdvPartyLedgerId) {
        this.materialAdvPartyLedgerId = materialAdvPartyLedgerId;
    }
}
