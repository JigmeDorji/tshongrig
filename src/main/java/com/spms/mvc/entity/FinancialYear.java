package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Bikash Rai on 5/5/2020.
 */

@Entity
@Table(name = "tbl_financial_year_setup")
public class FinancialYear {

    @Id
    @Column(name = "financialYearId")
    private Integer financialYearId;

    @Column(name = "financialYearFrom")
    private Date financialYearFrom;

    @Column(name = "financialYearTo")
    private Date financialYearTo;

    @Column(name = "status")
    private Character status;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "companyId")
    private Integer companyId;

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Date getFinancialYearFrom() {
        return financialYearFrom;
    }

    public void setFinancialYearFrom(Date financialYearFrom) {
        this.financialYearFrom = financialYearFrom;
    }

    public Date getFinancialYearTo() {
        return financialYearTo;
    }

    public void setFinancialYearTo(Date financialYearTo) {
        this.financialYearTo = financialYearTo;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
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

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
