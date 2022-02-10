package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jigmePc on 5/8/2019.
 */
@Entity
@Table(name = "tbl_acc_voucher_entries")
public class Voucher {
    @Id
    @Column(name = "voucherId")
    private Integer voucherId;

    @Column(name = "voucherTypeId")
    private Integer voucherTypeId;

    @Column(name = "voucherNo")
    private Integer voucherNo;

    @Column(name = "voucherEntryDate")
    private Date voucherEntryDate;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "financialYearId")
    private Integer financialYearId;

    @Column(name = "narration")
    private String narration;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Integer getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(Integer voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public Date getVoucherEntryDate() {
        return voucherEntryDate;
    }

    public void setVoucherEntryDate(Date voucherEntryDate) {
        this.voucherEntryDate = voucherEntryDate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
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
