package com.spms.mvc.dao;

import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.library.helper.CurrentUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 3/27/2021.
 */
@Repository("saleItemDetailDao")
public class SaleDetailDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<SaleItemListDTO> getSaleItemDetailList(Integer companyId, Date fromDate, Date toDate) {
        String query = "SELECT a.saleRecordId,\n" +
                "a.receiptMemoNo,\n" +
                "a.invoiceNo,\n" +
                "a.voucherNo,\n" +
                "SUM(b.qty * b.sellingPrice) AS totalAmount,\n" +
                "saleDate FROM tbl_inv_sale_record a\n" +
                "INNER JOIN tbl_inv_sale_record_detail b ON a.saleRecordId=b.saleRecordId " +
                "WHERE a.companyId=:companyId AND a.saleDate>=:fromDate AND a.saleDate <=:toDate \n" +
                "group by saleRecordId";
        Session session = sessionFactory.getCurrentSession();
        return (List<SaleItemListDTO>) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public SaleItemListDTO getSaleMemoDetail(String receiptMemoNo, Integer companyId) {
        String query = "SELECT sum(b.qty*b.sellingPrice) as totalAmount FROM tbl_inv_sale_record a\n" +
                "inner join tbl_inv_sale_record_detail b on a.saleRecordId=b.saleRecordId\n" +
                "group by a.receiptMemoNo where a.receiptMemoNo=:receiptMemoNo and a.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (SaleItemListDTO) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("receiptMemoNo", receiptMemoNo)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<SaleItemListDTO> getSaleItemList(String receiptMemoNo, Integer companyId) {
        String query = "SELECT id, b.itemCode,b.qty FROM tbl_inv_sale_record a\n" +
                "inner join tbl_inv_sale_record_detail b on a.saleRecordId=b.saleRecordId\n" +
                "WHERE a.receiptMemoNo=:receiptMemoNo and a.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("receiptMemoNo", receiptMemoNo)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class)).list();
    }

    @Transactional
    public void updateQtyInPurchase(String itemCode, BigDecimal qty, Integer companyId) {
        String query = "UPDATE tbl_inv_purchase SET qty=qty + :qty WHERE itemCode=:itemCode AND companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("itemCode", itemCode)
                .setParameter("qty", qty)
                .executeUpdate();
    }

    @Transactional
    public void deleteSaleRecord(String receiptMemoNo, Integer companyId, Integer financialYearId) {
        String query = "DELETE FROM  tbl_inv_sale_record WHERE receiptMemoNo=:receiptMemoNo AND companyId=:companyId AND financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("receiptMemoNo", receiptMemoNo)
                .executeUpdate();

    }

    @Transactional
    public void deleteSaleDetail(Integer id) {
        String query = "DELETE FROM  tbl_inv_sale_record_detail where id=:id";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Integer getVoucherNoByReceiptMemoNo(String receiptMemoNo, CurrentUser currentUser) {
        String query = "SELECT voucherNo FROM tbl_inv_sale_record a WHERE a.receiptMemoNo=:receiptMemoNo AND a.companyId=:companyId AND a.financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .setParameter("receiptMemoNo", receiptMemoNo)
                .uniqueResult();
    }
}
