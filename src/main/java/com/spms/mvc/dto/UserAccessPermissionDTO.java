package com.spms.mvc.dto;

import java.util.List;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
public class UserAccessPermissionDTO {

    private Integer userRoleTypeId;

    private List<UserAccessPermissionListDTO> userAccessPermissionListDTO;

    public Integer getUserRoleTypeId() {
        return userRoleTypeId;
    }

    public void setUserRoleTypeId(Integer userRoleTypeId) {
        this.userRoleTypeId = userRoleTypeId;
    }

    public List<UserAccessPermissionListDTO> getUserAccessPermissionListDTO() {
        return userAccessPermissionListDTO;
    }

    public void setUserAccessPermissionListDTO(List<UserAccessPermissionListDTO> userAccessPermissionListDTO) {
        this.userAccessPermissionListDTO = userAccessPermissionListDTO;
    }
}
