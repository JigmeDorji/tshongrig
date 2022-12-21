package com.spms.mvc.dao;


import com.spms.mvc.dto.*;
import com.spms.mvc.entity.RawMaterialStorage;
import com.spms.mvc.library.helper.CurrentUser;
import org.hibernate.Criteria;
import org.hibernate.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository("rawMaterialStorageDao")
public class RawMaterialStorageDao {
    @Autowired
    private SessionFactory sessionFactory;


    @Transactional
    public void save(RawMaterialStorage rawMaterialStorage) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rawMaterialStorage);
    }

    @Transactional(readOnly = true)
    public List<RawMaterialStorageViewDTO> getList(CurrentUser currentUser,Date asOnDate) {
        String  query="SELECT id AS id,particular_name AS rawMaterialParticularName,quantity AS rawMaterialParticularQty,Unit AS rawMaterialParticularUnit ,Price AS rawMaterialParticularPrice,location AS rawMaterialParticularLocation,storageModifier AS storageModifier,rawMaterial_date as asOnDate" +
                " FROM tbl_rawmaterial_storage  where companyId=:companyId and rawMaterial_date=:asOnDate";

        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",currentUser.getCompanyId())
                .setParameter("asOnDate",asOnDate)
                .setResultTransformer(Transformers.aliasToBean(RawMaterialStorageViewDTO.class)).list();
    }

    public List<RawMaterialStorageViewDTO> getList(CurrentUser currentUser) {
     String  query="SELECT id AS id,particular_name AS rawMaterialParticularName,quantity AS rawMaterialParticularQty,Unit AS rawMaterialParticularUnit ,Price AS rawMaterialParticularPrice,location AS rawMaterialParticularLocation,storageModifier AS storageModifier" +
                " FROM tbl_rawmaterial_storage  where companyId=:companyId";

        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(RawMaterialStorageViewDTO.class)).list();
    }
}
