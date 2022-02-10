package com.spms.mvc.dao;

import com.spms.mvc.dto.AccProfitAndLossReportDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2/29/2020.
 */
@Repository("ledgerGroupListDao")
public class LedgerGroupListDao{
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<AccProfitAndLossReportDTO> getCurrentAssetList(Integer companyId,Integer accountTypeId) {
        String sqlQry = "SELECT C.ledgerId,  \n" +
                "C.ledgerName AS ledgerName ,\n" +
                "CASE WHEN(B.debitAmount IS NULL OR B.debitAmount =0) \n" +
                "THEN SUM(B.creditAmount) ELSE SUM(B.debitAmount) END AS amount,\n" +
                "d.accTypeName AS accTypeName, \n" +
                "C.ledgerId AS ledgerId, \n" +
                "d.accTypeId AS accTypeId \n" +
                "FROM tbl_acc_voucher A\n" +
                "INNER JOIN  tbl_acc_voucher_detail B ON A.voucherId=B.voucherId \n" +
                "INNER JOIN tbl_acc_ledger C on C.ledgerId=B.ledgerId\n" +
                "INNER JOIN tbl_acc_acctype d on d.accTypeId=C.accTypeId \n" +
                "where d.accHeadTypeEffect='BS' AND d.accTypeId IN(:accountTypeId)  GROUP BY C.ledgerId";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("accountTypeId",accountTypeId)
                .setResultTransformer(Transformers.aliasToBean(AccProfitAndLossReportDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public List<AccProfitAndLossReportDTO> getLedgerGroupList(Integer companyId, Integer accountTypeId, CurrentUser currentUser) {
        String sqlQry = "select ledgerId, particular,sum(amount) as amount,groupId  from (SELECT b.ledgerId, b.ledgerName as particular, b.openingBal as amount,f.groupId as groupId\n" +
                "FROM tbl_acc_acctype a\n" +
                "INNER JOIN tbl_acc_ledger b on a.accTypeId=b.accTypeId\n" +
                "left JOIN tbl_acc_voucher_entries_detail c on c.ledgerId=b.ledgerId\n" +
                "inner join tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "inner join tbl_acc_acctype e on e.accTypeId=a.accTypeId\n" +
                "inner join tbl_acc_group f on f.groupId=e.groupId\n" +
                "where a.accTypeId=:accountTypeId and b.companyId=:companyId and d.voucherEntryDate<:toDate GROUP BY b.ledgerId\n" +
                "UNION \n" +
                "SELECT  b.ledgerId, b.ledgerName as particular,ifnull(SUM(c.drcrAmount*-1),0) as amount,f.groupId as groupId\n" +
                "FROM tbl_acc_acctype a\n" +
                "INNER JOIN tbl_acc_ledger b on a.accTypeId=b.accTypeId\n" +
                "left JOIN tbl_acc_voucher_entries_detail c on c.ledgerId=b.ledgerId\n" +
                "inner join tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "inner join tbl_acc_acctype e on e.accTypeId=a.accTypeId\n" +
                "inner join tbl_acc_group f on f.groupId=e.groupId\n" +
                "where a.accTypeId=:accountTypeId and b.companyId=:companyId and d.voucherEntryDate<:toDate GROUP BY b.ledgerId\n" +
                "UNION#this will include ledger with only opening bal without any acc transation\n" +
                "select a.ledgerId,a.ledgerName as particular,ifnull(a.openingBal,0) as amount,c.groupId\n" +
                "from tbl_acc_ledger a\n" +
                "inner join tbl_acc_acctype b on a.accTypeId=b.accTypeId\n" +
                "inner join tbl_acc_group c on c.groupId=b.groupId\n" +
                "left join tbl_acc_voucher_entries_detail d on d.ledgerId=a.ledgerId\n" +
                "where a.accTypeId=:accountTypeId and a.companyId=:companyId and d.drcrAmount is null\n" +
                ") as A  group by ledgerId";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("accountTypeId",accountTypeId)
//                .setParameter("fromDate", DateUtil.toDate(currentUser.getFinancialYearFrom()))
                .setParameter("toDate",DateUtil.toDate(currentUser.getFinancialYearTo()))
                .setResultTransformer(Transformers.aliasToBean(AccProfitAndLossReportDTO.class))
                .list();
    }
}
