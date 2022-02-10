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
}
