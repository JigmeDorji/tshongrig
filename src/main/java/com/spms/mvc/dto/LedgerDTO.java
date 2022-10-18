package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by jigmePc on 5/5/2019.
 */
public class LedgerDTO {
    private String ledgerId;
    private String ledgerName;
    private Integer bankId;
    private Integer accTypeId;
    private Date reconciliationDate;
    private String bankAccHolderDetail;
    private String accHolderName;
    private String accNo;
    private String bankName;
    private String branch;
    private Double openingBal;
    private Double retainedEarning;
    private Double currentEarning;
    private Double materialOpeningAmt;
    private Boolean isBankAccLedger;

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public Date getReconciliationDate() {
        return reconciliationDate;
    }

    public void setReconciliationDate(Date reconciliationDate) {
        this.reconciliationDate = reconciliationDate;
    }

    public String getBankAccHolderDetail() {
        return bankAccHolderDetail;
    }

    public void setBankAccHolderDetail(String bankAccHolderDetail) {
        this.bankAccHolderDetail = bankAccHolderDetail;
    }

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Double getOpeningBal() {
        return openingBal;
    }

    public void setOpeningBal(Double openingBal) {
        this.openingBal = openingBal;
    }

    public Boolean getIsBankAccLedger() {
        return isBankAccLedger;
    }

    public void setIsBankAccLedger(Boolean isBankAccLedger) {
        this.isBankAccLedger = isBankAccLedger;
    }

    public Double getRetainedEarning() {
        return retainedEarning;
    }


    public void setRetainedEarning(Double retainedEarning) {
        this.retainedEarning = retainedEarning;


    }

    public Double getCurrentEarning() {
        return currentEarning;
    }

    public void setCurrentEarning(Double currentEarning) {
        this.currentEarning = currentEarning;
    }

    public Double getMaterialOpeningAmt() {
        return materialOpeningAmt;
    }

    public void setMaterialOpeningAmt(Double materialOpeningAmt) {
        this.materialOpeningAmt = materialOpeningAmt;
    }
}
