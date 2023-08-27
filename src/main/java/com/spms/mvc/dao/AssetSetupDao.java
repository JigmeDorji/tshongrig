package com.spms.mvc.dao;

import com.spms.mvc.dto.AssetSetupDTO;
import com.spms.mvc.entity.FaGroupSerial;
import com.spms.mvc.entity.FaItemSetup;
import com.spms.mvc.entity.FaItemSetupDetail;
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
 * Description: AssetSetupDao
 * Date:  2021-Oct-08
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-08
 * Change Description:
 * Search Tag:
 */

@Repository("assetSetupDao")
public class AssetSetupDao extends BaseDao {
    @Autowired
    SessionFactory sessionFactory;


    @Transactional(readOnly = true)
    public Boolean checkIsAssetCodeExists(Integer groupId, Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT count(*) FROM tbl_fa_group_serial a\n" +
                "INNER JOIN tbl_fa_group b on a.assetClassId=b.assetClassId\n" +
                "WHERE b.accTypeId=:groupId AND a.companyId=:companyId";
        return session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("groupId", groupId)
                .uniqueResult().equals(BigInteger
                        .ZERO);
    }

    @Transactional(readOnly = true)
    public Integer getMaxAssetSerialId() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT MAX(assetSerialId) FROM tbl_fa_group_serial";
        return (Integer) session.createSQLQuery(sql)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public AssetSetupDTO getParentAssetCodeByGroupId(Integer groupId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT assetClassId, parentAssetCode FROM tbl_fa_group WHERE accTypeId=:groupId";
        return (AssetSetupDTO) session.createSQLQuery(sql)
                .setParameter("groupId", groupId)
                .setResultTransformer(Transformers.aliasToBean(AssetSetupDTO.class))
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxGroupSerialNo(Integer companyId, Integer assetClassId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT assetNoSerial FROM tbl_fa_group_serial WHERE companyId=:companyId and assetClassId=:assetClassId";
        return (BigInteger) session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("assetClassId", assetClassId)
                .uniqueResult();
    }

    @Transactional
    public void save(FaGroupSerial faGroupSerial) {
        sessionFactory.getCurrentSession().saveOrUpdate(faGroupSerial);
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxAssetId() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT MAX(assetId) FROM tbl_fa_item_setup";
        return (BigInteger) session.createSQLQuery(sql)
                .uniqueResult();
    }

    @Transactional
    public void saveAssetItem(FaItemSetup faItemSetup) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(faItemSetup);
    }

    @Transactional
    public void updateGroupSerialNo(BigInteger serialNo, Integer companyId, Integer assetClassId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "update tbl_fa_group_serial set assetNoSerial=:serialNo WHERE companyId=:companyId AND assetClassId=:assetClassId";
        session.createSQLQuery(sql)
                .setParameter("serialNo", serialNo)
                .setParameter("companyId", companyId)
                .setParameter("assetClassId", assetClassId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getDescriptionList(Integer companyId) {
        String query = "SELECT assetId as valueBigInteger, description as text FROM tbl_fa_item_setup where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public BigInteger getMaxAssetDetailId() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT MAX(assetDetailId) FROM tbl_fa_item_setup_detail";
        return (BigInteger) session.createSQLQuery(sql)
                .uniqueResult();
    }

    @Transactional
    public void saveAssetItemDetail(FaItemSetupDetail faItemSetupDetail) {
        sessionFactory.getCurrentSession().saveOrUpdate(faItemSetupDetail);
    }

    @Transactional(readOnly = true)
    public List<AssetSetupDTO> getFixedAssetDetail(Integer companyId) {
        String query = "SELECT a.assetId,\n" +
                " e.faPurchaseId, \n" +
                " a.description,\n" +
                " b.assetDetailId,\n" +
                " b.particular,\n" +
                " d.accTypeName AS groupName,\n" +
                " count(e.faPurchaseId) as qty \n" +
                "FROM tbl_fa_item_setup  a \n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetId=b.assetId\n" +
                "INNER JOIN tbl_fa_group c ON c.assetClassId=a.assetClassId\n" +
                "INNER JOIN tbl_acc_acctype d ON d.accTypeId=c.accTypeId\n" +
                "LEFT JOIN tbl_fa_purchase e ON e.assetDetailId=b.assetDetailId\n" +
                "LEFT JOIN tbl_fa_purchase_detail f ON f.faPurchaseId=e.faPurchaseId\n" +
                " where a.companyId=:companyId\n" +
                " group by b.assetDetailId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(AssetSetupDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<AssetSetupDTO> getAssetItemDetail(BigInteger faPurchaseId) {
        String query = "SELECT particular,\n" +
                "rate, \n" +
                "(case when b.status='N' then 'New'   \n" +
                "when b.status='S' then 'Sold'   \n" +
                "when b.status='D' then 'Fully Depreciated'\n" +
                "ELSE 'Partially Depreciated'  end) as status,\n" +
                "assetCode\n" +
                "FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_purchase_detail b ON a.faPurchaseId=b.faPurchaseId\n" +
                "INNER JOIN tbl_fa_item_setup_detail c ON c.assetDetailId=a.assetDetailId\n" +
                "where a.faPurchaseId=:faPurchaseId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("faPurchaseId", faPurchaseId)
                .setResultTransformer(Transformers.aliasToBean(AssetSetupDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<AssetSetupDTO> getAssetItemTxnDetail(BigInteger assetDetailId) {
        String query = "SELECT  e.faPurchaseId, e.purchaseMasterId,g.voucherNo, \n" +
                "CASE WHEN e.openingBalance IS NULL THEN \"Purchase\" \n" +
                "ELSE \"Opening\" END AS description,\n" +
                "e.purchaseDate,\n" +
                "e.rate,\n" +
                "count(e.faPurchaseId) as qty \n" +
                "FROM tbl_fa_item_setup  a \n" +
                "INNER JOIN tbl_fa_item_setup_detail b ON a.assetId=b.assetId\n" +
                "INNER JOIN tbl_fa_group c ON c.assetClassId=a.assetClassId\n" +
                "INNER JOIN tbl_acc_acctype d ON d.accTypeId=c.accTypeId\n" +
                "LEFT JOIN tbl_fa_purchase e ON e.assetDetailId=b.assetDetailId\n" +
                "INNER JOIN tbl_fa_purchase_mater g ON g.purchaseMasterId=e.purchaseMasterId\n" +
                "INNER JOIN tbl_fa_purchase_detail f ON f.faPurchaseId=e.faPurchaseId\n" +
                "where e.assetDetailId=:assetDetailId\n" +
                "group by e.faPurchaseId,a.assetId,b.particular\n" +
                "ORDER BY purchaseDate ASC;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("assetDetailId", assetDetailId)
                .setResultTransformer(Transformers.aliasToBean(AssetSetupDTO.class)).list();
    }
}
