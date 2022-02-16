package com.spms.mvc.dto;

import java.util.Date;

/**
 * Description: AutoVoucherDTO
 * Date:  2021-May-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-08
 * Change Description:
 * Search Tag:
 */
public class AutoVoucherDTO {

    private Integer autoVoucherId;
    private Integer typeId;
    private Date autoVoucherDate;
    private String paidTo;
    private String ledgerId;
    private String description;
    private Integer paidForTypeId;
    private Integer costId;
    private Double amount;
    private Integer tdsType;
    private Double tdsAmount;
    private String deductedFrom;
    private Double deductedAmount;
    private Integer paidInTypeId;
    private String bankLedgerId;
    private Double amountPaid;
    private Integer isCash;
    private String text;
    private String id;
    private Double receiptAmount;
    private Integer cashDepositWithdrawalType;
    private Double depositedAmount;

    //Receipt
    private String receiveFrom;
    private Integer receivedFor;
    private Double amountReceived;
    private String capitalLedgerName;

    //Bank Transfer
    private String bankLedgerFromId;
    private String bankLedgerToId;

    //Adjustment
    private String adjustedAgainst;
    private String adjustedFrom;

    //payable
    private String partyLedgerId;
    private String partyName;
    private String expenditure;

    public Integer getAutoVoucherId() {
        return autoVoucherId;
    }

    public void setAutoVoucherId(Integer autoVoucherId) {
        this.autoVoucherId = autoVoucherId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Date getAutoVoucherDate() {
        return autoVoucherDate;
    }

    public void setAutoVoucherDate(Date autoVoucherDate) {
        this.autoVoucherDate = autoVoucherDate;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
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

    public Integer getPaidForTypeId() {
        return paidForTypeId;
    }

    public void setPaidForTypeId(Integer paidForTypeId) {
        this.paidForTypeId = paidForTypeId;
    }

    public Integer getCostId() {
        return costId;
    }

    public void setCostId(Integer costId) {
        this.costId = costId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTdsType() {
        return tdsType;
    }

    public void setTdsType(Integer tdsType) {
        this.tdsType = tdsType;
    }

    public Double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(Double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public String getDeductedFrom() {
        return deductedFrom;
    }

    public void setDeductedFrom(String deductedFrom) {
        this.deductedFrom = deductedFrom;
    }

    public Double getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(Double deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public Integer getPaidInTypeId() {
        return paidInTypeId;
    }

    public void setPaidInTypeId(Integer paidInTypeId) {
        this.paidInTypeId = paidInTypeId;
    }

    public String getBankLedgerId() {
        return bankLedgerId;
    }

    public void setBankLedgerId(String bankLedgerId) {
        this.bankLedgerId = bankLedgerId;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Integer getIsCash() {
        return isCash;
    }

    public void setIsCash(Integer isCash) {
        this.isCash = isCash;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Double receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Integer getCashDepositWithdrawalType() {
        return cashDepositWithdrawalType;
    }

    public void setCashDepositWithdrawalType(Integer cashDepositWithdrawalType) {
        this.cashDepositWithdrawalType = cashDepositWithdrawalType;
    }

    public Double getDepositedAmount() {
        return depositedAmount;
    }

    public void setDepositedAmount(Double depositedAmount) {
        this.depositedAmount = depositedAmount;
    }

    public String getReceiveFrom() {
        return receiveFrom;
    }

    public void setReceiveFrom(String receiveFrom) {
        this.receiveFrom = receiveFrom;
    }

    public Integer getReceivedFor() {
        return receivedFor;
    }

    public void setReceivedFor(Integer receivedFor) {
        this.receivedFor = receivedFor;
    }

    public Double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(Double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public String getCapitalLedgerName() {
        return capitalLedgerName;
    }

    public void setCapitalLedgerName(String capitalLedgerName) {
        this.capitalLedgerName = capitalLedgerName;
    }

    public String getBankLedgerFromId() {
        return bankLedgerFromId;
    }

    public void setBankLedgerFromId(String bankLedgerFromId) {
        this.bankLedgerFromId = bankLedgerFromId;
    }

    public String getBankLedgerToId() {
        return bankLedgerToId;
    }

    public void setBankLedgerToId(String bankLedgerToId) {
        this.bankLedgerToId = bankLedgerToId;
    }

    public String getAdjustedAgainst() {
        return adjustedAgainst;
    }

    public void setAdjustedAgainst(String adjustedAgainst) {
        this.adjustedAgainst = adjustedAgainst;
    }

    public String getAdjustedFrom() {
        return adjustedFrom;
    }

    public void setAdjustedFrom(String adjustedFrom) {
        this.adjustedFrom = adjustedFrom;
    }

    public String getPartyLedgerId() {
        return partyLedgerId;
    }

    public void setPartyLedgerId(String partyLedgerId) {
        this.partyLedgerId = partyLedgerId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(String expenditure) {
        this.expenditure = expenditure;
    }
}
