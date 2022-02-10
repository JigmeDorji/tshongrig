package com.spms.mvc.dao;

import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.library.helper.ResponseMessage;
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
 * Created by jigmePc on 8/25/2019.
 */
@Repository("viewItemDetailDao")
public class ViewItemDetailDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<PurchaseDTO> getItemDetail(Integer companyId, String itemCode, Integer financialYearId, Date asOnDate) {
        String query = "call sp_acc_fetch_purchase_sale_details(:companyId,:itemCode,:financialYearId,:asOnDate)";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("asOnDate", asOnDate)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).list();
    }

    @Transactional
    public void deletePurchaseRelatedVoucher(Integer voucherNo, Integer companyId, Integer financialYearId,String itemCode) {
        String query1 = "select id from tbl_inv_purchase_a a\n" +
                "inner join tbl_inv_purchase b on a.purchaseId=b.purchaseId\n" +
                "where a.companyId=:companyId and a.financialYearId=:financialYearId and a.purchaseVoucherNo=:voucherNo and a.itemCode=:itemCode";
        Session session = sessionFactory.getCurrentSession();
        Integer id = (Integer) session.createSQLQuery(query1)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("itemCode", itemCode)
                .uniqueResult();

        String query = "update tbl_inv_purchase_a set cmdFlag='D' where id=:id";
        Session session1 = sessionFactory.getCurrentSession();
        session1.createSQLQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public void updateQtyInPurcahse(Integer companyId, Integer financialYearId, String itemCode, BigDecimal qty) {
        String query = "update tbl_inv_purchase set qty=qty-:qty where itemCode=:itemCode and companyId=:companyId and financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("qty", qty)
                .executeUpdate();
    }
}
