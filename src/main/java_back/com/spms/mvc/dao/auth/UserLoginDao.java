package com.spms.mvc.dao.auth;

import com.spms.mvc.dto.UserDTO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
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
 **/
@Repository("userLoginDao")
public class UserLoginDao {
    //region private sessionFactory
    @Autowired
    SessionFactory spmsSessionFactory;
    //endregion
    //region private variables
    private Query hQuery;
    //endregion

    //region public method

    /**
     * to get the user information while logging in.
     *
     * @param username username
     * @param companyId
     * @return List<CreateUsersDTO>
     */
    @Transactional(readOnly = true)
    public UserDTO login(String username, Integer companyId) {


        String query = "\n" +
                "select A.userId, \n" +
                "A.username, A.saltValue, \n" +
                "A.userFullName, A.userMobileNo, \n" +
                "A.userPassword, A.createdDate, \n" +
                "A.updatedDate, A.updatedBy, \n" +
                "A.userStatus, A.userRoleTypeId, \n" +
                "A.emailId, B.userRoleTypeName,\n" +
                "C.companyId\n" +
                "from tbl_user A inner join tbl_user_role_type B on A.userRoleTypeId=B.userRoleTypeId \n" +
                "left join tbl_company_mapping C ON A.userId=C.userId\n" +
                "where A.username =:username AND C.companyId=:companyId\n" +
                "UNION\n" +
                "select A.userId, \n" +
                "A.username, A.saltValue, \n" +
                "A.userFullName, A.userMobileNo, \n" +
                "A.userPassword, A.createdDate, \n" +
                "A.updatedDate, A.updatedBy, \n" +
                "A.userStatus, A.userRoleTypeId, \n" +
                "A.emailId, B.userRoleTypeName,\n" +
                "A.companyId\n" +
                "from tbl_user A inner join tbl_user_role_type B on A.userRoleTypeId=B.userRoleTypeId \n" +
                "where A.username =:username AND A.companyId=:companyId";
        Session session = spmsSessionFactory.getCurrentSession();
        return (UserDTO)session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(UserDTO.class))
                .setParameter("username",username)
                .setParameter("companyId",companyId)
                .uniqueResult();
    }
    //endregion
}
