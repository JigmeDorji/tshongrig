/**
 * Component Name: Spare part management
 * Name: CreateUser
 * Description: See the description at the top of class declaration
 * Project: Ascend Financial Solution
 * @author: bikash.rai
 * Creation: 25-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.entity;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "tbl_users")
public class CreateUser implements Serializable {

    private static final long serialVersionUID = -723583058586873479L;
    //region private column fields
    @Id
    @NotNull
    @Column(name = "UserId")
    private String loginId;

    @NotNull
    @Column(name = "UserName")
    private String txtUserName;

    @Column(name = "UserMobileNo")
    private String userMobileNo;

    @NotNull
    @Column(name = "UserPassword")
    private String txtPassword;

    @NotNull
    @Column(name = "UserCreatedDate")
    private Date createdDate;

    @NotNull
    @Column(name = "userStatus")
    private Character userStatus;

    @Column(name = "UserUpdatedDate")
    private Date updatedDate;

    @Column(name = "UserUpdatedBy")
    private String updatedBy;

    @Column(name = "userType")
    private Character userType;

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

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    public void setUserStatus(Character userStatus) {
        this.userStatus = userStatus;
    }

    public Character getUserStatus() {
        return userStatus;
    }

    public String getUserMobileNo() {
        return userMobileNo;
    }

    public void setUserMobileNo(String userMobileNo) {
        this.userMobileNo = userMobileNo;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Character getUserType() {
        return userType;
    }

    public void setUserType(Character userType) {
        this.userType = userType;
    }

    //endregion
}
