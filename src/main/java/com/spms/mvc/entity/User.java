
package com.spms.mvc.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
@Entity
@Table(name = "tbl_user")
public class User implements Serializable {

    private static final long serialVersionUID = -723583058586873479L;
    //region private column fields
    @Id
    @NotNull
    @Column(name = "userId")
    private BigInteger userId;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "userFullName")
    private String userFullName;

    @Column(name = "userMobileNo")
    private String userMobileNo;

    @NotNull
    @Column(name = "userPassword")
    private String userPassword;

    @NotNull
    @Column(name = "saltValue")
    private String saltValue;

    @NotNull
    @Column(name = "userStatus")
    private Character userStatus;

    @Column(name = "userRoleTypeId")
    private Integer userRoleTypeId;

    @Column(name = "emailId")
    private String emailId;

    @Column(name = "companyId")
    private Integer companyId;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @NotNull
    @Column(name = "createdBy")
    private String createdBy;

    @NotNull
    @Column(name = "createdDate")
    private Date createdDate;
    //endregion

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
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

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
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


    //endregion
}
