package com.spms.mvc.dao;

import com.spms.mvc.dto.BOQDetailsDTO;
import com.spms.mvc.dto.BOQDetailsListDTO;
import com.spms.mvc.entity.RABill;
import com.spms.mvc.entity.RABillDetail;
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
 * Description: BOQRABillGenerationDao
 * Date:  2022-Jan-14
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2022-Jan-14
 * Change Description:
 * Search Tag:
 */
@Repository("boqRABillGeneration")
public class BOQRABillGenerationDao {

    @Autowired
    SessionFactory sessionFactory;


    @Transactional(readOnly = true)
    public List<BOQDetailsListDTO> getBOQList(BigInteger boqId) {
        String query = "SELECT boqDetailId,code,description,unit AS unitOfMeasurement,rate FROM tbl_acc_boq_detail where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(BOQDetailsListDTO.class))
                .setParameter("boqId", boqId).list();
    }

    @Transactional(readOnly = true)
    public Integer getRASerialNo(Integer boqId) {
        String query = "SELECT MAX(raSerialNo) FROM tbl_acc_boq_ra_bill where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("boqId", boqId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Boolean isRaSerialCounterExistsForCompany(Integer boqId) {
        String query = "SELECT count(raSerialNo) FROM tbl_acc_boq_ra_bill where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("boqId", boqId).uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional
    public void insertInRASerialCounter(Integer companyId) {
        String query = "Insert INTO tbl_acc_boq_ra_counter VALUES(1,1,:companyId)";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId", companyId).executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getWorkOrderList(Integer companyId) {
        String query = "SELECT boqId AS valueBigInteger, concat(workOrderNo,' : ',nameOfWork) AS text FROM tbl_acc_boq where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class))
                .setParameter("companyId", companyId).list();
    }


    @Transactional(readOnly = true)
    public BigInteger getMaxRaBillId() {
        String query = "SELECT MAX(raBillId) FROM tbl_acc_boq_ra_bill";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query)
                .uniqueResult();
    }

    @Transactional
    public void save(RABill raBill) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(raBill);
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxRaBillDetailId() {
        String query = "SELECT MAX(raBillDetailId) FROM tbl_acc_boq_ra_bill_detail";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query)
                .uniqueResult();
    }

    @Transactional
    public void saveDetail(RABillDetail raBillDetail) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(raBillDetail);
    }

    @Transactional(readOnly = true)
    public String getEmployingAgency(BigInteger boqId) {
        String query = "SELECT employingAgency FROM tbl_acc_boq where boqId=:boqId";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("boqId", boqId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<BOQDetailsListDTO> getRABillList(BigInteger boqId, Integer raSerialNo) {
        String query = "SELECT d.code,\n" +
                "d.description,\n" +
                "d.rate,\n" +
                "d.unit AS unitOfMeasurement,\n" +
                "a.qty,\n" +
                "ROUND((d.rate*a.qty),2) AS amount  \n" +
                "FROM tbl_acc_boq_ra_bill_detail a\n" +
                "INNER JOIN tbl_acc_boq_ra_bill b ON a.raBillId=b.raBillId\n" +
                "INNER JOIN tbl_acc_boq c ON c.boqId=b.boqId\n" +
                "INNER JOIN tbl_acc_boq_detail d ON d.boqId=c.boqId AND a.boqDetailId=d.boqDetailId\n" +
                "WHERE a.raBillId=(SELECT raBillId FROM tbl_acc_boq_ra_bill WHERE boqId=:boqId and raSerialNo=:raSerialNo) group by a.raBillDetailId;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(BOQDetailsListDTO.class))
                .setParameter("boqId", boqId)
                .setParameter("raSerialNo", raSerialNo)
                .list();
    }

    @Transactional(readOnly = true)
    public String getRABillNo(BigInteger boqId, Integer raSerialNo) {
        String query = "SELECT raBillNo FROM tbl_acc_boq_ra_bill WHERE boqId=:boqId AND raSerialNo=:raSerialNo";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("boqId", boqId)
                .setParameter("raSerialNo", raSerialNo)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<BOQDetailsDTO> getGeneratedRAList(Integer companyId) {
        String query = "SELECT b.boqId,b.workOrderNo,a.voucherNo,a.raSerialNo,a.raBillNo,a.billDate FROM tbl_acc_boq_ra_bill a\n" +
                "INNER JOIN tbl_acc_boq b ON a.boqId=b.boqId\n" +
                "WHERE b.companyId=:companyId \n" +
                "group by a.raSerialNo, a.boqId \n" +
                "order by b.workOrderNo,a.raSerialNo";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(BOQDetailsDTO.class))
                .setParameter("companyId", companyId).list();
    }

    @Transactional(readOnly = true)
    public List<BOQDetailsListDTO> getBOQListForUpdate(BigInteger boqId, Integer raSerialNo) {
        String query = "SELECT c.raBillDetailId,a.boqDetailId,\n" +
                "a.code, a.description,\n" +
                "a.unit AS unitOfMeasurement,\n" +
                "a.rate,\n" +
                "c.qty \n" +
                "FROM tbl_acc_boq_detail a \n" +
                "INNER JOIN tbl_acc_boq_ra_bill b ON a.boqId=b.boqId \n" +
                "INNER JOIN tbl_acc_boq_ra_bill_detail c ON b.raBillId=c.raBillId AND a.boqDetailId=c.boqDetailId\n" +
                "where a.boqId=:boqId AND b.raSerialNo=:raSerialNo";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(BOQDetailsListDTO.class))
                .setParameter("boqId", boqId)
                .setParameter("raSerialNo", raSerialNo)
                .list();
    }

    @Transactional(readOnly = true)
    public BOQDetailsDTO getRaDetail(BigInteger boqId, Integer raSerialNo) {
        String query = "SELECT a.raBillId, a.raBillNo,a.billDate FROM tbl_acc_boq_ra_bill a WHERE a.boqId=:boqId AND a.raSerialNo=:raSerialNo";
        Session session = sessionFactory.getCurrentSession();
        return (BOQDetailsDTO) session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(BOQDetailsDTO.class))
                .setParameter("boqId", boqId)
                .setParameter("raSerialNo", raSerialNo)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigInteger getRaBillId(BigInteger boqId, Integer raSerialNo) {
        String query = "SELECT a.raBillId FROM tbl_acc_boq_ra_bill a WHERE a.boqId=:boqId AND a.raSerialNo=:raSerialNo";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query)
                .setParameter("boqId", boqId)
                .setParameter("raSerialNo", raSerialNo)
                .uniqueResult();
    }

    @Transactional
    public void deleteFromRaDetail(BigInteger raBillId) {
        String query = "DELETE FROM tbl_acc_boq_ra_bill_detail WHERE raBillId=:raBillId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("raBillId", raBillId)
                .executeUpdate();
    }

    @Transactional
    public void deleteFromRaMainTable(BigInteger raBillId) {
        String query = "DELETE FROM tbl_acc_boq_ra_bill WHERE raBillId=:raBillId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("raBillId", raBillId)
                .executeUpdate();
    }
}
