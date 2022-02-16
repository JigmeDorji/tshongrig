package com.spms.mvc.dao;

import com.spms.mvc.dto.PurchaseDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 15-Dec-16.
 */
@Repository("viewItemDao")
public class ViewItemDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<PurchaseDTO> getItemAvailable(Integer companyId, Date asOnDate) {
        String query = "call sp_acc_get_inv_closing_bal(:companyId,null,:asOnDate)";

//        String query = "Select partNo,\n" +
//                "purchaseId,\n" +
//                "locationId,\n" +
//                "temp_purchase.itemCode,\n" +
//                "itemName, \n" +
//                "(bigQty-ifnull(soldQty,0)) as qtyBig,\n" +
//                "unitName,\n" +
//                "sellingPrice as amount\n" +
//                "FROM (SElECT partNo,purchaseId,itemCode,itemName, SUM(bigQty) AS bigQty,unitName, SUM(amount),locationId \n" +
//                "FROM (SELECT a.partNo as partNo,\n" +
//                "a.purchaseId As purchaseId,\n" +
//                "a.itemCode AS itemCode,\n" +
//                "concat(a.itemName,' : ',a.type) AS itemName,\n" +
//                "a.cmdFlag,\n" +
//                "(SELECT (CASE WHEN a.cmdFlag='D' then 0 ELSE  a.qty END)) AS bigQty,\n" +
//                "b.unitName as unitName,\n" +
//                "a.sellingPrice  AS sellingPrice,\n" +
//                "a.locationId AS locationId\n" +
//                "FROM tbl_inv_purchase_a a                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               \n" +
//                "inner join tbl_inv_unit b on a.unitId=b.unitId\n" +
//                "where a.companyId=:companyId and purchaseDate<=:asOnDate) AS A group by a.itemCode) as temp_purchase\n" +
//                "left join #after this part will get sold item detail with cost of goods sold\n" +
//                "(SELECT SUM(a.qty) as soldQty, a.itemCode \n" +
//                "FROM tbl_inv_sale_record_detail a inner join tbl_inv_sale_record b on a.saleRecordId=b.saleRecordId\n" +
//                "where b.companyId=:companyId and b.saleDate<=:asOnDate group by itemCode) as temp_sale on temp_purchase.itemCode=temp_sale.itemCode";

        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("asOnDate",asOnDate)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public String getItemName(String itemCode, Integer companyId) {
        String query = "SELECT itemName FROM tbl_inv_purchase where itemcode=:itemCode and companyId=:companyId limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId).
                uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<PurchaseDTO> getAllBrandItemList(Integer companyId) {
        String query = "SELECT p.itemCode, p.itemName,l.locationId,l.description FROM tbl_inv_purchase p\n" +
                "INNER JOIN tbl_item_code i ON p.brandId=i.brandId \n" +
                "INNER JOIN tbl_locationsetup l ON l.locationSetUpId=p.locationId\n" +
                "where p.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<PurchaseDTO> getBrandItemListByBrandId(Integer brandId, Integer companyId) {
        String query = "SELECT p.itemCode, p.itemName,l.locationId,l.description FROM tbl_inv_purchase p\n" +
                "INNER JOIN tbl_item_code i ON p.brandId=i.brandId \n " +
                "INNER JOIN tbl_locationsetup l ON l.locationSetUpId=p.locationId\n " +
                "where p.brandId=:brandId AND p.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("brandId", brandId)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Double getTotalStockBal(Integer companyId) {
        String query = "(SUM(a.costPrice * a.qty)+ifnull((SELECT SUM(ifnull(c.returnQty,0)) \n" +
                "from tbl_inv_return_item c where c.itemcode=a.itemCode),0))\n" +
                "FROM tbl_inv_purchase_a a\n" +
                "inner join tbl_inv_purchase c on a.itemCode=c.itemCode\n" +
                "INNER JOIN tbl_locationsetup b ON a.locationId=b.locationSetupId where a.companyId=:companyId";

//        String query = "SELECT \n" +
//                "\tSUM(a.costPrice * a.qty+ifnull((SELECT SUM(ifnull(c.returnQty,0)) \n" +
//                "    from tbl_inv_return_item c where c.itemcode=a.itemCode),0))\n" +
//                "\tFROM tbl_inv_purchase a\n" +
//                "\tINNER JOIN tbl_locationsetup b ON a.locationId=b.locationSetupId where a.companyId=:companyId \n";
        Session session = sessionFactory.getCurrentSession();
        return (Double)session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .uniqueResult();
    }
}
