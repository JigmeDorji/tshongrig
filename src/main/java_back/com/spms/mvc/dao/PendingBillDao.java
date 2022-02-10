package com.spms.mvc.dao;

import com.spms.mvc.dto.PendingBillDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SonamPC on 13-Jan-19.
 */
@Repository("pendingBillDao")
public class PendingBillDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<PendingBillDTO> getAllThePendingBill() {
        String query = "SELECT A.id AS id, \n" +
                "A.generateDate AS generateDate,\n" +
                "C.agency_name AS agentName,\n" +
                "B.department AS department,\n" +
                "B.contact_no AS contactNo,\n" +
                "A.billNo AS billNo,\n" +
                "A.workOrderNo AS workOrderNumber,\n" +
                "B.vehicle_no AS vehicleNo,\n" +
                "B.vehicle_type AS type,\n" +
                "A.totalBillAmt AS totalBillAmt,\n" +
                "A.remarks AS remarks,\n" +
                "A.paymentStatus AS paymentStatus\n" +
                "FROM tbl_agencyledger A\n" +
                "INNER JOIN tbl_registration B ON A.workOrderNo=B.registration_no \n" +
                "INNER JOIN tbl_agency C ON C.agency_id=A.agentId \n" +
                "WHERE A.paymentStatus='P'";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(PendingBillDTO.class)).list();

    }

    @Transactional
    public void updateBill(PendingBillDTO pendingBillDTO) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE tbl_agencyledger SET paymentStatus='B',remarks=:remarks WHERE billNo=:billNo";
        session.createSQLQuery(sql)
                .setParameter("billNo", pendingBillDTO.getBillNo())
                .setParameter("remarks", pendingBillDTO.getRemarks())
                .executeUpdate();

    }

    @Transactional(readOnly = true)
    public List<PendingBillDTO> searchBill(String agencyId, Character status) {
        String query = "SELECT A.id AS id, \n" +
                "A.generateDate AS generateDate,\n" +
                "C.agency_name AS agentName,\n" +
                "B.department AS department,\n" +
                "B.contact_no AS contactNo,\n" +
                "A.billNo AS billNo,\n" +
                "A.workOrderNo AS workOrderNumber,\n" +
                "B.vehicle_no AS vehicleNo,\n" +
                "B.vehicle_type AS type,\n" +
                "A.totalBillAmt AS totalBillAmt,\n" +
                "A.remarks AS remarks,\n" +
                "A.paymentStatus AS paymentStatus\n" +
                "FROM tbl_agencyledger A\n" +
                "INNER JOIN tbl_registration B ON A.workOrderNo=B.registration_no \n" +
                "INNER JOIN tbl_agency C ON C.agency_id=A.agentId \n"  +
                "WHERE (:agencyId is null OR A.agentId=:agencyId) \n" +
                "AND (:status IS NULL OR A.paymentStatus=:status)";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("agencyId", agencyId.equals("")?null:agencyId)
                .setParameter("status", status)
                .setResultTransformer(Transformers.aliasToBean(PendingBillDTO.class)).list();
    }
}
