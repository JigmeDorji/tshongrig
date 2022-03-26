package com.spms.mvc.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_common_company")
public class CompanyCreation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "mailingAddress")
    private String mailingAddress;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "fnYrStart")
    private Date fnYrStart;

    @Column(name = "businessType")
    private Integer businessType;

    @Column(name = "pfPercentage")
    private BigDecimal pfPercentage;

    @Column(name = "status")
    private Character status;


    @Column(name = "contactPerson")
    private String contactPerson;


    @Column(name = "remarks")
    private String remarks;

    @Column(name = "trialExpiryDate")
    private Date trialExpiryDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}