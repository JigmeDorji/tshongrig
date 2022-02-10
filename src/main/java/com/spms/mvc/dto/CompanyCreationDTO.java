package com.spms.mvc.dto;

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

    private Date fnYrStart;

    private Integer businessType;

    private Double totalSale;
    private BigDecimal pfPercentage;
    private List<Double> totalListSale;

    private Date saleDate;
    private List<Date> saleListDate;

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
}
