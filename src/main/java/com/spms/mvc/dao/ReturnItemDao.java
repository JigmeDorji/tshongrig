package com.spms.mvc.dao;

import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.entity.ReplaceItem;
import com.spms.mvc.entity.ReturnItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jigmePc on 8/31/2019.
 */
@Repository("returnDao")
public class ReturnItemDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<SaleItemListDTO> getReceiptItemDetails(String receiptNo) {
        String query = "SELECT\n" +
                "b.itemName AS itemName,\n" +
                "a.qty AS qty, \n" +
                "a.itemCode AS itemCode, \n" +
                "b.sellingPrice AS sellingPrice,\n" +
                "(a.qty * b.sellingPrice) AS totalAmount\n" +
                " FROM tbl_inv_sale_record a\n" +
                "INNER JOIN tbl_inv_purchase b on a.itemCode=b.itemCode\n" +
                "WHERE a.receiptMemoNo=:receiptNo";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("receiptNo", receiptNo)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class)).list();
    }
    @Transactional
    public void saveReturnQty(ReturnItem returnItem) {
        sessionFactory.getCurrentSession().save(returnItem);
    }

    @Transactional
    public void updateReturnQtyInPurchase(BigDecimal returnQty, String itemCode) {
        String query = "UPDATE tbl_inv_purchase SET qty=(qty+:returnQty) WHERE itemCode=:itemCode";
        Session session = sessionFactory.getCurrentSession();
         session.createSQLQuery(query)
                .setParameter("returnQty", returnQty)
                .setParameter("itemCode", itemCode).executeUpdate();
    }
    @Transactional
    public void saveReplaceQty(ReplaceItem replaceItem) {
        sessionFactory.getCurrentSession().save(replaceItem);

    }
}
