package com.spms.mvc.dto;

import javax.persistence.Column;

/**
 * Description: LoanDTO
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
public class LoanDTO {

    private Integer loanId;

    private String loanLedgerName;

    private String loanAccNo;

    private String bank;

    private String branch;

    private Double monthlyEmi;

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
}
