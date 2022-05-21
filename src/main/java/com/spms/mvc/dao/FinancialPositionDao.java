package com.spms.mvc.dao;

import com.spms.mvc.dto.AccTrialBalanceDTO;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by jigme.dorji on 5/21/2022.
 */
@Repository("financialPositionDao")
public class FinancialPositionDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<AccTrialBalanceDTO> getTrialBalance(Integer companyId, Integer financialYearId, Date fromDate, Date toDate) {
        String sqlQry = "CALL sp_acc_get_financial_position_detail(:companyId, :financialYearId, :fromDate,:toDate)";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setResultTransformer(Transformers.aliasToBean(AccTrialBalanceDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public List<AccTrialBalanceDTO> getSubTrialBalance(Integer companyId, Integer financialYearId, Date fromDate, Date toDate, Integer accTypeId) {
        String sqlQry = "SELECT b.ledgerId as ledgerId,\n" +
                "b.ledgerName as particular,\n" +
                "SUM(c.drcrAmount) as amount,\n" +
                "a.groupId as groupId,\n" +
                "a.accTypeId as accTypeId \n" +
                "FROM tbl_acc_acctype a\n" +
                "LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId\n" +
                "LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId\n" +
                "LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "WHERE d.companyId=:companyId AND d.financialYearId=:financialYearId \n" +
                "AND  d.voucherEntryDate>=:fromDate AND d.voucherEntryDate<=:toDate AND a.accTypeId=:accTypeId\n" +
                "GROUP BY b.ledgerId;";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("accTypeId", accTypeId)
                .setResultTransformer(Transformers.aliasToBean(AccTrialBalanceDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public AccTrialBalanceDTO   totalAmountByAccType(Integer companyId, Integer financialYearId, Date fromDate, Date toDate, Integer accTypeId) {
        String sqlQry = "SELECT * FROM (SELECT b.ledgerId as ledgerId, a.accTypeName as particular,SUM(c.drcrAmount) + SUM(b.openingBal) as amount,\n" +
                "a.groupId as groupId, a.accTypeId as accTypeId FROM tbl_acc_acctype a\n" +
                "LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId\n" +
                "LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId\n" +
                "LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "WHERE d.companyId=:companyId AND d.financialYearId=:financialYearId AND b.accTypeId=:accTypeId AND d.voucherEntryDate >= :fromDate AND d.voucherEntryDate <= :toDate\n" +
                "GROUP BY a.accTypeId\n" +
                "UNION\n" +
                "SELECT  l.ledgerId as ledgerId, a.accTypeName as particular, SUM(l.openingBal) as amount, a.groupId as groupId, a.accTypeId as accTypeId\n" +
                "FROM tbl_acc_ledger l INNER JOIN tbl_acc_acctype a ON l.accTypeId=a.accTypeId\n" +
                "WHERE l.companyId=:companyId AND l.accTypeId=:accTypeId AND l.ledgerId NOT IN(SELECT x.ledgerId FROM tbl_acc_ledger x\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail y ON x.ledgerId=y.ledgerId\n" +
                "INNER JOIN tbl_acc_voucher_entries z ON z.voucherId=y.voucherId\n" +
                "WHERE z.companyId=:companyId AND  z.financialYearId=:financialYearId AND x.accTypeId=:accTypeId AND z.voucherEntryDate >= :fromDate  AND  z.voucherEntryDate <= :toDate\n" +
                "GROUP BY x.accTypeId) GROUP by l.accTypeId) A  GROUP BY A.accTypeId";

        return (AccTrialBalanceDTO) sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .setParameter("accTypeId", accTypeId)
                .setResultTransformer(Transformers.aliasToBean(AccTrialBalanceDTO.class))
                .uniqueResult();
    }
}
