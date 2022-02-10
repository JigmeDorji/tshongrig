package com.spms.mvc.dao;

import com.spms.mvc.dto.AccTypeDTO;
import com.spms.mvc.dto.LedgerDTO;
import com.spms.mvc.entity.Bank;
import com.spms.mvc.entity.Ledger;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jigmePc on 5/5/2019.
 */
@Repository("LedgerDao")
public class LedgerDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void save(Ledger ledger) {
        sessionFactory.getCurrentSession().save(ledger);
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getAccTypeList() {
        String query = "SELECT B.accTypeId AS value,\n" +
                "B.accTypeName AS text FROM tbl_acc_acctype B  ORDER BY B.accTypeName asc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();


    }

    @Transactional(readOnly = true)
    public AccTypeDTO isBankAccType(Integer accTypeId) {
        String query = "SELECT B.accTypeId AS accTypeId,\n" +
                "B.accTypeName AS accTypeName,B.isBankAccLedger AS isBankAccLedger  " +
                "FROM tbl_acc_acctype B WHERE  B.accTypeId=:accTypeId";
        Session session = sessionFactory.getCurrentSession();
        return (AccTypeDTO) session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(AccTypeDTO.class))
                .setParameter("accTypeId", accTypeId).uniqueResult();


    }

    @Transactional(readOnly = true)
    public List<LedgerDTO> getLedgerList(Integer companyId) {
        String query = "SELECT accTypeId AS accTypeId,ledgerId AS ledgerId,ledgerName AS ledgerName\n" +
                " FROM tbl_acc_ledger WHERE companyId=:companyId order by ledgerName asc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(LedgerDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public LedgerDTO getLedgerDetails(Integer ledgerId) {
        String query = "SELECT A.ledgerId AS ledgerId,\n" +
                "       A.ledgerName AS ledgerName,\n" +
                "       A.accTypeId AS accTypeId,\n" +
                "       C.bankId AS bankId,\n" +
                "       C.reconciliationDate AS reconciliationDate,\n" +
                "       C.bankAccHolderDetail AS bankAccHolderDetail,\n" +
                "       C.accHolderName AS accHolderName,\n" +
                "       C.accNo AS accNo,\n" +
                "       C.bankName AS bankName,\n" +
                "       C.branch AS branch,\n" +
                "       A.openingBal AS openingBal,\n" +
                "       B.isBankAccLedger AS isBankAccLedger\n" +
                "FROM tbl_acc_ledger A\n" +
                "INNER JOIN tbl_acc_acctype B ON A.accTypeId=B.accTypeId \n" +
                "LEFT JOIN tbl_acc_bank C ON C.bankId=A.bankId\n" +
                "WHERE A.ledgerId=:ledgerId";
        Session session = sessionFactory.getCurrentSession();
        return (LedgerDTO) session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(LedgerDTO.class))
                .setParameter("ledgerId", ledgerId).uniqueResult();
    }

    @Transactional
    public void updateLedgerDetails(Ledger ledger) {
        sessionFactory.getCurrentSession().update(ledger);
    }

    @Transactional(readOnly = true)
    public Boolean isLedgerNameExists(String ledgerName, Integer companyId) {
String sqlQry = "SELECT(EXISTS(SELECT * from tbl_acc_ledger b where b.ledgerName=:ledgerName AND companyId=:companyId))";
    return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("ledgerName", ledgerName)
                .setParameter("companyId", companyId)
                .uniqueResult().equals(BigInteger.ONE);
    }

    @Transactional(readOnly = true)
    public Boolean isLedgerUsed(Integer ledgerId, Integer companyId) {
        String sqlQry = "SELECT(EXISTS(SELECT * from tbl_acc_voucher_entries_detail b INNER JOIN tbl_acc_voucher_entries c ON b.voucherId=c.voucherId WHERE b.ledgerId=:ledgerId AND c.companyId=:companyId))";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", companyId)
                .uniqueResult().equals(BigInteger.ONE);
    }

    @Transactional()
    public void deleteLedgerByLedgerId(Integer ledgerId) {
        String sqlQry = "DELETE FROM tbl_acc_ledger WHERE ledgerId=:ledgerId";
        sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId).executeUpdate();
    }

    @Transactional
    public String saveLedgerWithReturnLedgerId(Ledger ledger) {
        final Session session = sessionFactory.getCurrentSession();
        session.evict(ledger);
        session.saveOrUpdate(ledger);
        return ledger.getLedgerId();
    }

    @Transactional(readOnly = true)
    public String getMaxLedgerId() {
        String sqlQry = "SELECT max(cast(ledgerId as signed))  FROM tbl_acc_ledger";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.uniqueResult() == null ? "0" : hibernate.uniqueResult().toString();
    }

    @Transactional
    public Integer saveBankDetail(Bank bank) {
        sessionFactory.getCurrentSession().saveOrUpdate(bank);
        return bank.getBankId();
    }

    @Transactional(readOnly = true)
    public Integer getMaxBankId() {
        String sqlQry = "SELECT bankId FROM tbl_acc_bank order by bankId DESC limit 1";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.uniqueResult() == null ? 0 : (Integer) hibernate.uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getLedgerIdByLedgerName(Integer companyId, String ledgerName) {
        String sqlQry = "select ledgerId from tbl_acc_ledger where companyId=:companyId and ledgerName=:ledgerName";
        return (String) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("ledgerName", ledgerName)
                .setParameter("companyId", companyId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getLedgerIdByAccountTypeId(Integer accountTypeId, Integer companyId) {
        String sqlQry = "select ledgerId from tbl_acc_ledger where accTypeId=:accountTypeId AND companyId=:companyId limit 1";
        return (String) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("accountTypeId", accountTypeId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getNameByLedgerId(String ledgerId, Integer companyId, Integer financialYearId) {
        String sqlQry = "select ledgerName from tbl_acc_ledger where companyId=:companyId and ledgerId=:ledgerId";
        return (String) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId)
//                .setParameter("financialYearId", financialYearId)
                .setParameter("companyId", companyId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getCashLedgerByCashAccountHead(Integer accTypeId, Integer companyId) {
        String sqlQry = "select ledgerName from tbl_acc_ledger where companyId=:companyId and accTypeId=:accTypeId";
        return (String) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("accTypeId", accTypeId)
                .setParameter("companyId", companyId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double checkIsOpeningBalance(Integer ledgerId, Integer companyId) {
        String sqlQry = "select openingBal from tbl_acc_ledger where companyId=:companyId and ledgerId=:ledgerId";
        return (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", companyId).uniqueResult();
    }

    @Transactional
    public void updateOpeningBalance(String ledgerId, Integer companyId, Double amount) {
        String sqlQry = "Update tbl_acc_ledger set openingBal=+:amount where companyId=:companyId and ledgerId=:ledgerId";
        sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", companyId)
                .setParameter("amount", amount)
                .executeUpdate();
    }
}
