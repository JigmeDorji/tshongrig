package com.spms.mvc.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
public class UserDTO {
    //region private variables
    private BigInteger userId;

    private String username;

    private String userFullName;

    private String userMobileNo;

    private String saltValue;

    private String userPassword;

    private String txtConfirmPassword;

    private Character userStatus;

    private Integer userRoleTypeId;

    private String updatedBy;

    private Date updatedDate;

    private String createdBy;

    private Date createdDate;

    private String userRoleTypeName;

    private String emailId;

    private BigInteger userAuditId;

    private Character cmdFlag;

    private String createdByFullName;

    private String updatedByFullName;

    private String createdTime;

    private String updatedTime;

    private Integer companyId;
    private List<Integer> companyMappingId;


    //endregion

    //region setters and getters

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getTxtConfirmPassword() {
        return txtConfirmPassword;
    }

    public void setTxtConfirmPassword(String txtConfirmPassword) {
        this.txtConfirmPassword = txtConfirmPassword;
    }

    public Character getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Character userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserRoleTypeId() {
        return userRoleTypeId;
    }

    public void setUserRoleTypeId(Integer userRoleTypeId) {
        this.userRoleTypeId = userRoleTypeId;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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

    public String getUserRoleTypeName() {
        return userRoleTypeName;
    }

    public void setUserRoleTypeName(String userRoleTypeName) {
        this.userRoleTypeName = userRoleTypeName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public BigInteger getUserAuditId() {
        return userAuditId;
    }

    public void setUserAuditId(BigInteger userAuditId) {
        this.userAuditId = userAuditId;
    }

    public Character getCmdFlag() {
        return cmdFlag;
    }

    public void setCmdFlag(Character cmdFlag) {
        this.cmdFlag = cmdFlag;
    }

    public String getCreatedByFullName() {
        return createdByFullName;
    }

    public void setCreatedByFullName(String createdByFullName) {
        this.createdByFullName = createdByFullName;
    }

    public String getUpdatedByFullName() {
        return updatedByFullName;
    }

    public void setUpdatedByFullName(String updatedByFullName) {
        this.updatedByFullName = updatedByFullName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public List<Integer> getCompanyMappingId() {
        return companyMappingId;
    }

    public void setCompanyMappingId(List<Integer> companyMappingId) {
        this.companyMappingId = companyMappingId;
    }

    //endregion
}
