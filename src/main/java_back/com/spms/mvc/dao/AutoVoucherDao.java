package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.AutoVoucherDTO;
import com.spms.mvc.dto.LoanDTO;
import com.spms.mvc.entity.Loan;
import com.spms.mvc.library.helper.CurrentUser;
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
 * Description: AutoVoucherDao
 * Date:  2021-May-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-08
 * Change Description:
 * Search Tag:
 */
@Repository("autoVoucherDao")
public class AutoVoucherDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getLedgerUnderPayableForRepayment(CurrentUser currentUser) {
        String query = "select ledgerId AS id, ledgerName AS text FROM tbl_acc_ledger WHERE accTypeId IN(:accountTypeId,:accountTypePartyAdvanceId, :accountTypeSecuredLoanId,:accountTypeUnSecuredLoanId) AND companyId=:companyId ";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", AccountTypeEnum.PAYABLE.getValue())
                .setParameter("accountTypePartyAdvanceId", AccountTypeEnum.PARTY_ADVANCE_RECEIVED.getValue())
                .setParameter("accountTypeSecuredLoanId", AccountTypeEnum.SECURED_LOAN.getValue())
                .setParameter("accountTypeUnSecuredLoanId", AccountTypeEnum.UN_SECURED_LOAN.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getAllLedgerUnderExpenseForCost(CurrentUser currentUser) {
        String query = "select ledgerId AS id, ledgerName AS text FROM tbl_acc_ledger WHERE accTypeId IN(:accountTypeDirectCostId,:accountTypeIndirectCostId) AND companyId=:companyId ";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeDirectCostId", AccountTypeEnum.DIRECT_COST.getValue())
                .setParameter("accountTypeIndirectCostId", AccountTypeEnum.INDIRECT_COST.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getAllLedgerUnderAdvancePaid(CurrentUser currentUser) {
        String query = "select ledgerId AS id, ledgerName AS text FROM tbl_acc_ledger WHERE accTypeId=:accountTypeId AND companyId=:companyId ";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", AccountTypeEnum.PARTY_ADVANCE_PAID.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<AutoVoucherDTO> getAllLedgerUnderAdvanceReceived(CurrentUser currentUser) {
        String query = "SELECT a.ledgerId AS id, a.ledgerName AS text, SUM(b.drcrAmount) AS receiptAmount FROM tbl_acc_ledger a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.ledgerId=b.ledgerId\n" +
                "WHERE accTypeId=:accountTypeId AND companyId=:companyId \n" +
                "group by a.ledgerId;";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", AccountTypeEnum.PARTY_ADVANCE_RECEIVED.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(AutoVoucherDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Integer getMaxLoanId() {
        String query = "SELECT loanId FROM tbl_acc_loan order by loanId desc limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public boolean checkLoanIdExists(Integer loanId) {
        String query = "SELECT COUNT(*) FROM tbl_acc_loan WHERE loanId=:loanId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setParameter("loanId", loanId)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional
    public void saveLoanDetail(Loan loan) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(loan);
    }

    @Transactional(readOnly = true)
    public List<LoanDTO> getLoanDetails(Integer companyId) {
        String query = "SELECT loanId,loanLedgerName, loanAccNo, bank,branch,monthlyEmi FROM tbl_acc_loan WHERE companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(LoanDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<AutoVoucherDTO> getLoanLedgerList(CurrentUser currentUser) {
        String query = "SELECT a.ledgerId AS id, a.ledgerName AS text FROM tbl_acc_ledger a WHERE accTypeId=:accountTypeId AND companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", AccountTypeEnum.SECURED_LOAN.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(AutoVoucherDTO.class)).list();
    }
}
