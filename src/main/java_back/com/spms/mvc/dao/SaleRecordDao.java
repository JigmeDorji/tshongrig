package com.spms.mvc.dao;

import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleRecordDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 18-Dec-16.
 */
@Repository("saleRecordDao")
public class SaleRecordDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<SaleRecordDTO> getSaleRecordList(Integer itemId, Date fromDate, Date toDate) {
        String query = "SELECT tsr.itemcode AS itemCode,\n" +
                "tsr.generateddate AS generateDate,\n" +
                "                tic.partDescription AS partDescription,\n" +
                "                tic.partNumber AS itemName,\n" +
                "                tsr.sellingprice AS pricePerQty,\n" +
                "                tsr.cPPerQty AS cPPerQty,\n" +
                "                tsr.discountRate AS discountAmt,\n" +
                "                IFNULL(tsr.workOrderNo,'') AS workOrderNo, \n" +
                "                IFNULL(tsr.memono,'') AS memoNo, \n" +
                "                IFNULL(r.bill_no,'') AS  bill_No \n" +
                "                FROM tbl_salerecord tsr\n" +
                "                INNER JOIN tbl_itemcategory tic\n" +
                "                ON tsr.itemid=tic.itemcategoryid \n" +
                "                LEFT JOIN tbl_registration r \n" +
                "                ON r.registration_no=tsr.workOrderNo \n" +
                "                WHERE tsr.itemid=:itemId AND tsr.generateddate BETWEEN :fromDate AND :toDate\n" +
                "                UNION ALL\n" +
                "                SELECT tsr.itemcode AS itemCode,\n" +
                "                tsr.generateddate AS generateDate,\n" +
                "                tic.partDescription AS partDescription,\n" +
                "                tic.partNumber AS itemName,\n" +
                "                tsr.sellingprice AS pricePerQty,\n" +
                "                tsr.cPPerQty AS cPPerQty,\n" +
                "                tsr.discountRate AS discountAmt,\n" +
                "                IFNULL(tsr.workOrderNo,'') AS workOrderNo, \n" +
                "                IFNULL(tsr.memono,'') AS memoNo, \n" +
                "                IFNULL(r.bill_no,'') AS  bill_No \n" +
                "                FROM tbl_dealerItemsalerecord tsr\n" +
                "                INNER JOIN tbl_itemcategory tic\n" +
                "                ON tsr.itemid=tic.itemcategoryid \n" +
                "                LEFT JOIN tbl_registration r \n" +
                "                ON r.registration_no=tsr.workOrderNo \n" +
                "                WHERE tsr.itemid=:itemId AND tsr.generateddate BETWEEN :fromDate AND :toDate";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("itemId", itemId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(SaleRecordDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<SaleRecordDTO> getSaleRecordListSummary(Integer itemId, Date fromDate, Date toDate) {
        String query = "SELECT A.itemName AS itemName,\n" +
                "A.partDescription AS partDescription,\n" +
                "                A.pricePerQty AS pricePerQty,\n" +
                "                SUM(A.qtySold) as qtySold,\n" +
                "                SUM(A.totalAmount) AS totalAmount\n" +
                "                FROM (SELECT tic.partNumber AS itemName,\n" +
                "                tic.partDescription AS partDescription,\n" +
                "                tsr.sellingprice AS pricePerQty,\n" +
                "                Count(tsr.sellingprice) as qtySold,\n" +
                "                SUM(tsr.sellingprice) AS totalAmount \n" +
                "                FROM tbl_salerecord tsr\n" +
                "                INNER JOIN tbl_itemcategory tic ON tsr.itemid=tic.itemcategoryid \n" +
                "                WHERE tsr.itemid=:itemId AND tsr.generateddate BETWEEN :fromDate AND :toDate\n" +
                "                group by tsr.sellingprice,tic.itemcategoryid\n" +
                "                UNION ALL\n" +
                "                SELECT tic.partNumber AS itemName,\n" +
                "                tic.partDescription AS partDescription,\n" +
                "                tsr.sellingprice AS pricePerQty,\n" +
                "                Count(tsr.sellingprice) as qtySold,\n" +
                "                SUM(tsr.sellingprice) AS totalAmount \n" +
                "                FROM tbl_dealerItemsalerecord tsr\n" +
                "                INNER JOIN tbl_itemcategory tic ON tsr.itemid=tic.itemcategoryid \n" +
                "                WHERE tsr.itemid=:itemId AND tsr.generateddate BETWEEN :fromDate AND :toDate\n" +
                "                group by tsr.sellingprice,tic.itemcategoryid) A  group by A.itemName,A.partDescription  ";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("itemId", itemId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(SaleRecordDTO.class)).list();
    }


    @Transactional(readOnly = true)
    public List<SaleRecordDTO> getSaleRecordListALL(Integer itemId, Date fromDate, Date toDate) {
        String query = "SELECT tsr.itemcode AS itemCode,\n" +
                "tsr.generateddate AS generateDate,\n" +
                "                tic.partDescription AS partDescription,\n" +
                "                tic.partNumber AS itemName,\n" +
                "                tsr.sellingprice AS pricePerQty,\n" +
                "                tsr.cPPerQty AS cPPerQty,\n" +
                "                tsr.discountRate AS discountAmt,\n" +
                "                IFNULL(tsr.workOrderNo,'') AS workOrderNo, \n" +
                "                IFNULL(tsr.memono,'') AS memoNo, \n" +
                "                IFNULL(r.bill_no,'') AS  bill_No \n" +
                "                FROM tbl_salerecord tsr\n" +
                "                INNER JOIN tbl_itemcategory tic\n" +
                "                ON tsr.itemid=tic.itemcategoryid \n" +
                "                LEFT JOIN tbl_registration r \n" +
                "                ON r.registration_no=tsr.workOrderNo \n" +
                "                WHERE tsr.generateddate BETWEEN :fromDate AND :toDate\n" +
                "                UNION ALL\n" +
                "                SELECT tsr.itemcode AS itemCode,\n" +
                "                tsr.generateddate AS generateDate,\n" +
                "                tic.partDescription AS partDescription,\n" +
                "                tic.partNumber AS itemName,\n" +
                "                tsr.sellingprice AS pricePerQty,\n" +
                "                tsr.cPPerQty AS cPPerQty,\n" +
                "                tsr.discountRate AS discountAmt,\n" +
                "                IFNULL(tsr.workOrderNo,'') AS workOrderNo, \n" +
                "                IFNULL(tsr.memono,'') AS memoNo, \n" +
                "                IFNULL(r.bill_no,'') AS  bill_No \n" +
                "                FROM tbl_dealerItemsalerecord tsr\n" +
                "                INNER JOIN tbl_itemcategory tic\n" +
                "                ON tsr.itemid=tic.itemcategoryid \n" +
                "                LEFT JOIN tbl_registration r \n" +
                "                ON r.registration_no=tsr.workOrderNo \n" +
                "                WHERE tsr.generateddate BETWEEN :fromDate AND :toDate";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(SaleRecordDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<SaleItemDTO> getSaleRecordListSummaryAll(Date fromDate, Date toDate) {
        String query = "SELECT C.itemCode AS itemCode,\n" +
                "B.itemName AS itemName,\n" +
                "SUM(C.qty) AS sumQty,\n" +
                "B.sellingPrice AS sellingPrice,\n" +
                "(SUM(C.qty))*B.sellingPrice AS amount,\n" +
                "SUM(ifnull(A.discount,0)) AS discountRate\n" +
                "FROM tbl_inv_sale_record A\n" +
                "INNER JOIN tbl_inv_sale_record_detail C ON A.saleRecordId=C.saleRecordId \n" +
                "INNER JOIN tbl_inv_purchase B ON C.itemCode=B.itemCode\n" +
                "WHERE A.saleDate>=:fromDate AND A.saleDate<=:toDate \n" +
                "GROUP BY C.itemCode";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public SaleRecordDTO getItemName(Integer itemId) {
        String query = "SELECT partNumber AS itemName FROM tbl_itemcategory WHERE itemcategoryid=:itemId";
        Session session = sessionFactory.getCurrentSession();
        return (SaleRecordDTO) session.createSQLQuery(query)
                .setParameter("itemId", itemId)
                .setResultTransformer(Transformers.aliasToBean(SaleRecordDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getTotalDiscount(Date fromDate, Date toDate, String discountLedgerId) {
        String query = "select abs(sum(a.drcrAmount)) from tbl_acc_voucher_entries_detail a\n" +
                "inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId where a.ledgerId=:discountLedgerId\n" +
                "and b.setDate>=:fromDate and b.setDate<=:toDate";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query).setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("discountLedgerId", discountLedgerId);
        return result.uniqueResult() == null ? 0 : (Double) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getSaleReplaceDiffAmt(Date fromDate, Date toDate) {
        String query = "Select IFNULL(SUM(A.returnQty*B.sellingPrice),0)-((Select IFNULL(SUM(A.replaceQty*B.sellingPrice),0) from tbl_inv_replace_item A \n" +
                "INNER JOIN tbl_inv_purchase B ON A.itemCode=B.itemCode\n" +
                "where A.setDate>=:fromDate AND A.setDate<=:toDate)) from tbl_inv_return_item A \n" +
                "INNER JOIN tbl_inv_purchase B ON A.itemCode=B.itemCode\n" +
                "where A.setDate>=:fromDate AND A.setDate<=:toDate";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query).setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate);
        return result.uniqueResult() == null ? 0 : (Double) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getTotalAmtByAccType(Date fromDate, Date toDate,  Integer companyId,Integer accountTypeId) {
        String query = "select abs(sum(a.drcrAmount)) from tbl_acc_voucher_entries_detail a\n" +
                "inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId \n" +
                "inner join tbl_acc_ledger c on c.ledgerId=a.ledgerId\n" +
                "where accTypeId=:accountTypeId and b.setDate>=:fromDate and b.setDate<=:toDate and b.companyId=:companyId and a.drcrAmount>0";//<0 means take only cash credited amt
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query).setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("accountTypeId", accountTypeId)
                .setParameter("companyId", companyId);
        return result.uniqueResult() == null ? 0 : (Double) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getTotalAmtCashOrBank(Date fromDate, Date toDate,  Integer companyId,Integer accountTypeId) {
        String query = "select abs(sum(a.drcrAmount)) from tbl_acc_voucher_entries_detail a\n" +
                "inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId \n" +
                "inner join tbl_acc_ledger c on c.ledgerId=a.ledgerId\n" +
                "where accTypeId=:accountTypeId and b.setDate>=:fromDate and b.setDate<=:toDate and b.companyId=:companyId and a.drcrAmount<0";//<0 means take only cash credited amt
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query).setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("accountTypeId", accountTypeId)
                .setParameter("companyId", companyId);
        return result.uniqueResult() == null ? 0 : (Double) result.uniqueResult();
    }
}
