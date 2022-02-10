/**
 * Component Name: Spare part management
 * Name: ChangePasswordService
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 * @author:bikash.rai
 * Creation: 27-Apr-16
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.service;

import com.spms.mvc.dao.ChangePasswordDao;
import com.spms.mvc.dto.ChangePasswordDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("changePasswordService")
public class ChangePasswordService {
    //region private dao
    @Autowired
    private ChangePasswordDao changePasswordDao;

    private BCryptPasswordEncoder passwordEncoder;
    //endregion

    //region setter
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    //endregion

    //region public method

    /**
     * save method.
     *
     * @param changePasswordDTO changePasswordDTO
     * @return ResponseMessage
     */
    public ResponseMessage save(ChangePasswordDTO changePasswordDTO) {
        ResponseMessage responseMessage = new ResponseMessage();

        String saltValue=changePasswordDao.getSaltValue(changePasswordDTO.getUserId());

        String oldPWD = changePasswordDao.getOldCredentials(changePasswordDTO.getUserId());

        if (passwordEncoder.matches(saltValue+changePasswordDTO.getOldPassword(), oldPWD)) {
            changePasswordDao.updateUserPassword(passwordEncoder.encode(saltValue+changePasswordDTO.getNewPassword()),
                    changePasswordDTO.getUserId());
            responseMessage.setStatus(1);
            responseMessage.setText("Password changed successfully.");
        } else {
            responseMessage.setStatus(0);
            responseMessage.setText("Invalid password.");
        }

        return responseMessage;
    }
    //endregion

}
