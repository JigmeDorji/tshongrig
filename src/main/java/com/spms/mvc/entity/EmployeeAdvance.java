package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jigme.dorji on 4/18/2021.
 */
@Entity
@Table(name = "tbl_hr_employeeadvance")
public class EmployeeAdvance {
    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "advanceDate")
    private Date advanceDate;

    @Column(name = "empId")
    private String empId;

    @Column(name = "amount")
    private  Double amount;

    @Column(name = "paidIn")
    private Integer paidIn;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "voucherNo")
    private Integer voucherNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getPaidIn() {
        return paidIn;
    }

    public void setPaidIn(Integer paidIn) {
        this.paidIn = paidIn;
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

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }
}
