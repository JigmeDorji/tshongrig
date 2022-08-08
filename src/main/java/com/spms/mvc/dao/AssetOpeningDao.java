package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.OpeningAndBuyingDTO;
import com.spms.mvc.entity.FaPurchase;
import com.spms.mvc.entity.FaPurchaseDetail;
import com.spms.mvc.entity.FaPurchaseMaster;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Description: OpeningAndBuyingDao
 * Date:  2021-Sep-16
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Sep-16
 * Change Description:
 * Search Tag:
 */
@Repository("assetOpeningDao")
public class AssetOpeningDao extends BaseDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getFixedAssetGroupList() {
        String query = "SELECT accTypeId as valueInteger, accTypeName as text FROM tbl_acc_acctype where groupId=:groupTypeId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("groupTypeId", AccountTypeEnum.NON_CURRENT_ASSET.getValue())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxFaPurchaseId(Integer companyId) {
        String query = "SELECT MAX(faPurchaseId) FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_purchase_mater b ON a.purchaseMasterId=b.purchaseMasterId\n" +
                " WHERE b.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public void save(FaPurchase fa_purchase) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(fa_purchase);
    }

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingDTO> getItemSuggestionList(Integer companyId) {
        String query = "SELECT b.assetDetailId, b.particular FROM tbl_fa_item_setup a \n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetId=b.assetId\n" +
                "WHERE a.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxMasterFaPurchaseId(Integer companyId) {
        String query = "SELECT MAX(purchaseMasterId) FROM tbl_fa_purchase_mater WHERE companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public void saveToMasterTable(FaPurchaseMaster faPurchaseMaster) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(faPurchaseMaster);
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxFaPurchaseDetailId(Integer companyId) {
        String query = "SELECT MAX(a.faPurchaseDetailId) FROM tbl_fa_purchase_detail a\n" +
                "INNER JOIN tbl_fa_purchase b ON a.faPurchaseId=b.faPurchaseId\n" +
                "INNER JOIN tbl_fa_purchase_mater c ON b.purchaseMasterId=c.purchaseMasterId\n" +
                " WHERE c.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public void savePurchaseDetail(FaPurchaseDetail faPurchaseDetail) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(faPurchaseDetail);
    }

    @Transactional(readOnly = true)
    public String getMaxAssetCodeSerialNo(Integer companyId) {
        String query = "SELECT assetCode FROM tbl_fa_purchase_detail a\n" +
                "INNER JOIN tbl_fa_purchase b ON a.faPurchaseId=b.faPurchaseId\n" +
                "INNER JOIN tbl_fa_purchase_mater c ON b.purchaseMasterId=c.purchaseMasterId\n" +
                " WHERE c.companyId=:companyId order by a.faPurchaseDetailId desc limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingDTO> getInvoiceDetailList(String purchaseInvoiceNo, Integer companyId) {
        String query = "SELECT SUM(a.qty*a.rate) as amount, e.accTypeId  FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetDetailId=b.assetDetailId\n" +
                "INNER JOIN tbl_fa_item_setup c ON c.assetId=b.assetId\n" +
                "INNER JOIN tbl_fa_purchase_mater d ON d.purchaseMasterId=a.purchaseMasterId\n" +
                "INNER JOIN tbl_fa_group e ON c.assetClassId=e.assetClassId\n" +
                "WHERE c.companyId=:companyId AND d.purchaseInvoiceNo=:purchaseInvoiceNo\n" +
                "GROUP BY c.assetClassId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("purchaseInvoiceNo", purchaseInvoiceNo)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingDTO> getOpeningInvoiceDetailList(Integer companyId) {

        Session session = sessionFactory.getCurrentSession();

        String query = "SELECT max(purchaseMasterId) FROM tbl_fa_purchase_mater WHERE companyId=:companyId";
        BigInteger purchaseMasterId = (BigInteger) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();

        String sqlQuery = "SELECT a.openingBalance as amount, e.accTypeId  FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetDetailId=b.assetDetailId\n" +
                "INNER JOIN tbl_fa_item_setup c ON c.assetId=b.assetId\n" +
                "INNER JOIN tbl_fa_purchase_mater d ON d.purchaseMasterId=a.purchaseMasterId\n" +
                "INNER JOIN tbl_fa_group e ON c.assetClassId=e.assetClassId\n" +
                "WHERE d.purchaseMasterId=:purchaseMasterId";

        return session.createSQLQuery(sqlQuery)
                .setParameter("purchaseMasterId", purchaseMasterId)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingDTO> loadAssetOpeningList(BigInteger faPurchaseId) {
        String sqlQuery = "SELECT b.assetId,b.assetDetailId, " +
                "  a.faPurchaseId," +
                "  d.purchaseMasterId,\n" +
                "  b.particular,\n" +
                "  a.purchaseDate,\n" +
                "  a.openingBalance as amount, \n" +
                "  a.depreciatedValue,\n" +
                "  a.rate,\n" +
                "  a.qty,\n" +
                "  e.accTypeId \n" +
                " FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetDetailId=b.assetDetailId\n" +
                "INNER JOIN tbl_fa_item_setup c ON c.assetId=b.assetId\n" +
                "INNER JOIN tbl_fa_purchase_mater d ON d.purchaseMasterId=a.purchaseMasterId\n" +
                "INNER JOIN tbl_fa_group e ON c.assetClassId=e.assetClassId\n" +
                "WHERE a.faPurchaseId=:faPurchaseId";

        return sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("faPurchaseId", faPurchaseId)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingDTO.class)).list();
    }

    @Transactional
    public void deleteItemFromDetail(BigInteger faPurchaseId) {
        String sqlQuery = "DELETE FROM tbl_fa_purchase_detail where faPurchaseId=:faPurchaseId";

        sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("faPurchaseId", faPurchaseId)
                .executeUpdate();
    }

    @Transactional
    public void deleteItem(BigInteger faPurchaseId) {
        String sqlQuery = "DELETE FROM tbl_fa_purchase where faPurchaseId=:faPurchaseId";

        sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("faPurchaseId", faPurchaseId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public boolean isOpeningAssetCount(BigInteger purchaseMasterId) {
        String sqlQuery = "SELECT COUNT(*) FROM tbl_fa_purchase where purchaseMasterId=:purchaseMasterId";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("purchaseMasterId", purchaseMasterId)
                .uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional
    public void deleteItemFromMaster(BigInteger purchaseMasterId) {
        String sqlQuery = "DELETE FROM tbl_fa_purchase_mater where purchaseMasterId=:purchaseMasterId";
        sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("purchaseMasterId", purchaseMasterId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingDTO> loadAssetBuyingList(Integer voucherNo, BigInteger purchaseMasterId) {
        String sqlQuery = "SELECT b.assetId,d.purchaseMasterId, \n" +
                "  b.assetDetailId,\n" +
                "  a.faPurchaseId, \n" +
                "  b.particular,\n" +
                "  a.purchaseDate,\n" +
                "  a.rate,\n" +
                "  a.qty,\n" +
                "  d.purchaseInvoiceNo,\n" +
                "  d.paidInType,\n" +
                "  e.accTypeId,\n" +
                "  d.amtReceived,\n" +
                "  f.partyName\n" +
                " FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetDetailId=b.assetDetailId\n" +
                "INNER JOIN tbl_fa_item_setup c ON c.assetId=b.assetId\n" +
                "INNER JOIN tbl_fa_purchase_mater d ON d.purchaseMasterId=a.purchaseMasterId\n" +
                "INNER JOIN tbl_fa_group e ON c.assetClassId=e.assetClassId\n" +
                "LEFT JOIN tbl_acc_party_detail f ON d.partyId=f.partyId\n" +
                "WHERE d.purchaseMasterId=:purchaseMasterId and d.voucherNo=:voucherNo";

        return sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("voucherNo", voucherNo)
                .setParameter("purchaseMasterId", purchaseMasterId)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingDTO.class)).list();
    }

    public boolean checkIsOpening(BigInteger faPurchaseId) {
        String sqlQuery = "";

        return sessionFactory.getCurrentSession().createSQLQuery(sqlQuery)
                .setParameter("faPurchaseId", faPurchaseId).uniqueResult().equals(BigInteger.ZERO);
    }
}
