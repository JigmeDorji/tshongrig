package com.spms.mvc.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Bcass Sawa on 8/9/2019.
 */

@Entity
@Table(name = "tbl_acc_depreciation")
public class Depreciation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "depreciationId")
    private Integer depreciationId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "rateOfDepreciation")
    private Integer rateOfDepreciation;

    @Column(name = "openingBalance")
    private Double openingBalance;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "setDate")
    private Date setDate;

    @Column(name = "createdBy")
    private String createdBy;

    public Integer getDepreciationId() {
        return depreciationId;
    }

    public void setDepreciationId(Integer depreciationId) {
        this.depreciationId = depreciationId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getRateOfDepreciation() {
        return rateOfDepreciation;
    }

    public void setRateOfDepreciation(Integer rateOfDepreciation) {
        this.rateOfDepreciation = rateOfDepreciation;
    }

    public Double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(Double openingBalance) {
        this.openingBalance = openingBalance;
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
