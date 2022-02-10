package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Bcass Sawa on 8/3/2019.
 */

@Entity
@Table(name = "tbl_acc_bank_reconciliation")
public class BankReconciliation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankReconciliationId")
    private Integer bankReconciliationId;

    @Column(name = "bookBalance")
    private Double bookBalance;

    @Column(name = "chequeIssuedNotEncash")
    private Double chequeIssuedNotEncash;

    @Column(name = "directDeposit")
    private Double directDeposit;

    @Column(name = "directTransfer")
    private Double directTransfer;

    @Column(name = "previousMonthChequeEncash")
    private Double previousMonthChequeEncash;

    @Column(name = "bankReconciliationAmount")
    private Double bankReconciliationAmount;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getBankReconciliationId() {
        return bankReconciliationId;
    }

    public void setBankReconciliationId(Integer bankReconciliationId) {
        this.bankReconciliationId = bankReconciliationId;
    }

    public Double getBookBalance() {
        return bookBalance;
    }

    public void setBookBalance(Double bookBalance) {
        this.bookBalance = bookBalance;
    }

    public Double getChequeIssuedNotEncash() {
        return chequeIssuedNotEncash;
    }

    public void setChequeIssuedNotEncash(Double chequeIssuedNotEncash) {
        this.chequeIssuedNotEncash = chequeIssuedNotEncash;
    }

    public Double getDirectDeposit() {
        return directDeposit;
    }

    public void setDirectDeposit(Double directDeposit) {
        this.directDeposit = directDeposit;
    }

    public Double getDirectTransfer() {
        return directTransfer;
    }

    public void setDirectTransfer(Double directTransfer) {
        this.directTransfer = directTransfer;
    }

    public Double getPreviousMonthChequeEncash() {
        return previousMonthChequeEncash;
    }

    public void setPreviousMonthChequeEncash(Double previousMonthChequeEncash) {
        this.previousMonthChequeEncash = previousMonthChequeEncash;
    }

    public Double getBankReconciliationAmount() {
        return bankReconciliationAmount;
    }

    public void setBankReconciliationAmount(Double bankReconciliationAmount) {
        this.bankReconciliationAmount = bankReconciliationAmount;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
