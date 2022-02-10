package com.spms.mvc.dto;

/**
 * Created by Bcass Sawa on 8/3/2019.
 */
public class BankReconciliationDTO {

    private Double bookBalance;

    private Double chequeIssuedNotEncash;

    private Double directDeposit;

    private Double directTransfer;

    private Double previousMonthChequeEncash;

    private Double bankReconciliationAmount;

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
}
