/**
 * Name: ChangePasswordDao
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 * @author:bikash.rai
 * Creation: 27-Apr-16
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016, private Limited
 */
package com.spms.mvc.dao;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Project Name: Spare part management system
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

@Repository
@SuppressWarnings({"unchecked", "rawtypes"})
public class ChangePasswordDao {
    //region private sessionFactory
    @Autowired
    SessionFactory sessionFactory;
    //endregion

    //region public method

    /**
     * to get the old credentials
     *
     * @param userId userId
     * @return String
     */
    @Transactional(readOnly = true)
    public String getOldCredentials(String userId) {
        String sqlQry = "SELECT UserPassword FROM tbl_user WHERE username =:userId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (String) hibernate.setParameter("userId",userId).uniqueResult();
//        String query = "SELECT UserPassword FROM tbl_user WHERE UserId =:userId";
//        Session session = sessionFactory.getCurrentSession();
//        return session.createSQLQuery(query).setParameter("userId", userId).uniqueResult().toString();
    }

    /**
     * update system parameter table for application ID
     *
     * @param newPassword newPassword
     * @param userId      userId
     */
    @Transactional
    public void updateUserPassword(String newPassword, String userId) {
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery("UPDATE tbl_user SET userPassword =:newPassword WHERE username =:userId")
                .setParameter("newPassword", newPassword)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public String getSaltValue(String userId) {
        String sqlQry = "SELECT saltValue FROM tbl_user WHERE username =:userId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (String) hibernate.setParameter("userId",userId).uniqueResult();
    }
    //endregion
}
