/**
 * Component Name: Spare part management
 * Name: UserLoginService
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 *
 * @author: bikash.rai
 * Creation: 04-May-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.service.auth;

import com.spms.mvc.dao.auth.UserLoginDao;
import com.spms.mvc.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userLoginService")
public class UserLoginService {
    //region private dao
    @Autowired
    UserLoginDao userLoginDao;
    //endregion

    //region public method

    /**
     * to get user information while logging in.
     *
     * @param username username
     * @param companyId
     * @return CreateUserDTO
     */
    public UserDTO login(String username, Integer companyId) {
        return userLoginDao.login(username, companyId);
    }

}
