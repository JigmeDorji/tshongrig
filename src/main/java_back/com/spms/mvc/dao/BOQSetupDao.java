package com.spms.mvc.dao;

import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.dto.BOQDetailsListDTO;
import com.spms.mvc.entity.BOQ;
import com.spms.mvc.entity.BOQDetail;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Description: BOQSetupDao
 * Date:  2022-Jan-11
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-11
 * Change Description:
 * Search Tag:
 */
@Repository("bOQSetupDao")
public class BOQSetupDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public BigInteger getMaxBOQId() {
        String query = "SELECT MAX(boqId) FROM tbl_acc_boq";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxDetailBOQId() {
        String query = "SELECT MAX(boqDetailId) FROM tbl_acc_boq_detail";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional
    public void saveBOQDetail(BOQDetail boqDetail) {
        sessionFactory.getCurrentSession().save(boqDetail);
    }

    @Transactional
    public void saveBoq(BOQ boq) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(boq);
    }

    @Transactional(readOnly = true)
    public Boolean checkIsWorkOrderNoExists(String workOrderNo) {
        String query = "SELECT COUNT(workOrderNo) FROM tbl_acc_boq WHERE workOrderNo=:workOrderNo";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("workOrderNo", workOrderNo).uniqueResult()
                .equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public List<BOQDetailsDTO> getGeneratedBOQList(Integer companyId) {
        String query = "SELECT boqId,workOrderNo,nameOfWork,employingAgency,workOrderDate,workStartDate,completionDate FROM tbl_acc_boq \n" +
                "WHERE companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(BOQDetailsDTO.class))
                .setParameter("companyId", companyId).list();
    }

    @Transactional(readOnly = true)
    public BOQDetailsDTO getDetailByBOQId(BigInteger boqId) {
        String query = "SELECT boqId,workOrderNo,nameOfWork,employingAgency,workOrderDate,workStartDate,completionDate FROM tbl_acc_boq \n" +
                "WHERE boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return (BOQDetailsDTO) session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(BOQDetailsDTO.class))
                .setParameter("boqId", boqId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<BOQDetailsListDTO> getBOQList(BigInteger boqId) {
        String query = "SELECT code,description,unit AS unitOfMeasurement,rate,qty,rateInWords,totalAmountInWords FROM tbl_acc_boq_detail where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(BOQDetailsListDTO.class))
                .setParameter("boqId", boqId).list();
    }

    @Transactional
    public void deleteFromDetail(BigInteger boqId) {
        String query = "DELETE FROM tbl_acc_boq_detail where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("boqId", boqId).executeUpdate();
    }

    @Transactional
    public void deleteFromMainTable(BigInteger boqId) {
        String query = "DELETE FROM tbl_acc_boq where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("boqId", boqId).executeUpdate();
    }

    @Transactional(readOnly = true)
    public boolean isWorkOrderNoUsedInRABill(BigInteger boqId) {
        String query = "SELECT COUNT(boqId) FROM tbl_acc_boq_ra_bill where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setParameter("boqId", boqId).uniqueResult()
                .equals(BigInteger.ZERO);
    }
}
