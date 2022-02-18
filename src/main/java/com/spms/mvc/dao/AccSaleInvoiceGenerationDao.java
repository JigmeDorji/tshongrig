package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.AccSaleInvoiceGenerationDTO;
import com.spms.mvc.dto.AccSaleInvoiceGenerationListDTO;
import com.spms.mvc.entity.AccSaleInvoiceNoGeneration;
import com.spms.mvc.entity.PartyDetail;
import com.spms.mvc.entity.SaleInvoice;
import com.spms.mvc.entity.SaleInvoiceDetail;
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
 * Created by Bcass Sawa on 10/24/2019.
 */
@Repository("accSaleInvoiceGenerationDao")
public class AccSaleInvoiceGenerationDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public String getMaxCountOfInvoiceNo(Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT saleInvoiceNoCounter from tbl_acc_sale_invoice_counter a where a.companyId=:companyId ";
        return (String) session.createSQLQuery(sql)
                .setParameter("companyId", companyId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Boolean isCompanyInvoiceNoExists(Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT count(*) from tbl_acc_sale_invoice_counter a where a.companyId=:companyId";
        return session.createSQLQuery(sql)
                .setParameter("companyId", companyId).uniqueResult()
                .equals(BigInteger.ZERO);
    }

    @Transactional
    public void saveInvoiceNoCounterForLoginCompany(AccSaleInvoiceNoGeneration accSaleInvoiceNoGeneration) {
        sessionFactory.getCurrentSession().save(accSaleInvoiceNoGeneration);
    }

    @Transactional
    public Integer saveToSaleInvoiceTable(SaleInvoice saleInvoice) {
        sessionFactory.getCurrentSession().save(saleInvoice);
        return saleInvoice.getSaleInvoiceId();
    }

    @Transactional
    public void saveToSaleInvoiceDetailTable(SaleInvoiceDetail saleInvoiceDetail) {
        sessionFactory.getCurrentSession().save(saleInvoiceDetail);
    }

    @Transactional
    public void updateSaleInvoiceSerialNo(String nextSerialNo, Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE  tbl_acc_sale_invoice_counter set saleInvoiceNoCounter=:nextSerialNo where companyId=:companyId";
        session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("nextSerialNo", nextSerialNo)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<AccSaleInvoiceGenerationListDTO> getInvoiceDetailsList(Integer saleInvoiceId, Integer companyId) {
        String sql = "SELECT C.particular AS Particular, C.amount AS amount \n" +
                "FROM  tbl_acc_party_detail A \n" +
                "INNER JOIN tbl_acc_sale_invoice B ON A.partyId=B.partyId\n" +
                "INNER JOIN tbl_acc_sale_invoice_detail C ON B.saleInvoiceId=C.saleInvoiceId\n" +
                "WHERE B.saleInvoiceId=:saleInvoiceId AND A.companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("saleInvoiceId", saleInvoiceId)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(AccSaleInvoiceGenerationListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public AccSaleInvoiceGenerationDTO getInvoiceDetails(Integer saleInvoiceId, Integer companyId) {
        String sql = "\n" +
                "SELECT A.partyName AS partyName, \n" +
                "A.partyAddress AS partyAddress,\n" +
                "B.invoiceNo AS invoiceNo,\n" +
                "B.invoiceDate AS invoiceDate,\n" +
                "SUM(C.amount) as amount\n" +
                "FROM  tbl_acc_party_detail A \n" +
                "INNER JOIN tbl_acc_sale_invoice B ON A.partyId=B.partyId\n" +
                "INNER JOIN tbl_acc_sale_invoice_detail C ON B.saleInvoiceId=C.saleInvoiceId\n" +
                "WHERE B.saleInvoiceId=:saleInvoiceId and A.companyId=:companyId  GROUP BY C.saleInvoiceId";
        return (AccSaleInvoiceGenerationDTO) sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("saleInvoiceId", saleInvoiceId)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(AccSaleInvoiceGenerationDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<AccSaleInvoiceGenerationListDTO> getLedgerList(Integer companyId) {
        String sql = "SELECT ledgerName AS ledgerName FROM tbl_acc_ledger where " +
                "accTypeId IN(:directIncome,:otherIncome) AND companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("directIncome", AccountTypeEnum.DIRECT_INCOME.getValue())
                .setParameter("otherIncome", AccountTypeEnum.OTHER_INCOME.getValue())
                .setResultTransformer(Transformers.aliasToBean(AccSaleInvoiceGenerationListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<AccSaleInvoiceGenerationDTO> getPartyList(Integer companyId) {
        String sql = "SELECT partyName FROM tbl_acc_party_detail where companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(AccSaleInvoiceGenerationDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Integer getPartyIdIFExists(Integer companyId, String partyName) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT partyId from tbl_acc_party_detail a where a.companyId=:companyId and a.partyName=:partyName ";
        return (Integer) session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("partyName", partyName)
                .uniqueResult();
    }

    @Transactional
    public void savePartyDetail(PartyDetail partyDetail) {
        sessionFactory.getCurrentSession().save(partyDetail);
    }

    @Transactional(readOnly = true)
    public Integer getMaxPartyId() {
        String sqlQry = "SELECT partyId FROM tbl_acc_party_detail order by partyId DESC limit 1";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.uniqueResult() == null ? 0 : (Integer) hibernate.uniqueResult();
    }

    @Transactional(readOnly = true)
    public AccSaleInvoiceGenerationDTO getPartyDetail(Integer companyId, String partyName) {
        String sql = "SELECT partyAddress, partyContactNo, partyEmail FROM tbl_acc_party_detail" +
                " where companyId=:companyId AND partyName=:partyName";
        return (AccSaleInvoiceGenerationDTO) sessionFactory.getCurrentSession().createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("partyName", partyName)
                .setResultTransformer(Transformers.aliasToBean(AccSaleInvoiceGenerationDTO.class)).uniqueResult();
    }
}
