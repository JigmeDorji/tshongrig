package com.spms.mvc.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by jigmePc on 10/22/2019.
 */
@Entity
@Table(name = "tbl_acc_sale_invoice")
public class SaleInvoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "saleInvoiceId")
    private Integer saleInvoiceId;

    @Column(name = "partyId")
    private Integer partyId;

    @Column(name = "invoiceNo")
    private String invoiceNo;

    @Column(name = "physicalInvoiceNo")
    private String physicalInvoiceNo;

    @Column(name = "invoiceDate")
    private Date invoiceDate;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getSaleInvoiceId() {
        return saleInvoiceId;
    }

    public void setSaleInvoiceId(Integer saleInvoiceId) {
        this.saleInvoiceId = saleInvoiceId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public String getPhysicalInvoiceNo() {
        return physicalInvoiceNo;
    }

    public void setPhysicalInvoiceNo(String physicalInvoiceNo) {
        this.physicalInvoiceNo = physicalInvoiceNo;
    }
}
