package com.spms.mvc.dao;

import com.spms.mvc.dto.DepreciationDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/14/2019.
 */
@Repository("accProfitAndLossReportDao")
public class AccProfitAndLossReportDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Double getTotalAmountForSelectedAccountType(Integer companyId, Integer accountTypeId, Date fromDate, Date toDate, Integer financialYearId) {
        String sqlQry = "SELECT SUM(drcrAmount) FROM tbl_acc_voucher_entries_detail A\n" +
                "INNER JOIN tbl_acc_voucher_entries B ON A.voucherId=B.voucherId\n" +
                "LEFT JOIN tbl_acc_ledger C ON C.ledgerId=A.ledgerId\n" +
                "LEFT JOIN tbl_acc_acctype E ON E.accTypeId=C.accTypeId\n" +
                "WHERE (:fromDate is null or B.voucherEntryDate>=:fromDate)  AND B.voucherEntryDate<=:toDate  AND E.accTypeId=:accountTypeId " +
                "AND C.companyId=:companyId";
        Double totalAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("accountTypeId", accountTypeId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .uniqueResult();

        return totalAmount == null ? 0 : totalAmount;
    }

    @Transactional
    public Double getTotalGoodSoldAmount(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "CALL sp_acc_get_cost_of_goods_sold(:companyId,:fromDate,:toDate)";
        Double totalAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate).uniqueResult();
        return totalAmount == null ? 0 : totalAmount;
    }


    @Transactional(readOnly = true)
    public List<DepreciationDTO> getDepreciationAmount(Integer companyId) {
        String sqlQry = "SELECT B.additional AS cost,\n" +
                "B.additionalQty AS qty, \n" +
                "A.rateOfDepreciation AS rateOfDepreciation,\n" +
                "CAST(B.dateOfPurchase AS CHAR(11)) AS dateOfPurchase \n" +
                "FROM tbl_acc_depreciation  A\n" +
                "INNER JOIN tbl_acc_depreciation_item_details B ON A.depreciationId=B.depreciationId\n" +
                "WHERE A.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DepreciationDTO.class)).list();
    }


    @Transactional
    public Double getOpeningBalanceDifference(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "SELECT ufn_get_opening_balance_diff(:companyId,:fromDate,:toDate)";
        Double openingAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate).uniqueResult();
        return openingAmount == null ? 0 : openingAmount;
    }

    @Transactional(readOnly = true)
    public Double getTotalSale(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "SELECT SUM(a.qty*b.costPrice) FROM tbl_inv_sale_record_detail a\n" +
                "INNER JOIN tbl_inv_purchase_a b ON a.purchaseInvoiceNo=b.purchaseInvoiceNo\n" +
                "INNER JOIN tbl_inv_sale_record c ON c.saleRecordId=a.saleRecordId\n" +
                "WHERE b.companyId=:companyId and c.saleDate>= :fromDate and c.saleDate<= :toDate";
        Double totalSale = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate).uniqueResult();
        return totalSale == null ? 0 : totalSale;
    }


    @Transactional(readOnly = true)
    public double getDetailWithFinancialYear(Integer companyId, Integer accountTypeId, Date fromDate, Date toDate, Integer financialYearId) {
        String sqlQry = "SELECT SUM(drcrAmount) FROM tbl_acc_voucher_entries_detail A\n" +
                "INNER JOIN tbl_acc_voucher_entries B ON A.voucherId=B.voucherId\n" +
                "LEFT JOIN tbl_acc_ledger C ON C.ledgerId=A.ledgerId\n" +
                "LEFT JOIN tbl_acc_acctype E ON E.accTypeId=C.accTypeId\n" +
                "WHERE (:fromDate is null or B.voucherEntryDate>=:fromDate)  AND B.voucherEntryDate<=:toDate  AND E.accTypeId=:accountTypeId " +
                "AND C.companyId=:companyId AND B.financialYearId=:financialYearId";

        Double totalAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("accountTypeId", accountTypeId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult();

        return totalAmount == null ? 0 : totalAmount;
    }
}
