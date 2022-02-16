package com.spms.mvc.dao;

import com.spms.mvc.dto.UserAccessPermissionListDTO;
import com.spms.mvc.entity.UserAccessPermission;
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
 * Created by jigme.dorji Sawa on 22/04/2020.
 */
@Repository()
public class UserAccessPermissionDao extends BaseDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getUserRoleList() {
        String sqlQuery = "SELECT userRoleTypeId AS valueInteger,userRoleTypeName AS text FROM tbl_user_role_type WHERE userRoleTypeId !=4";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<UserAccessPermissionListDTO> getScreenList(Integer userRoleTypeId) {
        String sqlQuery = "SELECT * FROM (SELECT \n" +
                "                          a.screenId, \n" +
                "                          a.screenName , \n" +
                "                          b.userAccessPermissionId , \n" +
                "                          b.isScreenAccessAllowed, \n" +
                "                          b.isEditAccessAllowed, \n" +
                "                          b.isDeleteAccessAllowed, \n" +
                "                          b.isSaveAccessAllowed \n" +
                "                          FROM tbl_screen a INNER JOIN tbl_useraccesspermission b \n" +
                "                          ON a.screenId = b.screenId WHERE b.userRoleTypeId =:userRoleTypeId \n" +
                "                          UNION \n" +
                "                          SELECT  \n" +
                "                          a.screenId, \n" +
                "                          a.screenName , \n" +
                "                          null AS userAccessPermissionId, \n" +
                "                          null AS isScreenAccessAllowed, \n" +
                "                          null AS isEditAccessAllowed, \n" +
                "                          null AS isDeleteAccessAllowed,\n" +
                "                          null AS isSaveAccessAllowed  \n" +
                "                          FROM tbl_screen a WHERE a.screenId NOT IN (SELECT a.screenId \n" +
                "                          FROM tbl_screen a INNER JOIN tbl_useraccesspermission b \n" +
                "                          ON a.screenId = b.screenId WHERE b.userRoleTypeId =:userRoleTypeId)) AS TEMP";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery)
                .setParameter("userRoleTypeId",userRoleTypeId)
                .setResultTransformer(Transformers.aliasToBean(UserAccessPermissionListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<UserAccessPermissionListDTO> getUnScreenList() {
        String sqlQuery = "SELECT a.screenId , a.screenName FROM tbl_screen a";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery).setResultTransformer(Transformers.aliasToBean(UserAccessPermissionListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Boolean isUserRoleAssigned(Integer roleTypeId) {
        String sqlQuery = "SELECT * FROM tbl_useraccesspermission WHERE userRoleTypeId=:roleTypeId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQuery).setParameter("roleTypeId",roleTypeId).list()!=null;
    }

    @Transactional
    public void update(UserAccessPermission userAccessPermission) {
        sessionFactory.getCurrentSession().saveOrUpdate(userAccessPermission);
    }

    @Transactional
    public void save(UserAccessPermission userAccessPermission) {
        sessionFactory.getCurrentSession().saveOrUpdate(userAccessPermission);
    }

    @Transactional(readOnly = true)
    public BigInteger getUserAccessPermissionIdSerial() {
        String sqlQuery = "SELECT userAccessPermissionId \n" +
                           "FROM tbl_useraccesspermission \n" +
                           "ORDER BY userAccessPermissionId DESC LIMIT 1";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger)session.createSQLQuery(sqlQuery).uniqueResult();

    }
}
