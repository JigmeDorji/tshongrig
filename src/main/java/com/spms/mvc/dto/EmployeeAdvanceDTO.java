package com.spms.mvc.dto;

import java.util.Date;

/**
 * Created by jigme.dorji on 4/18/2021.
 */
public class EmployeeAdvanceDTO {
    private Integer id;
    private Date advanceDate;
    private String empId;
    private String empName;
    private  Double amount;
    private Integer isCash;
    private String bankLedgerId;

    private String createdBy;
    private Date createdDate;
    private Integer voucherNo;

    public Date getAdvanceDate() {
        return advanceDate;
    }

    public void setAdvanceDate(Date advanceDate) {
        this.advanceDate = advanceDate;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }
}
