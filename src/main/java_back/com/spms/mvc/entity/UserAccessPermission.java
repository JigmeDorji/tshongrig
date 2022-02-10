package com.spms.mvc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
@Entity
@Table(name = "tbl_useraccesspermission")
public class UserAccessPermission {
    @Id
    @Column(name = "userAccessPermissionId")
    private BigInteger userAccessPermissionId;

    @Column(name = "userRoleTypeId")
    private Integer userRoleTypeId;

    @Column(name = "screenId")
    private Integer screenId;

    @Column(name = "isScreenAccessAllowed")
    private Character isScreenAccessAllowed;

    @Column(name = "isEditAccessAllowed")
    private Character isEditAccessAllowed;

    @Column(name = "isDeleteAccessAllowed")
    private Character isDeleteAccessAllowed;

    @Column(name = "isSaveAccessAllowed")
    private Character isSaveAccessAllowed;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "createdDate")
    private Date createdDate;

    public BigInteger getUserAccessPermissionId() {
        return userAccessPermissionId;
    }

    public void setUserAccessPermissionId(BigInteger userAccessPermissionId) {
        this.userAccessPermissionId = userAccessPermissionId;
    }

    public Integer getUserRoleTypeId() {
        return userRoleTypeId;
    }

    public void setUserRoleTypeId(Integer userRoleTypeId) {
        this.userRoleTypeId = userRoleTypeId;
    }

    public Integer getScreenId() {
        return screenId;
    }

    public void setScreenId(Integer screenId) {
        this.screenId = screenId;
    }

    public Character getIsScreenAccessAllowed() {
        return isScreenAccessAllowed;
    }

    public void setIsScreenAccessAllowed(Character isScreenAccessAllowed) {
        this.isScreenAccessAllowed = isScreenAccessAllowed;
    }

    public Character getIsEditAccessAllowed() {
        return isEditAccessAllowed;
    }

    public void setIsEditAccessAllowed(Character isEditAccessAllowed) {
        this.isEditAccessAllowed = isEditAccessAllowed;
    }

    public Character getIsDeleteAccessAllowed() {
        return isDeleteAccessAllowed;
    }

    public void setIsDeleteAccessAllowed(Character isDeleteAccessAllowed) {
        this.isDeleteAccessAllowed = isDeleteAccessAllowed;
    }

    public Character getIsSaveAccessAllowed() {
        return isSaveAccessAllowed;
    }

    public void setIsSaveAccessAllowed(Character isSaveAccessAllowed) {
        this.isSaveAccessAllowed = isSaveAccessAllowed;
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
}
