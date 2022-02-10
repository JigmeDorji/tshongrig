package com.spms.mvc.service.auth;

import com.spms.mvc.dao.UserAccessPermissionDao;
import com.spms.mvc.dto.UserAccessPermissionDTO;
import com.spms.mvc.dto.UserAccessPermissionListDTO;
import com.spms.mvc.entity.UserAccessPermission;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
@Service("userAccessPermissionService")
public class UserAccessPermissionService {

    @Autowired
    private UserAccessPermissionDao userAccessPermissionDao;


    public List<DropdownDTO> getUserRoleList() {
        return userAccessPermissionDao.getUserRoleList();
    }

    public List<UserAccessPermissionListDTO> getScreenList(Integer userRoleTypeId) {

        List<UserAccessPermissionListDTO> userAccessPermissionListDTOList;
        if (userAccessPermissionDao.isUserRoleAssigned(userRoleTypeId)) {
            userAccessPermissionListDTOList = userAccessPermissionDao.getScreenList(userRoleTypeId);
        } else {
            userAccessPermissionListDTOList = userAccessPermissionDao.getUnScreenList();
        }
        return userAccessPermissionListDTOList;
    }

    public ResponseMessage save(UserAccessPermissionDTO userAccessPermissionDTO, CurrentUser currentUser) {
        UserAccessPermission userAccessPermission = new UserAccessPermission();
        ResponseMessage responseMessage = new ResponseMessage();
        BigInteger userAccessPermissionIdSerial = userAccessPermissionDao.getUserAccessPermissionIdSerial();
        userAccessPermissionIdSerial = userAccessPermissionIdSerial == null ? BigInteger.ONE : userAccessPermissionIdSerial;
        for (UserAccessPermissionListDTO userAccessPermissionListDTO : userAccessPermissionDTO.getUserAccessPermissionListDTO()) {
            userAccessPermission.setScreenId(userAccessPermissionListDTO.getScreenId());
            userAccessPermission.setUserRoleTypeId(userAccessPermissionDTO.getUserRoleTypeId());
            userAccessPermission.setIsScreenAccessAllowed(userAccessPermissionListDTO.getIsScreenAccessAllowed());
            userAccessPermission.setIsEditAccessAllowed(userAccessPermissionListDTO.getIsEditAccessAllowed());
            userAccessPermission.setIsDeleteAccessAllowed(userAccessPermissionListDTO.getIsDeleteAccessAllowed());
            userAccessPermission.setIsSaveAccessAllowed(userAccessPermissionListDTO.getIsSaveAccessAllowed());
            userAccessPermission.setCreatedBy(currentUser.getTxtUserName());
            userAccessPermission.setCreatedDate(currentUser.getCreatedDate());
            if (userAccessPermissionListDTO.getUserAccessPermissionId() != null) {
                userAccessPermission.setUserAccessPermissionId(userAccessPermissionListDTO.getUserAccessPermissionId());
                userAccessPermissionDao.update(userAccessPermission);
            } else {
                userAccessPermission.setUserAccessPermissionId(userAccessPermissionIdSerial.add(BigInteger.ONE));
                userAccessPermissionIdSerial = userAccessPermissionIdSerial.add(BigInteger.ONE);
                userAccessPermissionDao.save(userAccessPermission);
            }
        }
        responseMessage.setStatus(1);
        responseMessage.setText("Data saved successfully.");
        return responseMessage;
    }

    public List<UserAccessPermissionListDTO> getUserAccessPermissionDetails(Integer roleTypeId) {
        return userAccessPermissionDao.getScreenList(roleTypeId);
    }
}

