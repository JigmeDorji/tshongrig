package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by Bcass Sawa on 5/14/2019.
 */
public class AccProfitAndLossReportDTO {
    private String particular;
    private Integer groupLevel;
    private Double amount;
    private Double returnPNLAmount;

    private String ledgerId;
    private Integer voucherId;
    private String ledgerName;
    private Integer accTypeId;
    private Double debitAmount;
    private Double creditAmount;
    private String accTypeName;
    private Boolean isTopParent;
    private Date voucherCreatedDate;
    private Double totalCredit;
    private Double totalDebit;
    private Integer voucherTypeId;
    private String voucherTypeName;
    private Integer voucherNo;
    private Integer groupId;
    private Boolean isNotLedger;
    private Boolean isLedgerGroup;
    private Double drcrAmount;

    public Boolean getIsLedgerGroup() {
        return isLedgerGroup;
    }

    public void setIsLedgerGroup(Boolean isLedgerGroup) {
        this.isLedgerGroup = isLedgerGroup;
    }

    public Double getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(Double totalCredit) {
        this.totalCredit = totalCredit;
    }

    public Double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public String getVoucherTypeName() {
        return voucherTypeName;
    }

    public void setVoucherTypeName(String voucherTypeName) {
        this.voucherTypeName = voucherTypeName;
    }

    public Integer getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(Integer voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getLedgerName() {
        return ledgerName;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getAccTypeName() {
        return accTypeName;
    }

    public void setAccTypeName(String accTypeName) {
        this.accTypeName = accTypeName;
    }

    public Boolean getIsTopParent() {
        return isTopParent;
    }

    public void setIsTopParent(Boolean isTopParent) {
        this.isTopParent = isTopParent;
    }

    public Integer getAccTypeId() {
        return accTypeId;
    }

    public void setAccTypeId(Integer accTypeId) {
        this.accTypeId = accTypeId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Date getVoucherCreatedDate() {
        return voucherCreatedDate;
    }

    public void setVoucherCreatedDate(Date voucherCreatedDate) {
        this.voucherCreatedDate = voucherCreatedDate;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Boolean getIsNotLedger() {
        return isNotLedger;
    }

    public void setIsNotLedger(Boolean isNotLedger) {
        this.isNotLedger = isNotLedger;
    }

    public String getLedgerId() {
        return ledgerId;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public Integer getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(Integer groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Double getDrcrAmount() {
        return drcrAmount;
    }

    public void setDrcrAmount(Double drcrAmount) {
        this.drcrAmount = drcrAmount;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Double getReturnPNLAmount() {
        return returnPNLAmount;
    }

    public void setReturnPNLAmount(Double returnPNLAmount) {
        this.returnPNLAmount = returnPNLAmount;
    }
}
