package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Description: Loan
 * Date:  2021-May-23
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-23
 * Change Description:
 * Search Tag:
 */
@Entity
@Table(name = "tbl_acc_loan")
public class Loan {
    @Id
    @Column(name = "loanId")
    private Integer loanId;

    @Column(name = "loanLedgerName")
    private String loanLedgerName;

    @Column(name = "loanAccNo")
    private String loanAccNo;

    @Column(name = "bank")
    private String bank;

    @Column(name = "branch")
    private String branch;

    @Column(name = "monthlyEmi")
    private Double monthlyEmi;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getLoanLedgerName() {
        return loanLedgerName;
    }

    public void setLoanLedgerName(String loanLedgerName) {
        this.loanLedgerName = loanLedgerName;
    }

    public String getLoanAccNo() {
        return loanAccNo;
    }

    public void setLoanAccNo(String loanAccNo) {
        this.loanAccNo = loanAccNo;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Double getMonthlyEmi() {
        return monthlyEmi;
    }

    public void setMonthlyEmi(Double monthlyEmi) {
        this.monthlyEmi = monthlyEmi;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
