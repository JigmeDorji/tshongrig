package com.spms.mvc.dto;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by Bikash Rai on 5/5/2020.
 */
public class FinancialYearDTO {


    private Character status;

    private Date financialYearTo;

    private Date financialYearFrom;

    private Integer financialYearId;
    private String createdBy;

    private Date createdDate;
    private Integer companyId;


    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public Date getFinancialYearTo() {
        return financialYearTo;
    }

    public void setFinancialYearTo(Date financialYearTo) {
        this.financialYearTo = financialYearTo;
    }

    public Date getFinancialYearFrom() {
        return financialYearFrom;
    }

    public void setFinancialYearFrom(Date financialYearFrom) {
        this.financialYearFrom = financialYearFrom;
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
