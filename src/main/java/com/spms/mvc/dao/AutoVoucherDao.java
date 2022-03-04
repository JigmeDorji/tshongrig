package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.AutoVoucherDTO;
import com.spms.mvc.dto.LoanDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.LedgerWiseCostType;
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
    public List<DropdownDTO> getAllLedgerUnderAccountType(CurrentUser currentUser, Integer accountTypeId) {
        String query = "select ledgerId AS id, ledgerName AS text FROM tbl_acc_ledger WHERE accTypeId=:accountTypeId AND companyId=:companyId ";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", accountTypeId)
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

    @Transactional(readOnly = true)
    public List<AutoVoucherDTO> getPayableList(CurrentUser currentUser) {
        String query = "SELECT a.ledgerId AS id, a.ledgerName AS text FROM tbl_acc_ledger a WHERE accTypeId=:accountTypeId AND companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", AccountTypeEnum.PAYABLE.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(AutoVoucherDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Boolean fetchTDSPayableList(CurrentUser currentUser, String ledgerName) {
        String query = "SELECT count(ledgerId) FROM tbl_acc_ledger WHERE ledgerName=:ledgerName AND companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("ledgerName", ledgerName)
                .setParameter("companyId", currentUser.getCompanyId())
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getAllLPayableLedgerExcludingTds(CurrentUser currentUser, String ledgerId) {
        String query = "select ledgerId AS id, ledgerName AS text FROM tbl_acc_ledger WHERE accTypeId IN(:accountTypeId) AND companyId=:companyId AND ledgerId <> :ledgerId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("accountTypeId", AccountTypeEnum.PAYABLE.getValue())
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Double getAmountByLedgerId(CurrentUser currentUser, String ledgerId) {
        String query = "SELECT abs(sum(b.drcrAmount)) AS receiptAmount FROM tbl_acc_ledger a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.ledgerId=b.ledgerId\n" +
                "WHERE b.ledgerId=:ledgerId AND companyId=:companyId \n" +
                "group by a.ledgerId";
        return (Double) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", currentUser.getCompanyId())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxId(Integer companyId) {
        String query = "SELECT max(id) FROM tbl_acc_ledger_wise_cost_type where companyId=:companyId";
        return (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public boolean checkLedgerExists(String ledgerId) {
        String query = "SELECT count(ledgerId) FROM tbl_acc_ledger_wise_cost_type where ledgerId=:ledgerId";
        return (Boolean) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("ledgerId", ledgerId)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public BigInteger getIdByLedgerId(String ledgerId) {
        String query = "SELECT id FROM tbl_acc_ledger_wise_cost_type where ledgerId=:ledgerId";
        return (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("ledgerId", ledgerId)
                .uniqueResult();
    }

    @Transactional
    public void saveOrUpdate(LedgerWiseCostType ledgerWiseCostType) {
        sessionFactory.getCurrentSession().saveOrUpdate(ledgerWiseCostType);
    }

    @Transactional(readOnly = true)
    public Integer getCostTypeByLedgerId(CurrentUser currentUser, String ledgerId) {
        String query = "SELECT costTypeId FROM tbl_acc_ledger_wise_cost_type where ledgerId=:ledgerId and companyId=:companyId";
        return (Integer) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", currentUser.getCompanyId())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<VoucherDetailDTO> getVoucherDetail(Integer voucherNo, CurrentUser currentUser, Integer type) {
        String query = "SELECT  c.ledgerName as description, b.drcrAmount \n" +
                "   FROM  tbl_acc_voucher_entries a \n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b \n" +
                "INNER JOIN tbl_acc_ledger c ON b.ledgerId=c.ledgerId\n" +
                "ON a.voucherId=b.voucherId\n" +
                "WHERE a.companyId=:companyId and a.financialYearId=:financialYearId and a.voucherNo=:voucherNo and voucherTypeId=:voucherTypeId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .setParameter("voucherTypeId", type)
                .setResultTransformer(Transformers.aliasToBean(VoucherDetailDTO.class)).list();
    }
}
