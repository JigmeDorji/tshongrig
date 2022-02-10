/**
 * Component Name: Spare part Management
 * Name: CreateUserDao
 * Description: See the description at the top of class declaration
 * Project: G2C
 * @author:bikash.rai
 * Creation: 22-Apr-16
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016, private Limited
 */
package com.spms.mvc.dao;

import com.spms.mvc.dto.CreateUserDTO;
import com.spms.mvc.entity.CreateUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Project Name: spms
 * Description: <Replace description>
 * Date:11/28/2016
 * Year :2016
 *
 * @author: vcass
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:
 * Author:
 * Date:
 * Change Description:
 * Search Tag:
 */

@Repository("createUserDao")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CreateUserDao {
    //region private sessionFactory
    @Autowired
    SessionFactory spmsSessionFactory;
    //endregion

    //region public method

    /**
     * to get Status List
     *
     * @return List<ContactCreationDTO>
     */
    @Transactional(readOnly = true)
    public List<DropdownDTO> getStatusList() {
        String query = "SELECT CAST(StatusId AS CHAR(1)) AS value, StatusTitle AS text FROM tbl_systemstatus";
        Session session = spmsSessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean
                (DropdownDTO.class)).list();
    }

    /**
     * To check if the login id value already exists or not
     *
     * @param loginValue loginValue
     * @return Boolean
     */
    @Transactional(readOnly = true)
    public Boolean isLoginIdAlreadyExists(String loginValue) {
        String query = "SELECT COUNT(*) FROM tbl_users WHERE UserId =:loginValue";
        Session session = spmsSessionFactory.getCurrentSession();
        return !session.createSQLQuery(query).setParameter("loginValue", loginValue).uniqueResult().equals(BigInteger
                .ZERO);
    }

    /**
     * To get the list of existing users
     *
     * @return List<CreateUserDTO>
     */
    @Transactional(readOnly = true)
    public List<CreateUserDTO> getUserList() {
        String query = "SELECT UserId AS loginId, UserName AS txtUserName, UserCreatedDate AS createdDate, userStatus AS userStatus FROM tbl_users";
        Session session = spmsSessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(CreateUserDTO.class)).list();
    }

    /**
     * to save the new users
     *
     * @param createUser createUser
     */
    @Transactional
    public void saveUsers(CreateUser createUser) {
        spmsSessionFactory.getCurrentSession().saveOrUpdate(createUser);
    }

    /**
     * To get the user password
     *
     * @param loginId loginId
     * @return String
     */
    @Transactional(readOnly = true)
    public String getUserInfo(String loginId) {
        String query = "SELECT UserPassword FROM tbl_users WHERE UserId=:loginId";
        return (String) spmsSessionFactory.getCurrentSession().createSQLQuery(query).setParameter("loginId", loginId).uniqueResult();
    }

    /**
     * To update the user info
     *
     * @param createUser createUser
     */
    @Transactional
    public void updateUserInfo(CreateUser createUser) {
        spmsSessionFactory.getCurrentSession().saveOrUpdate(createUser);
    }

    /**
     * To get populate the grid value to fields
     *
     * @param loginId loginId
     * @return CreateUserDTO
     */
    @Transactional(readOnly = true)
    public CreateUserDTO getGridListToField(String loginId) {
        String query = "SELECT UserId AS loginId, UserName AS txtUserName,UserMobileNo AS userMobileNo,userType AS permissionType, UserCreatedDate AS createdDate, userStatus AS userStatus FROM tbl_users WHERE UserId =:loginId";
        return (CreateUserDTO) spmsSessionFactory.getCurrentSession()
                .createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(CreateUserDTO.class))
                .setParameter("loginId", loginId)
                .uniqueResult();
    }

    @Transactional
    public void deleteUser(String userId) {
        String query = "DELETE FROM tbl_users  WHERE UserId=:userId";
        spmsSessionFactory.getCurrentSession()
                .createSQLQuery(query).setParameter("userId", userId).executeUpdate();
    }
    //endregion


}
