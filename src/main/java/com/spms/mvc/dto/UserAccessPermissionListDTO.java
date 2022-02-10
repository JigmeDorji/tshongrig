package com.spms.mvc.dto;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
public class UserAccessPermissionListDTO {
    //region private variables
    private BigInteger userAccessPermissionId;
    private Integer userRoleTypeId;
    private Integer screenId;
    private Character isScreenAccessAllowed;
    private Character isEditAccessAllowed;
    private Character isDeleteAccessAllowed;
    private Character isSaveAccessAllowed;
    private String updatedBy;
    private Date updatedDate;
    private String createdBy;
    private Date createdDate;
    private String screenName;
    //endregion

    //region setters and getters
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

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }
    //endregion
}
