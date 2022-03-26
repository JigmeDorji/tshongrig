


/**
 * Component Name: Spare part management
 * Name: CurrentUser
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 *
 * @author: bikash.rai
 * Creation: 29-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.library.helper;

import java.math.BigInteger;
import java.util.Date;

public class CurrentUser {

    //region private fields
    private String loginId;
    private String txtUserName;
    private Integer companyId;
    private Integer financialYearId;
    private String financialYearFrom;
    private String financialYearTo;
    private String companyName;
    private String companyAdd;
    private String email;
    private String contact;
    private Date createdDate;
    private Character userStatus;
    private Character permissionType;
    private Integer businessType;
    private String mailingAddress;
    private BigInteger userId;
    private Integer userRoleTypeId;
    //endregion

    //region public setter and getter

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getTxtUserName() {
        return txtUserName;
    }

    public void setTxtUserName(String txtUserName) {
        this.txtUserName = txtUserName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getFinancialYearId() {
        return financialYearId;
    }

    public void setFinancialYearId(Integer financialYearId) {
        this.financialYearId = financialYearId;
    }

    public String getFinancialYearFrom() {
        return financialYearFrom;
    }

    public void setFinancialYearFrom(String financialYearFrom) {
        this.financialYearFrom = financialYearFrom;
    }

    public String getFinancialYearTo() {
        return financialYearTo;
    }

    public void setFinancialYearTo(String financialYearTo) {
        this.financialYearTo = financialYearTo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAdd() {
        return companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = new Date();
    }

    public Character getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Character userStatus) {
        this.userStatus = userStatus;
    }

    public Character getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(Character permissionType) {
        this.permissionType = permissionType;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public Integer getUserRoleTypeId() {
        return userRoleTypeId;
    }

    public void setUserRoleTypeId(Integer userRoleTypeId) {
        this.userRoleTypeId = userRoleTypeId;
    }

    //endregion
}
