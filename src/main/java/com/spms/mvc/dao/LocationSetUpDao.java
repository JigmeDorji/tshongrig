package com.spms.mvc.dao;

import com.spms.mvc.dto.LocationSetUpDTO;
import com.spms.mvc.entity.LocationSetUp;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SonamPC on 12-Jun-17.
 */
@Repository("locationSetUpDao")
public class LocationSetUpDao {
    @Autowired
    SessionFactory sessionFactory;


    @Transactional
    public void update(LocationSetUp locationSetUp) {
        sessionFactory.getCurrentSession().update(locationSetUp);
    }


    @Transactional
    public void save(LocationSetUp locationSetUpy) {
        sessionFactory.getCurrentSession().saveOrUpdate(locationSetUpy);
    }

    @Transactional(readOnly = true)
    public List<LocationSetUpDTO> getLocationList(Integer companyId) {
        String query = "SELECT locationSetUpId AS locationSetUpId,locationId AS locationId ,description AS description" +
                "  FROM tbl_locationsetup where companyId=:companyId ORDER BY LENGTH(locationId), locationId;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setResultTransformer(Transformers.aliasToBean(LocationSetUpDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getLocationSetUpList(Integer companyId) {
        String query = "SELECT B.locationSetUpId AS value,\n" +
                "concat(B.locationId,' : ',description) AS text FROM tbl_locationsetup B where B.companyId=:companyId  ORDER BY LENGTH(B.locationId),B.locationId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();

    }
}
