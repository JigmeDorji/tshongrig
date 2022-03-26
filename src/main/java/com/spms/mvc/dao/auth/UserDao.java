package com.spms.mvc.dao.auth;

import com.spms.mvc.dao.BaseDao;
import com.spms.mvc.dto.UserDTO;
import com.spms.mvc.entity.CompanyMapping;
import com.spms.mvc.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jigme.dorji on 23/04/2020.
 */
@Repository()
public class UserDao extends BaseDao {

    @Autowired
    SessionFactory sessionFactory;

    //region public methods
    @Transactional(readOnly = true)
    public Boolean isLoginIdAlreadyExists(String username) {
        String sqlQuery = "SELECT COUNT(*) FROM tbl_user WHERE username =:username";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("username", username)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    //region public methods
    @Transactional(readOnly = true)
    public Boolean chekAdminUserExist(String username, Integer companyId) {
        String sqlQuery = "select COUNT(*) FROM tbl_company_mapping a\n" +
                "INNER JOIN tbl_user b ON a.userId=b.userId where\n" +
                "a.companyId=:companyId and b.username=:username";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("username", username)
                .setParameter("companyId", companyId)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getUserList(Integer companyId) {
        String sqlQuery = "SELECT A.userId,A.username, A.userFullName, A.createdDate, \n" +
                "A.userStatus,A.userMobileNo, B.userRoleTypeName \n" +
                "FROM tbl_user A INNER JOIN tbl_user_role_type B \n" +
                "ON A.userRoleTypeId = B.userRoleTypeId WHERE A.companyId=:companyId and A.userRoleTypeId !=4\n" +
                "UNION \n" +
                "SELECT A.userId,A.username, A.userFullName, A.createdDate, \n" +
                "A.userStatus,A.userMobileNo, B.userRoleTypeName \n" +
                "FROM tbl_user A INNER JOIN tbl_user_role_type B ON A.userRoleTypeId = B.userRoleTypeId \n" +
                "INNER JOIN tbl_company_mapping C On A.userId=C.userId\n" +
                "WHERE C.companyId=:companyId and A.userRoleTypeId !=0";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public List<UserDTO> getOwnerUserList(Integer companyId) {
        String sqlQuery = "SELECT A.userId,A.username, A.userFullName, A.createdDate, \n" +
                "A.userStatus,A.userMobileNo, B.userRoleTypeName \n" +
                "FROM tbl_user A INNER JOIN tbl_user_role_type B \n" +
                "ON A.userRoleTypeId = B.userRoleTypeId WHERE A.companyId=:companyId and A.userRoleTypeId !=4\n" +
                "UNION \n" +
                "SELECT A.userId,A.username, A.userFullName, A.createdDate, \n" +
                "A.userStatus,A.userMobileNo, B.userRoleTypeName \n" +
                "FROM tbl_user A INNER JOIN tbl_user_role_type B ON A.userRoleTypeId = B.userRoleTypeId \n" +
                "INNER JOIN tbl_company_mapping C On A.userId=C.userId\n" +
                "WHERE C.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(UserDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public BigInteger getLastUserId() {
        String sqlQuery = "select max(u.userId) from tbl_user u";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(sqlQuery)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigInteger getLastAuditId() {
        String sqlQuery = "select max(u.userAuditId) from tbl_user_a u";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(sqlQuery)
                .uniqueResult();
    }

    @Transactional
    public BigInteger addUser(User user) {
        sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user.getUserId();
    }

    @Transactional(readOnly = true)
    public String getOldPassword(BigInteger userId) {

        String sqlQuery = "SELECT UserPassword FROM tbl_user WHERE userId =:userId";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(sqlQuery)
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Transactional
    public void updateUserInfo(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserDetail(String username, Integer companyId) {
        String sqlQuery = "SELECT userId, username, \n" +
                "userFullName, saltValue, \n" +
                "userMobileNo, userRoleTypeId, \n" +
                "userStatus, emailId ,updatedBy, updatedDate, createdBy, createdDate\n" +
                "FROM tbl_user WHERE username =:username AND companyId=:companyId\n" +
                "UNION\n" +
                "SELECT a.userId, username, \n" +
                "userFullName, saltValue, \n" +
                "userMobileNo, userRoleTypeId, \n" +
                "userStatus, emailId ,updatedBy, updatedDate, createdBy, createdDate\n" +
                "FROM tbl_user a\n" +
                "INNER JOIN tbl_company_mapping b ON a.userId=b.userId\n" +
                "WHERE username =:username AND b.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (UserDTO) session.createSQLQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(UserDTO.class))
                .setParameter("username", username)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Boolean isUsernameExists(String username) {
        String sqlQuery = "SELECT COUNT(*) FROM tbl_user WHERE username !=:username";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("username", username)
                .list() == null;
    }

    @Transactional(readOnly = true)
    public Integer getMaxCurrentId() {
        String sqlQuery = "SELECT id FROM tbl_company_mapping ORDER BY id DESC LIMIT 1";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery).uniqueResult() == null ? (0) : (Integer) session.createSQLQuery(sqlQuery).uniqueResult();
    }

    @Transactional
    public void saveCompanyMapping(CompanyMapping companyMapping) {
        sessionFactory.getCurrentSession().saveOrUpdate(companyMapping);
    }


    @Transactional(readOnly = true)
    public Boolean isCompanyMappedExists(BigInteger userId, Integer companyId) {
        String sqlQuery = "SELECT COUNT(*) FROM tbl_company_mapping WHERE userId=:userId AND companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery).setParameter("userId", userId)
                .setParameter("companyId", companyId)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public Integer getId(BigInteger userId, Integer companyId) {
        String sqlQuery = "SELECT id FROM tbl_company_mapping WHERE userId=:userId AND companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(sqlQuery)
                .setParameter("userId", userId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigInteger getUserIdByUserName(String username) {
        String sqlQuery = "SELECT userId FROM tbl_user WHERE username=:username";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(sqlQuery)
                .setParameter("username", username)
                .uniqueResult();
    }

    @Transactional
    public void deleteMappedCompany(BigInteger userId, Integer companyId) {
        String sqlQuery = "DELETE FROM tbl_company_mapping WHERE userId=:userId AND companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(sqlQuery)
                .setParameter("userId", userId)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

    @Transactional
    public void deleteMappedCompanyByUserId(BigInteger userId) {
        String sqlQuery = "DELETE FROM tbl_company_mapping WHERE userId=:userId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(sqlQuery)
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Boolean checkIsAdministratorExists(Integer roleTypeId, Integer companyId) {
        String sqlQuery = "SELECT COUNT(*) FROM tbl_user a\n" +
                "INNER JOIN tbl_company_mapping b On a.userId=b.userId \n" +
                "WHERE userRoleTypeId=:roleTypeId AND b.companyId=:companyId\n" +
                "AND a.companyId IS NULL";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("roleTypeId", roleTypeId)
                .setParameter("companyId", companyId)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional
    public void deleteUser(BigInteger userId) {
        String sqlQuery = "DELETE FROM tbl_user WHERE userId=:userId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(sqlQuery)
                .setParameter("userId", userId)
                .executeUpdate();
    }
    @Transactional(readOnly = true)
    public Integer getCompanyId(String username) {
        String sqlQuery = "SELECT companyId FROM tbl_user where username=:username";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(sqlQuery)
                .setParameter("username", username)
                .uniqueResult();
    }

    //endregion
}
