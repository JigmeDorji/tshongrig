package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tbl_inv_sale_record")
public class SaleRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saleRecordId")
    private Integer saleRecordId;

    @Column(name = "saleDate")
    private Date saleDate;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "receiptMemoNo")
    private String receiptMemoNo;

    @Column(name = "financialYearId")
    private Integer financialYearId;

    @Column(name = "invoiceNo")
    private String invoiceNo;

    @Column(name = "partyId")
    private Integer partyId;

    @Column(name = "saleInType")
    private Integer saleInType;

    @Column(name = "voucherNo")
    private Integer voucherNo;

    @Column(name = "issueTo")
    private String issueTo;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getSaleRecordId() {
        return saleRecordId;
    }

    public void setSaleRecordId(Integer saleRecordId) {
        this.saleRecordId = saleRecordId;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getReceiptMemoNo() {
        return receiptMemoNo;
    }

    public void setReceiptMemoNo(String receiptMemoNo) {
        this.receiptMemoNo = receiptMemoNo;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public Integer getSaleInType() {
        return saleInType;
    }

    public void setSaleInType(Integer saleInType) {
        this.saleInType = saleInType;
    }

    public Integer getVoucherNo() {
        return voucherNo;
    }

    public void setVoucherNo(Integer voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getIssueTo() {
        return issueTo;
    }

    public void setIssueTo(String issueTo) {
        this.issueTo = issueTo;
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
