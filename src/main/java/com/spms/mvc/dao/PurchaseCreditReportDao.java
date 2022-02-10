package com.spms.mvc.dao;

import com.spms.mvc.dto.CreditPaymentForReceiveItemDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 13-Feb-18.
 */
@Repository("purchaseCreditReportDao")
public class PurchaseCreditReportDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<CreditPaymentForReceiveItemDTO> getAllAgentWisePurchaseCreditSummaryDetail(Date fromDate, Date toDate) {
        String query = "SELECT B.bill_reference AS bill_reference,\n" +
                "A.agency_name AS agencyName,\n" +
                "SUM(B.total_amount) AS total_amount,\n" +
                "IFNULL(C.total_amount_paid,0) AS total_amount_paid,\n" +
                "SUM(B.total_amount)-IFNULL(C.total_amount_paid,0) AS balanceAmount,\n" +
                "case when SUM(B.total_amount)-IFNULL(C.total_amount_paid,0)=0 then \"Paid\" \n" +
                "when SUM(B.total_amount)=SUM(B.total_amount)-IFNULL(C.total_amount_paid,0) then \"Pending\" \n" +
                "when SUM(B.total_amount)-IFNULL(C.total_amount_paid,0)!=0 AND (SUM(B.total_amount)-IFNULL(C.total_amount_paid,0)!=SUM(B.total_amount)) then \"Partial\" \n" +
                "end AS status\n" +
                "FROM tbl_agency A INNER JOIN tbl_agencywise_item_receive B ON  A.agency_id=B.agency_id\n" +
                "LEFT JOIN (select bill_reference,SUM(total_amount_paid) AS total_amount_paid,agency_id from tbl_credit_payment_for_receiveitem group by bill_reference) C\n" +
                "ON A.agency_id =C.agency_id AND B.bill_reference =C.bill_reference\n" +
                "WHERE B.setDate between :fromDate AND :toDate\n" +
                "GROUP BY B.bill_reference \n" +
                "ORDER BY A.agency_name;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(CreditPaymentForReceiveItemDTO.class)).list();
    }


    @Transactional(readOnly = true)
    public List<CreditPaymentForReceiveItemDTO> getAllAgentWisePurchaseCreditDetail(Date fromDate, Date toDate) {
        String query = "SELECT \n" +
                "B.bill_reference AS bill_reference,\n" +
                "A.agency_name AS agencyName,\n" +
                "ic.partNumber AS partNumber,\n" +
                "ic.partDescription AS partName,\n" +
                "B.setDate AS itemReceivedDate,\n" +
                "B.rate AS rate,\n" +
                "SUM(B.quantity_receive) AS quantityReceived,\n" +
                "SUM(B.total_amount) AS total_amount,\n" +
                "IFNULL(C.total_amount_paid,0) AS total_amount_paid,\n" +
                "(SELECT  SUM(total_amount) AS calTotalAmt FROM tbl_agencywise_item_receive WHERE  \n" +
                "bill_reference=(SELECT B.bill_reference) AND setDate \n" +
                "between :fromDate AND :toDate group by bill_reference )-IFNULL(C.total_amount_paid,0) AS balanceAmount,\n" +
                "case when (select balanceAmount)=0 then 'Paid' \n" +
                "when IFNULL(C.total_amount_paid,0)=0 then 'Pending' \n" +
                "when (select balanceAmount)<(\n" +
                "SELECT  SUM(total_amount) FROM tbl_agencywise_item_receive WHERE  bill_reference=(SELECT B.bill_reference) AND setDate \n" +
                "between :fromDate AND :toDate group by bill_reference \n" +
                ") then 'Partial' \n" +
                "end AS status\n" +
                "FROM tbl_agency A INNER JOIN tbl_agencywise_item_receive B ON  A.agency_id=B.agency_id\n" +
                "LEFT JOIN (select bill_reference,SUM(total_amount_paid) AS total_amount_paid,agency_id from tbl_credit_payment_for_receiveitem group by bill_reference) C\n" +
                "ON A.agency_id =C.agency_id AND B.bill_reference =C.bill_reference \n" +
                "INNER JOIN tbl_itemcategory ic ON ic.itemcategoryid=B.item_category_id\n" +
                "WHERE B.setDate between :fromDate AND :toDate\n" +
                "GROUP BY B.item_category_id, B.bill_reference ORDER BY A.agency_name,B.bill_reference ;\n";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(CreditPaymentForReceiveItemDTO.class)).list();
    }


    @Transactional(readOnly = true)
    public List<CreditPaymentForReceiveItemDTO> getAgentWisePurchaseCreditSummaryDetail(String agentId, Date fromDate, Date toDate) {
        String query = "SELECT B.bill_reference AS bill_reference,\n" +
                "A.agency_name AS agencyName,\n" +
                "SUM(B.total_amount) AS total_amount,\n" +
                "IFNULL(C.total_amount_paid,0) AS total_amount_paid,\n" +
                "SUM(B.total_amount)-IFNULL(C.total_amount_paid,0) AS balanceAmount,\n" +
                "case when SUM(B.total_amount)-IFNULL(C.total_amount_paid,0)=0 then \"Paid\" \n" +
                "when SUM(B.total_amount)=SUM(B.total_amount)- IFNULL(C.total_amount_paid,0) then \"Pending\" \n" +
                "when SUM(B.total_amount)-IFNULL(C.total_amount_paid,0)!=0 AND (SUM(B.total_amount)-IFNULL(C.total_amount_paid,0)!=SUM(B.total_amount)) then \"Partial\" \n" +
                "end AS status \n" +
                "FROM tbl_agency A INNER JOIN tbl_agencywise_item_receive B ON  A.agency_id=B.agency_id\n" +
                "LEFT JOIN (select bill_reference,SUM(total_amount_paid) AS total_amount_paid,agency_id from tbl_credit_payment_for_receiveitem group by bill_reference) C\n" +
                "ON A.agency_id =C.agency_id AND B.bill_reference =C.bill_reference\n" +
                "WHERE B.agency_id=:agentId AND B.setDate between :fromDate AND :toDate\n" +
                "GROUP BY B.bill_reference \n" +
                "ORDER BY A.agency_name;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("agentId", agentId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(CreditPaymentForReceiveItemDTO.class)).list();
    }


    @Transactional(readOnly = true)
    public List<CreditPaymentForReceiveItemDTO> getAgentWisePurchaseCreditDetail(String agentId, Date fromDate, Date toDate) {
        String query = "SELECT \n" +
                "B.bill_reference AS bill_reference,\n" +
                "A.agency_name AS agencyName,\n" +
                "ic.partNumber AS partNumber,\n" +
                "ic.partDescription AS partName,\n" +
                "B.setDate AS itemReceivedDate,\n" +
                "B.rate AS rate,\n" +
                "SUM(B.quantity_receive) AS quantityReceived,\n" +
                "SUM(B.total_amount) AS total_amount,\n" +
                "IFNULL(C.total_amount_paid,0) AS total_amount_paid,\n" +
                "(SELECT  SUM(total_amount) AS calTotalAmt FROM tbl_agencywise_item_receive WHERE \n" +
                " bill_reference=(SELECT B.bill_reference) AND setDate \n" +
                "between :fromDate AND :toDate group by bill_reference )-IFNULL(C.total_amount_paid,0) AS balanceAmount,\n" +
                "case when (select balanceAmount)=0 then 'Paid' \n" +
                "when IFNULL(C.total_amount_paid,0)=0 then 'Pending' \n" +
                "when (select balanceAmount)<(\n" +
                "SELECT  SUM(total_amount) FROM tbl_agencywise_item_receive WHERE  bill_reference=(SELECT B.bill_reference) AND setDate \n" +
                "between :fromDate AND :toDate group by bill_reference \n" +
                ") then 'Partial' \n" +
                "end AS status\n" +
                "FROM tbl_agency A INNER JOIN tbl_agencywise_item_receive B ON  A.agency_id=B.agency_id\n" +
                "LEFT JOIN (select bill_reference,SUM(total_amount_paid) AS total_amount_paid,agency_id from tbl_credit_payment_for_receiveitem group by bill_reference) C\n" +
                "ON A.agency_id =C.agency_id AND B.bill_reference =C.bill_reference \n" +
                "INNER JOIN tbl_itemcategory ic ON ic.itemcategoryid=B.item_category_id\n" +
                "WHERE B.agency_id=:agentId AND B.setDate between :fromDate AND :toDate\n" +
                "GROUP BY B.item_category_id, B.bill_reference ORDER BY A.agency_name,B.bill_reference ;\n";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("agentId", agentId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(CreditPaymentForReceiveItemDTO.class)).list();
    }

   @Transactional(readOnly = true)
    public List<CreditPaymentForReceiveItemDTO> getPurchaseCashReport(Date fromDate, Date toDate) {
        String query = "SELECT B.partNumber AS partNumber, B.partDescription AS partName, B.itempriceperqty AS rate,A.itemqty AS qty, A.recevieddate AS itemReceivedDate FROM tbl_item A \n" +
                "INNER JOIN tbl_itemcategory B ON A.itemcategoryid=B.itemcategoryid\n" +
                "WHERE recevieddate between :fromDate AND :toDate GROUP BY  B.itemcategoryid  ORDER BY B.partNumber";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(CreditPaymentForReceiveItemDTO.class)).list();
    }
}
