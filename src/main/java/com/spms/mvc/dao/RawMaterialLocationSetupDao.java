package com.spms.mvc.dao;

import com.spms.mvc.dto.LocationSetUpDTO;
import com.spms.mvc.entity.RawMaterialLocationSetup;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("rawMaterialLocationSetupDao")
public class RawMaterialLocationSetupDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Transactional
    public void save(RawMaterialLocationSetup rawMaterialLocationSetup) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rawMaterialLocationSetup);
    }

    @Transactional
    public void update(RawMaterialLocationSetup rawMaterialLocationSetup) {
        sessionFactory.getCurrentSession().update(rawMaterialLocationSetup);
    }


    @Transactional(readOnly = true)
    public List<LocationSetUpDTO> getLocationList(Integer companyId) {

        String query = "SELECT locationSetUpId AS locationSetUpId,locationId AS locationId ,description AS description" +
                "  FROM tbl_rawmateriallocationsetup where companyId=:companyId ORDER BY LENGTH(locationId), locationId;";

        Session session = sessionFactory.getCurrentSession();

        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setResultTransformer(Transformers.aliasToBean(LocationSetUpDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getLocationSetUpList(Integer companyId) {
        String query = "SELECT B.locationSetUpId AS value,\n" +
                "concat(B.locationId,' : ',description) AS text FROM tbl_rawmateriallocationsetup B where B.companyId=:companyId  ORDER BY LENGTH(B.locationId),B.locationId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<LocationSetUpDTO> getAllLocationList() {
        String query = "SELECT locationSetUpId AS locationSetUpId,locationId AS locationId ,description AS description" +
                "  FROM tbl_rawmateriallocationsetup ";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(LocationSetUpDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<LocationSetUpDTO> getAllLocationList(int locationSetupId) {
        String query = "SELECT locationSetUpId AS locationSetUpId,locationId AS locationId ,description AS description" +
                "  FROM tbl_rawmateriallocationsetup  where locationSetUpId=:locationSetupId";
        Session session = sessionFactory.getCurrentSession();

        return session.createSQLQuery(query)
                .setParameter("locationSetupId",locationSetupId)
                .setResultTransformer(Transformers.aliasToBean(LocationSetUpDTO.class)).list();

    }

}
