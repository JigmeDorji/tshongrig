package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.MoneyReceiptDTO;
import com.spms.mvc.entity.MoneyReceipt;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jigme.dorji on 10/27/2020.
 */
@Repository("moneyReceiptDao")
public class MoneyReceiptDao{
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getPartyLedgerList(Integer companyId) {
        String query = "SELECT ledgerId AS id,ledgerName AS text FROM tbl_acc_ledger where accTypeId=:accTypeId and companyId=:companyId and ledgerName!='TDS Receivable'";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("accTypeId", AccountTypeEnum.RECEIVABLE.getValue())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();

    }

    @Transactional
    public void saveMoneyReceipt(MoneyReceipt moneyReceipt) {
        sessionFactory.getCurrentSession().saveOrUpdate(moneyReceipt);
    }

    @Transactional(readOnly = true)
    public String getCurrentReceiptNo(Integer companyId) {
        String query = "SELECT MAX(receiptNo) FROM tbl_acc_money_receipt where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getBankList(Integer companyId) {
        String query = "SELECT ledgerId AS id,ledgerName AS text FROM tbl_acc_ledger where accTypeId IN (:accTypeId,:accTypeOverDraftId) and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("accTypeId", AccountTypeEnum.BANK.getValue())
                .setParameter("accTypeOverDraftId", AccountTypeEnum.BANK_OVER_DRAFT.getValue())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public List<MoneyReceiptDTO> getMoneyReceiptDetails(String moneyReceiptNo, Integer companyId) {
        String query = "SELECT b.ledgerName FROM tbl_acc_money_receipt a\n" +
                "INNER JOIN tbl_acc_ledger b on a.partyLedgerId=b.ledgerId \n" +
                "where a.receiptNo=:moneyReceiptNo and a.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("moneyReceiptNo", moneyReceiptNo)
                .setResultTransformer(Transformers.aliasToBean(MoneyReceiptDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Double getReceivableAmt(Integer partyLedgerId, Integer companyId) {
        String query = "select abs(sum(b.drcrAmount)) from tbl_acc_voucher_entries a\n" +
                "inner join tbl_acc_voucher_entries_detail b on b.voucherId =a.voucherId\n" +
                "inner join tbl_acc_ledger c on c.ledgerId=b.ledgerId\n" +
                "where b.ledgerId=:partyLedgerId and a.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (Double) session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("partyLedgerId", partyLedgerId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getMobilizationPartyLedgerList(Integer companyId) {
        String query = "SELECT ledgerId AS id,ledgerName AS text FROM tbl_acc_ledger where accTypeId=:accTypeId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("accTypeId", AccountTypeEnum.MOBILIZATION_ADV.getValue())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getMaterialAdvPartyLedgerList(Integer companyId) {
        String query = "SELECT ledgerId AS id,ledgerName AS text FROM tbl_acc_ledger where accTypeId=:accTypeId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("accTypeId", AccountTypeEnum.MATERIAL_ADV.getValue())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getAllLedgerUnderIncome(CurrentUser currentUser) {
        String query = "select ledgerId AS id, ledgerName AS text FROM tbl_acc_ledger WHERE accTypeId =:incomeAccountType AND companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("incomeAccountType", AccountTypeEnum.DIRECT_INCOME.getValue())
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }
}
