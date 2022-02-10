package com.spms.mvc.dao;

import com.spms.mvc.dto.LocationCreationDTO;
import com.spms.mvc.entity.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Project Name: Spare part management
 * Description: <Replace description>
 * Date:12/2/2016
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

@Repository("locationCreationDao")
public class LocationCreationDao {

    //region private sessionFactory
    @Autowired
    SessionFactory spmsSessionFactory;

    @Transactional
    public void save(Location location) {
        spmsSessionFactory.getCurrentSession().saveOrUpdate(location);
    }

    @Transactional(readOnly = true)
    public Boolean locationExists(Integer location_id) {
        String query = "SELECT COUNT(*) FROM tbl_locations WHERE location_id =:location_id";
        Session session = spmsSessionFactory.getCurrentSession();
        return !session.createSQLQuery(query).setParameter("location_id", location_id).uniqueResult().equals(BigInteger
                .ZERO);
    }

    @Transactional(readOnly = true)
    public List<LocationCreationDTO> getLocationList() {
        String query = "SELECT location_id AS location_id,location AS location, uploaded AS uploaded FROM tbl_locations";
        Session session = spmsSessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(LocationCreationDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public LocationCreationDTO getLocationDetail(Integer location_id) {
        String query = "SELECT location_id AS location_id,location AS location, uploaded AS uploaded FROM tbl_locations " +
                "WHERE location_id =:location_id";
        Session session = spmsSessionFactory.getCurrentSession();
        return (LocationCreationDTO) session.createSQLQuery(query)
                .setParameter("location_id", location_id)
                .setResultTransformer(Transformers.aliasToBean(LocationCreationDTO.class)).uniqueResult();
    }
}
