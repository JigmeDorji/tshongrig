package com.spms.mvc.service.auth; /**
 * Name: UserService
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 *
 * @author: bikash.rai
 * Creation: 22-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */

import com.spms.mvc.Enumeration.CommonStatus;
import com.spms.mvc.Enumeration.SystemDataInt;
import com.spms.mvc.Enumeration.UserRoleType;
import com.spms.mvc.dao.auth.UserDao;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.entity.CompanyMapping;
import com.spms.mvc.entity.User;
import com.spms.mvc.entity.User_a;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import com.spms.mvc.library.helper.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service()
@PreAuthorize("isAuthenticated()")
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    //region public method

    /**
     * To get the list of status
     *
     * @return List<DropdownDTO>
     */
    public List<DropdownDTO> getStatusList() {
        List<DropdownDTO> dropdownDTOs = new ArrayList<>();

        DropdownDTO dropdownDTOActiveStatus = new DropdownDTO();
        dropdownDTOActiveStatus.setValueChar(CommonStatus.Active.getValue());
        dropdownDTOActiveStatus.setText(CommonStatus.Active.getText());
        dropdownDTOs.add(dropdownDTOActiveStatus);

        DropdownDTO dropdownDTOInActiveStatus = new DropdownDTO();
        dropdownDTOInActiveStatus.setValueChar(CommonStatus.Inactive.getValue());
        dropdownDTOInActiveStatus.setText(CommonStatus.Inactive.getText());
        dropdownDTOs.add(dropdownDTOInActiveStatus);

        return dropdownDTOs;
    }

    /**
     * To validate if login ID already exists or not
     *
     * @param username username
     * @return ResponseMessage
     */
    public ResponseMessage isLoginIdAlreadyExists(String username, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        username = username.concat("@").concat(currentUser.getCompanyName().replaceAll("\\B.|\\P{L}", "").toUpperCase());

        //check for other user
        if (!userDao.isLoginIdAlreadyExists(username)) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText(username + " already exist in the system. Please choose different username.");
        }

        //check for other user
        if (!userDao.chekAdminUserExist(username, currentUser.getCompanyId())) {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            responseMessage.setText(username + " already exist in the system. Please choose different username.");
        }
        return responseMessage;
    }

    /**
     * To get the list of existing users
     *
     * @return
     */
    public List<UserDTO> getUserList(Integer companyId, CurrentUser currentUser) {
//        if(currentUser.getUserRoleTypeId().equals(UserRoleType.Owner.getValue())){
//            return  userDao.getOwnerUserList(companyId);
//        }else {
            return userDao.getUserList(companyId);
//        }

    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvxyz0123456789";

    public static String generatePassword(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public static String generateSaltValue(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    /**
     * Method to save users into database
     *
     * @param userDTO     userDTO
     * @param currentUser currentUser
     * @return ResponseMessage
     */
//    @Transactional(rollbackFor = Exception.class)
    public ResponseMessage addUser(UserDTO userDTO, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        User user = new User();
        BigInteger userId;

        if (UserRoleType.Administrator.getValue().equals(userDTO.getUserRoleTypeId())) {
            if (userDTO.getUserId() == null) {
                if (!userDao.checkIsAdministratorExists(UserRoleType.Administrator.getValue(), currentUser.getCompanyId())) {
                    responseMessage.setText("Cannot create another administrator.");
                    responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
                    return responseMessage;
                }
            }
        }

        try {
            String username=userDTO.getUsername().concat("@").concat(currentUser.getCompanyName().replaceAll("\\B.|\\P{L}", "").toUpperCase());
            user.setUserFullName(userDTO.getUserFullName());
            if (userDTO.getUserId() == null) {
                String saltValue = generateSaltValue(6);
                if (userDTO.getUserPassword().equals("")) {
                    userDTO.setUserPassword(generatePassword(6));//system generate 6 digits password
                }
                BigInteger lastUserId = userDao.getLastUserId();
                lastUserId = lastUserId == null ? BigInteger.ONE : lastUserId.add(BigInteger.ONE);
                user.setUserId(lastUserId);
                user.setUsername(username);
                user.setSaltValue(saltValue);
                user.setUserPassword(passwordEncoder.encode(saltValue + userDTO.getUserPassword().trim()));
                user.setUserMobileNo(userDTO.getUserMobileNo());
                user.setUserStatus(userDTO.getUserStatus());
                user.setUserRoleTypeId(userDTO.getUserRoleTypeId());
                user.setEmailId(userDTO.getEmailId());
                user.setCompanyId(currentUser.getCompanyId());
                user.setUpdatedBy(null);
                user.setUpdatedDate(null);
                user.setCreatedBy(currentUser.getLoginId());
                user.setCreatedDate(new Date());
                userId = userDao.addUser(user);
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
                responseMessage.setText("User added successfully.");
            } else {
                String userInfoPWD = userDTO.getUserPassword();
                UserDTO userDTOdB = userDao.getUserDetail(userDTO.getUsername(), currentUser.getCompanyId());
                if (userInfoPWD.equals("")) {
                    user.setUserPassword(userDao.getOldPassword(userDTO.getUserId()));
                    user.setSaltValue(userDTOdB.getSaltValue());
                } else {
                    String saltValue = generateSaltValue(6);
                    String newPassword = userDTO.getUserPassword();
                    user.setUserPassword(passwordEncoder.encode(saltValue + newPassword));
                    user.setSaltValue(saltValue);
                }
                user.setUserId(userDTO.getUserId());
                user.setUsername(userDTO.getUsername());
                user.setUserMobileNo(userDTO.getUserMobileNo());
                user.setUserStatus(userDTO.getUserStatus());
                user.setEmailId(userDTO.getEmailId());
                user.setUserRoleTypeId(userDTO.getUserRoleTypeId());
                user.setCreatedBy(userDTOdB.getCreatedBy());
                user.setCreatedDate(userDTOdB.getCreatedDate());
                user.setUpdatedBy(currentUser.getLoginId());
                user.setCompanyId(currentUser.getCompanyId());
                user.setUpdatedDate(new Date());
                userDao.updateUserInfo(user);
                responseMessage.setText("User updated successfully.");
                responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
                userId = userDTO.getUserId();
            }

            //Only applies to administrative role
            saveUserWiseCompanyMapping(userDTO, userId);
        } catch (Exception ex) {
            responseMessage.setText("Application Error.");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
            ex.printStackTrace();
        }
        return responseMessage;
    }

    public void saveUserWiseCompanyMapping(UserDTO userDTO, BigInteger userId) {
        if (userDTO.getCompanyMappingId() != null) {

            if (userId == null) {
                userId = userDao.getUserIdByUserName(userDTO.getUsername());
            }
            for (Integer companyId : userDTO.getCompanyMappingId()) {
                CompanyMapping companyMapping = new CompanyMapping();
                if (userDao.isCompanyMappedExists(userDTO.getUserId(), companyId)) {
                    companyMapping.setId(userDao.getMaxCurrentId() + 1);
                } else {
                    companyMapping.setId(userDao.getId(userDTO.getUserId(), companyId));
                }
                companyMapping.setCompanyId(companyId);
                companyMapping.setUserId(userId);
                userDao.saveCompanyMapping(companyMapping);
            }
        }
    }


    /**
     * To get the grid value to fields
     *
     * @param username    username
     * @param currentUser
     * @return ResponseMessage
     */

    public ResponseMessage getUserDetail(String username, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        UserDTO userDTO = userDao.getUserDetail(username, currentUser.getCompanyId());
        if (userDTO == null) {
            responseMessage.setText("Data not available");
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        } else {
            responseMessage.setDTO(userDTO);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }
        return responseMessage;
    }


    public ResponseMessage deleteUser(Integer companyId, BigInteger userId, CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();

        userDao.deleteUser(userId);
        if (companyId == null) {
            userDao.deleteMappedCompanyByUserId(userId);
        }
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("User has been deleted successfully.");
        return responseMessage;
    }
    //endregion


    public User_a convertAuditDtoToEntity(User user, Character cmdFlag) {
        User_a user_a = new User_a();
        BigInteger lastAuditId = userDao.getLastAuditId();
        lastAuditId = lastAuditId == null ? BigInteger.ONE : lastAuditId.add(BigInteger.ONE);
        user_a.setUserAuditId(lastAuditId);
        user_a.setCmdFlag(cmdFlag);
        user_a.setUserId(user.getUserId());
        user_a.setUsername(user.getUsername());
        user_a.setSaltValue(user.getSaltValue());
        user_a.setUserPassword(user.getUserPassword());
        user_a.setUserFullName(user.getUserFullName());
        user_a.setUserMobileNo(user.getUserMobileNo());
        user_a.setUserStatus(user.getUserStatus());
        user_a.setEmailId(user.getEmailId());
        user_a.setUserRoleTypeId(user.getUserRoleTypeId());
        user_a.setCreatedBy(user.getCreatedBy());
        user_a.setCreatedDate(user.getCreatedDate());
        user_a.setUpdatedBy(user.getUpdatedBy());
        user_a.setUpdatedDate(user.getUpdatedDate());
        return user_a;
    }

    public Boolean isCompanyMappedAlready(BigInteger userId, Integer companyId) {
        return userDao.isCompanyMappedExists(userId, companyId);
    }

    public ResponseMessage deleteMappedCompany(BigInteger userId, Integer companyId) {
        ResponseMessage responseMessage = new ResponseMessage();
        userDao.deleteMappedCompany(userId, companyId);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        responseMessage.setText("User has been deleted successfully.");
        return responseMessage;
    }
    //endregion
}
