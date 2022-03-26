package com.spms.mvc.dao;

import com.spms.mvc.dto.AccCashFlowDTO;
import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.dto.LedgerDTO;
import com.spms.mvc.entity.BankReconciliation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/19/2019.
 */
@Repository("voucherGroupListDao")
public class VoucherGroupListDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<AccProfitAndLossReportDTO> getVoucherDetailsByAccTypeId(Integer accTypeId) {
        String sqlQry = "SELECT B.voucherId AS voucherId, \n" +
                "C.ledgerName AS ledgerName ,\n" +
                "SUM(IFNULL(B.debitAmount,0)) AS debitAmount,\n" +
                "SUM(IFNULL(B.creditAmount,0)) AS creditAmount,\n" +
                "A.setDate AS voucherCreatedDate\n" +
                "FROM tbl_acc_voucher A\n" +
                "INNER JOIN  tbl_acc_voucher_detail B ON A.voucherId=B.voucherId \n" +
                "INNER JOIN tbl_acc_ledger C on C.ledgerId=B.ledgerId\n" +
                "INNER JOIN tbl_acc_acctype D on d.accTypeId=C.accTypeId \n" +
                "WHERE D.accTypeId=:accTypeId GROUP BY B.voucherId";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("accTypeId", accTypeId)
                .setResultTransformer(Transformers.aliasToBean(AccProfitAndLossReportDTO.class))
                .list();
    }

    @Transactional
    public List<AccProfitAndLossReportDTO> getVoucherDetailsByAccTypeAndLedgerType(String ledgerId, Date currentPeriodFrom, Date currentPeriodTo, Date fromDate, Date toDate, Integer companyId, Integer financialYearId) {
        String sqlQry = "call sp_acc_view_ledger_Details(:ledgerId,:currentPeriodFrom,:currentPeriodTo,:companyId,:financialYearId);";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId)
                .setParameter("currentPeriodFrom", currentPeriodFrom)
                .setParameter("currentPeriodTo", currentPeriodTo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
//                .setParameter("fromDate", fromDate)
//                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(AccProfitAndLossReportDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public LedgerDTO getOpeningBalance(String ledgerId) {
        String sqlQry = "SELECT openingBal AS openingBal,ledgerName AS ledgerName,accTypeId  FROM tbl_acc_ledger WHERE ledgerId=:ledgerId";
        return (LedgerDTO) sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId)
                .setResultTransformer(Transformers.aliasToBean(LedgerDTO.class))
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<AccProfitAndLossReportDTO> getInventoryPurchaseVoucherDetail() {
        String sqlQry = "SELECT A.voucherId AS voucherId,\n" +
                "A.voucherNo AS voucherNo,\n" +
                "D.ledgerName AS ledgerName,\n" +
                "E.voucherTypeName AS voucherTypeName,\n" +
                "B.voucherTypeId AS voucherTypeId,\n" +
                "ifnull(C.creditAmount,0) AS creditAmount,\n" +
                "IFNULL(C.debitAmount,0) AS debitAmount\n" +
                "FROM tbl_acc_voucher A \n" +
                "INNER JOIN tbl_acc_inv_voucherdetail B ON A.voucherId=B.voucherID\n" +
                "INNER JOIN tbl_acc_voucher_detail C ON C.voucherId=A.voucherId\n" +
                "INNER JOIN tbl_acc_ledger D ON D.ledgerId=C.ledgerId\n" +
                "INNER JOIN tbl_acc_vouchertype E ON A.voucherTypeId=E.voucherTypeId where A.voucherTypeId=6";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setResultTransformer(Transformers.aliasToBean(AccProfitAndLossReportDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public List<AccProfitAndLossReportDTO> getInventorySalesVoucherDetail() {
        String sqlQry = "SELECT A.voucherId AS voucherId,\n" +
                "A.voucherNo AS voucherNo,\n" +
                "D.ledgerName AS ledgerName,\n" +
                "E.voucherTypeName AS voucherTypeName,\n" +
                "A.setDate AS voucherCreatedDate, \n" +
                "IFNULL(C.creditAmount,0) AS creditAmount,\n" +
                "IFNULL(C.debitAmount,0) AS debitAmount\n" +
                "FROM tbl_acc_voucher A \n" +
                "INNER JOIN tbl_acc_inv_voucherdetail B ON A.voucherId=B.voucherID\n" +
                "INNER JOIN tbl_acc_voucher_detail C ON C.voucherId=A.voucherId\n" +
                "INNER JOIN tbl_acc_ledger D ON D.ledgerId=C.ledgerId\n" +
                "INNER JOIN tbl_acc_vouchertype E ON A.voucherTypeId=E.voucherTypeId where A.voucherTypeId=5";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setResultTransformer(Transformers.aliasToBean(AccProfitAndLossReportDTO.class))
                .list();
    }

    @Transactional
    public void saveBankReconciliation(BankReconciliation bankReconciliation) {
        sessionFactory.getCurrentSession().saveOrUpdate(bankReconciliation);
    }

    @Transactional
    public List<AccCashFlowDTO> getCashInFlowDetails(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "CALL sp_acc_get_cash_flow_detail(:companyId,:fromDate,:toDate,:isCashFlowIn);";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("isCashFlowIn", Boolean.TRUE)
                .setResultTransformer(Transformers.aliasToBean(AccCashFlowDTO.class))
                .list();
    }

    @Transactional
    public List<AccCashFlowDTO> getCashOutFlowDetails(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "CALL sp_acc_get_cash_flow_detail(:companyId,:fromDate,:toDate);";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(AccCashFlowDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public Double getClosingBalOfPreviousYear(Integer companyId, String ledgerId, Date currentPeriodFrom) {
 /*       String query = "SELECT sum(a.drcrAmount*-1)\n" +
                "FROM tbl_acc_voucher_entries_detail a\n" +
                "inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId\n" +
                "where a.ledgerId=:ledgerId and b.companyId=:companyId and b.voucherEntryDate<:currentPeriodFrom";
        */
        String query = "select sum(a.drcrAmount*-1)\n" +
                "from tbl_acc_voucher_entries_detail a\n" +
                "inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId\n" +
                "inner join tbl_acc_ledger c ON c.ledgerId=a.ledgerId\n" +
                "inner join tbl_acc_acctype d on d.accTypeId=c.accTypeId\n" +
                "inner join tbl_acc_group e on e.groupId=d.groupId\n" +
                "where a.ledgerId=:ledgerId and b.companyId=:companyId and b.voucherEntryDate <= :currentPeriodFrom\n" +
                "and e.groupId NOT IN(6,7,8,9)";

        Session session = sessionFactory.getCurrentSession();
        return (Double) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("ledgerId", ledgerId)
                .setParameter("currentPeriodFrom", currentPeriodFrom)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getMaterialOpeningAmt(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "SELECT SUM(a.costPrice*a.qty) \n" +
                "FROM tbl_inv_purchase_a a\n" +
                "INNER JOIN tbl_inv_purchase b ON a.purchaseId=b.purchaseId\n" +
                "WHERE b.companyId=:companyId and purchaseVoucherNo=0 and cmdFlag='C' and \n" +
                "(:fromDate is null OR a.purchaseDate>=:fromDate) and \n" +
                "a.purchaseDate<=:toDate\n";
        Double totalAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .uniqueResult();
        return totalAmount == null ? 0 : totalAmount;
    }


    /*@Transactional
    public void deleteVoucherFromVoucherTable(Integer voucherNo, Integer voucherTypeId, Integer companyId) {
        String sqlQry = "DELETE FROM tbl_acc_voucher WHERE voucherTypeId=:voucherTypeId AND voucherNo=:voucherNo AND companyId=:companyId";
        sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("voucherNo", voucherNo)
                .setParameter("voucherTypeId", voucherTypeId)
                .setParameter("companyId", companyId)
                .executeUpdate();

    }

    public void deleteVoucherFromVoucherDetails(Integer voucherTypeId, Integer voucherNo) {
        String sqlQry = "DELETE FROM tbl_acc_voucher WHERE voucherTypeId=:voucherTypeId AND voucherNo=:voucherNo";
        sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("voucherNo", voucherNo)
                .setParameter("voucherTypeId", voucherTypeId)
                .executeUpdate();

    }*/
}
