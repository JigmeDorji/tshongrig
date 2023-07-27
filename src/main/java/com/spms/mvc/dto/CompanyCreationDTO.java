package com.spms.mvc.dto;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/3/2019.
 */
public class CompanyCreationDTO {

    private Integer companyId;

    private String companyName;

    private String mailingAddress;

    private String mobileNo;

    private String email;

    private String website;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fnYrStart;

    private Integer businessType;
    private Character status;
    private String contactPerson;
    private String remarks;

    private Double totalSale;
    private BigDecimal pfPercentage;
    private List<Double> totalListSale;

    private Date saleDate;
    private Date trialExpiryDate;
    private List<Date> saleListDate;
    private String loginId;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getFnYrStart() {
        return fnYrStart;
    }

    public void setFnYrStart(Date fnYrStart) {
        this.fnYrStart = fnYrStart;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Double getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Double totalSale) {
        this.totalSale = totalSale;
    }

    public List<Double> getTotalListSale() {
        return totalListSale;
    }

    public void setTotalListSale(List<Double> totalListSale) {
        this.totalListSale = totalListSale;
    }

    public Date getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    public List<Date> getSaleListDate() {
        return saleListDate;
    }

    public void setSaleListDate(List<Date> saleListDate) {
        this.saleListDate = saleListDate;
    }

    public BigDecimal getPfPercentage() {
        return pfPercentage;
    }

    public void setPfPercentage(BigDecimal pfPercentage) {
        this.pfPercentage = pfPercentage;
    }

    public Character getStatus() {
        return status;
    }

    public void setStatus(Character status) {
        this.status = status;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getTrialExpiryDate() {
        return trialExpiryDate;
    }

    public void setTrialExpiryDate(Date trialExpiryDate) {
        this.trialExpiryDate = trialExpiryDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public String toString() {
        return "CompanyCreationDTO{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", mailingAddress='" + mailingAddress + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", fnYrStart=" + fnYrStart +
                ", businessType=" + businessType +
                ", status=" + status +
                ", contactPerson='" + contactPerson + '\'' +
                ", remarks='" + remarks + '\'' +
                ", totalSale=" + totalSale +
                ", pfPercentage=" + pfPercentage +
                ", totalListSale=" + totalListSale +
                ", saleDate=" + saleDate +
                ", trialExpiryDate=" + trialExpiryDate +
                ", saleListDate=" + saleListDate +
                ", loginId='" + loginId + '\'' +
                '}';
    }
}
