package com.spms.mvc.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by Bcass Sawa on 8/4/2019.
 */

@Entity
@Table(name = "tbl_acc_depreciation_item_details")
public class DepreciationDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "depreciationId")
    private Integer depreciationId;

    @NotNull
    @Column(name = "voucherId")
    private Integer voucherId;

    @NotNull
    @Column(name = "dateOfPurchase")
    private Date dateOfPurchase;

    @Column(name = "additionalQty")
    private Integer additionalQty;

    @Column(name = "additional")
    private Double additional;

    @Column(name = "disposalQty")
    private Integer disposalQty;

    @Column(name = "disposal")
    private Double disposal;

    @NotNull
    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepreciationId() {
        return depreciationId;
    }

    public void setDepreciationId(Integer depreciationId) {
        this.depreciationId = depreciationId;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getAdditionalQty() {
        return additionalQty;
    }

    public void setAdditionalQty(Integer additionalQty) {
        this.additionalQty = additionalQty;
    }

    public Double getAdditional() {
        return additional;
    }

    public void setAdditional(Double additional) {
        this.additional = additional;
    }

    public Integer getDisposalQty() {
        return disposalQty;
    }

    public void setDisposalQty(Integer disposalQty) {
        this.disposalQty = disposalQty;
    }

    public Double getDisposal() {
        return disposal;
    }

    public void setDisposal(Double disposal) {
        this.disposal = disposal;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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
