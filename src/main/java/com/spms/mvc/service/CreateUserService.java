/**
 * Component Name: Spare part management
 * Name: CreateUserService
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 * @author: bikash.rai
 * Creation: 22-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.service;

import com.spms.mvc.dao.CreateUserDao;
import com.spms.mvc.dto.CreateUserDTO;
import com.spms.mvc.entity.CreateUser;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("CreateUserService")
@PreAuthorize("isAuthenticated()")
public class CreateUserService {
    //region private dao
    @Autowired
    private CreateUserDao createUserDao;
    //endregion
    //region private helper class
    private BCryptPasswordEncoder passwordEncoder;
    //endregion

    //region setter helper class
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    //endregion


    //region public method

    /**
     * To get the list of status
     *
     * @return List<DropdownDTO>
     */
    public List<DropdownDTO> getStatusList() {
        return createUserDao.getStatusList();
    }

    /**
     * To validate if login ID already exists or not
     *
     * @param loginValue loginValue
     * @return ResponseMessage
     */
    public ResponseMessage isLoginIdAlreadyExists(String loginValue) {
        ResponseMessage responseMessage = new ResponseMessage();

        if (createUserDao.isLoginIdAlreadyExists(loginValue)) {
            responseMessage.setStatus(0);
            responseMessage.setText("Similar login ID already exists.");
        }
        return responseMessage;
    }

    /**
     * To get the list of existing users
     *
     * @return
     */
    public List<CreateUserDTO> getUserList() {
        return createUserDao.getUserList();
    }

    /**
     * Method to save users into database
     *
     * @param createUserDTO createUserDTO
     * @param currentUser   currentUser
     * @return ResponseMessage
     */
    public ResponseMessage saveUsers(CreateUserDTO createUserDTO, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        Boolean isLoginIdExists = createUserDao.isLoginIdAlreadyExists(createUserDTO.getLoginId());
        CreateUser createUser = new CreateUser();
        try {
            if (!isLoginIdExists) {
                if (createUserDTO.getTxtPassword().equals("")) {
                    responseMessage.setStatus(0);
                    responseMessage.setText("Password cannot be null");
                } else {
                    createUser.setLoginId(createUserDTO.getLoginId());
                    createUser.setTxtUserName(createUserDTO.getTxtUserName());
                    createUser.setTxtPassword(passwordEncoder.encode(createUserDTO.getTxtPassword()));
                    createUser.setCreatedDate(new Date());
                    createUser.setUserMobileNo(createUserDTO.getUserMobileNo());
                    createUser.setUserStatus('A');
                    createUser.setUserType(createUserDTO.getPermissionType());
                    createUserDao.saveUsers(createUser);
                    responseMessage.setStatus(1);
                    responseMessage.setText("User is created successfully.");
                }

            } else {
                String userInfoPWD = createUserDTO.getTxtPassword();
                String userInfoCPWD = createUserDTO.getTxtConfirmPassword();
                if (userInfoPWD.equals("") && userInfoCPWD.equals("")) {
                    String oldPWD = createUserDao.getUserInfo(createUserDTO.getLoginId());
                    createUser.setTxtPassword(oldPWD);
                } else {
                    createUser.setTxtPassword(passwordEncoder.encode(createUserDTO.getTxtPassword()));
                }
                createUser.setLoginId(createUserDTO.getLoginId());
                createUser.setTxtUserName(createUserDTO.getTxtUserName());
                createUser.setCreatedDate(createUserDTO.getCreatedDate());
                createUser.setUserStatus('A');
                createUser.setCreatedDate(createUserDTO.getCreatedDate());
                createUser.setUserMobileNo(createUserDTO.getUserMobileNo());
                createUser.setUpdatedDate(new Date());
                createUser.setUpdatedBy(currentUser.getTxtUserName());
                createUser.setUserType(createUserDTO.getPermissionType());

                createUserDao.updateUserInfo(createUser);
                responseMessage.setText("User information updated successfully.");
                responseMessage.setStatus(1);
            }

        } catch (Exception ex) {
            responseMessage.setText("Application Error.");
            responseMessage.setStatus(0);
            ex.printStackTrace();
        }
        return responseMessage;
    }

    /**
     * To get the grid value to fields
     *
     * @param loginId loginId
     * @return ResponseMessage
     */
    public ResponseMessage getGridListToField(String loginId) {
        ResponseMessage responseMessage = new ResponseMessage();

        CreateUserDTO createUserDTO = createUserDao.getGridListToField(loginId);

        if (createUserDTO == null) {
            responseMessage.setStatus(0);
            responseMessage.setText("Unable to fetch data");
        } else {
            responseMessage.setStatus(1);
            responseMessage.setDTO(createUserDTO);
        }

        return responseMessage;
    }

    public ResponseMessage deleteUser(String userId) {
        createUserDao.deleteUser(userId);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(0);
        responseMessage.setText("User has been deleted successfully");
        return responseMessage;
    }
    //endregion

}
