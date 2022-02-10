package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Description: AutoVoucher
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
@Entity
@Table(name = "tbl_acc_auto_voucher")
public class AutoVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "autoVoucherId")
    private Integer autoVoucherId;

    @Column(name = "typeId")
    private Integer typeId;

    @Column(name = "autoVoucherDate")
    private Date autoVoucherDate;

    @Column(name = "ledgerId")
    private String ledgerId;

    @Column(name = "paidForTypeId")
    private Integer paidForTypeId;

    @Column(name = "costId")
    private Integer costId;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "deductedFrom")
    private String deductedFrom;

    @Column(name = "tdsType")
    private String tdsType;

    @Column(name = "deductedAmount")
    private Double deductedAmount;

    @Column(name = "tdsAmount")
    private Double tdsAmount;

    @Column(name = "paidInTypeId")
    private Integer paidInTypeId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

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

    public String getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(String ledgerId) {
        this.ledgerId = ledgerId;
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

    public String getDeductedFrom() {
        return deductedFrom;
    }

    public void setDeductedFrom(String deductedFrom) {
        this.deductedFrom = deductedFrom;
    }

    public String getTdsType() {
        return tdsType;
    }

    public void setTdsType(String tdsType) {
        this.tdsType = tdsType;
    }

    public Double getDeductedAmount() {
        return deductedAmount;
    }

    public void setDeductedAmount(Double deductedAmount) {
        this.deductedAmount = deductedAmount;
    }

    public Double getTdsAmount() {
        return tdsAmount;
    }

    public void setTdsAmount(Double tdsAmount) {
        this.tdsAmount = tdsAmount;
    }

    public Integer getPaidInTypeId() {
        return paidInTypeId;
    }

    public void setPaidInTypeId(Integer paidInTypeId) {
        this.paidInTypeId = paidInTypeId;
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
