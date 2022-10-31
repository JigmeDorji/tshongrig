package com.spms.mvc.dao;


import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.entity.FinancialYear;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by Bikash Rai on 5/5/2020.
 */
@Repository
public class FinancialYearSetupDao extends BaseDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Integer getFinancialYearId(Integer companyId) {
        String query = "SELECT financialYearId FROM tbl_financial_year_setup where companyId=:companyId ORDER BY financialYearId DESC limit 1";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult() == null ? 0 : (Integer) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public Integer save(FinancialYear financialYear) {
        sessionFactory.getCurrentSession().saveOrUpdate(financialYear);
        return financialYear.getFinancialYearId();
    }

    @Transactional(readOnly = true)
    public List<FinancialYearDTO> getFinancialYearList(Integer companyId) {
        String query = "SELECT financialYearId,financialYearFrom,financialYearTo,status FROM tbl_financial_year_setup where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).list();
    }

    @Transactional
    public void update(FinancialYear financialYear) {
        sessionFactory.getCurrentSession().saveOrUpdate(financialYear);

    }

    @Transactional(readOnly = true)
    public Boolean isFinancialYearExists(Integer financialYearId, Integer companyId) {
        String query = "SELECT * FROM tbl_financial_year_setup WHERE financialYearId=:financialYearId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("financialYearId", financialYearId)
                .setParameter("companyId", companyId)
                .list().isEmpty();
    }

    @Transactional
    public void activateFinancialYear(BigInteger financialYearId, Integer companyId) {
        String query = "update tbl_financial_year_setup a set a.status='A' where a.financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getCurrentFinancialYear() {
        String query = "SELECT F.financialYearId AS valueInteger, CONCAT(YEAR(F.financialYearTo)) AS text FROM tbl_financial_year_setup F ORDER BY F.financialYearTo DESC";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public Integer getCurrentFinancialYearId() {
        String query = "SELECT F.financialYearId FROM tbl_financial_year_setup F WHERE F.status = 'A'";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getCurFinYear() {
        String query = "SELECT CONCAT(YEAR(F.financialYearTo)) FROM tbl_financial_year_setup F WHERE F.status = 'A'";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Boolean checkActiveFinancialYear(Integer companyId) {
        String query = "SELECT * FROM tbl_financial_year_setup WHERE status='A' and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .list().isEmpty();
    }

    @Transactional(readOnly = true)
    public FinancialYearDTO getPreviousFinancialYearDetail(Integer companyId, Integer financialYearId) {
 /*       String query = "SELECT financialYearId,financialYearTo,financialYearFrom,status\n" +
                "FROM tbl_financial_year_setup \n" +
                "where companyId=:companyId and \n" +
                "financialYearId<(select financialYearId from tbl_financial_year_setup where status='A' and companyId=:companyId)\n" +
                "order by financialYearId desc limit 1;";*/

        String query = "SELECT financialYearId,financialYearTo,financialYearFrom,status\n" +
                "FROM tbl_financial_year_setup where companyId=:companyId and  status='I'\n" +
                "order by financialYearId desc\n" +
                "LIMIT 1";
        Session session = sessionFactory.getCurrentSession();
        return (FinancialYearDTO) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).uniqueResult();
    }

    @Transactional
    public void makeInactiveFinancialYear(Integer companyId) {
        String query = "update tbl_financial_year_setup set status='I' where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query).setParameter("companyId", companyId).executeUpdate();
    }

    @Transactional(readOnly = true)
    public FinancialYearDTO getFinancialYearDetail(BigInteger financialYearId) {
        String query = "SELECT * FROM tbl_financial_year_setup where financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return (FinancialYearDTO) session.createSQLQuery(query)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public boolean checkIsFinancialYearAlreadyExist(Date newFromDate, Integer companyId) {
        String query = "SELECT * FROM tbl_financial_year_setup where financialYearFrom=:newFromDate and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("newFromDate", newFromDate)
                .setParameter("companyId", companyId)
                .list().size()>0;
    }

    @Transactional
    public void makeDefaultActiveYear(Date newFromDate, Integer companyId) {
        String query = "update tbl_financial_year_setup set status='A' where financialYearFrom=:newFromDate and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("newFromDate", newFromDate)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }
}
