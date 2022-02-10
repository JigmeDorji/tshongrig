package com.spms.mvc.dao;

import com.spms.mvc.dto.SupplierSetupDTO;
import com.spms.mvc.entity.SupplierSetup;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jigmePc on 10/20/2019.
 */
@Repository("supplierSetupDao")
public class SupplierSetupDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void save(SupplierSetup supplierSetup) {
        sessionFactory.getCurrentSession().saveOrUpdate(supplierSetup);
    }

    @Transactional(readOnly = true)
    public List<SupplierSetupDTO> getSupplierList(Integer companyId) {
        String query = "SELECT id as id,\n" +
                "supplierName as supplierName,\n" +
                "address as address,\n" +
                "email as email,\n" +
                "contactNo as contactNo\n" +
                "FROM tbl_inv_supplier where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setResultTransformer(Transformers.aliasToBean(SupplierSetupDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public SupplierSetupDTO getSupplierDetails(Integer id) {
        String query = "SELECT id as id,\n" +
                "supplierName as supplierName,\n" +
                "address as address,\n" +
                "email as email,\n" +
                "contactNo as contactNo\n" +
                "FROM tbl_inv_supplier where id=:id";
        Session session = sessionFactory.getCurrentSession();
        return (SupplierSetupDTO) session.createSQLQuery(query)
                .setParameter("id", id)
                .setResultTransformer(Transformers.aliasToBean(SupplierSetupDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getSupplierListDropDown(Integer companyId) {
        String query = "SELECT id AS value,\n" +
                "supplierName AS text FROM tbl_inv_supplier where companyId=:companyId ORDER BY supplierName asc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();


    }

    @Transactional(readOnly = true)
    public Integer getSupplierIdByName(String ledgerName, CurrentUser currentUser) {
        String sqlQry = "select id from tbl_inv_supplier where companyId=:companyId and supplierName=:ledgerName AND financialYearId=:financialYearId";
        return (Integer) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("ledgerName", ledgerName)
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .setParameter("companyId", currentUser.getCompanyId()).uniqueResult();
    }
}
