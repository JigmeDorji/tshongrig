package com.spms.mvc.service;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_create_company")
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
    private Integer mobileNo;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "fnYrStart")
    private Date fnYrStart;

    @Column(name = "bookYrStart")
    private Date bookYrStart;

    @Column(name = "businessType")
    private Integer businessType;

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

    public Integer getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(Integer mobileNo) {
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

    public Date getBookYrStart() {
        return bookYrStart;
    }

    public void setBookYrStart(Date bookYrStart) {
        this.bookYrStart = bookYrStart;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }
}