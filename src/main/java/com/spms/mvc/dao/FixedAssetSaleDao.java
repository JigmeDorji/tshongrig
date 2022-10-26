package com.spms.mvc.dao;

import com.spms.mvc.dto.FixedAssetScheduleDTO;
import com.spms.mvc.dto.OpeningAndBuyingDTO;
import com.spms.mvc.dto.OpeningAndBuyingListDTO;
import com.spms.mvc.entity.FaSaleRecord;
import com.spms.mvc.entity.FaSaleRecordDetail;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public class FixedAssetSaleDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingDTO> getFaItemDetails(BigInteger assetDetailId, Integer companyId) {
        String query = "SELECT b.particular, b.assetDetailId  \n" +
                "FROM tbl_fa_item_setup  a INNER JOIN  tbl_fa_item_setup_detail  b\n" +
                "ON a.assetId=b.assetId WHERE a.companyId=:companyId AND b.assetDetailId=:assetDetailId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("assetDetailId", assetDetailId)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemList(Integer companyId) {
        String query = "SELECT b.particular AS text, b.assetDetailId AS valueBigInteger, d.accTypeName \n" +
                "FROM tbl_fa_item_setup  a INNER JOIN  tbl_fa_item_setup_detail  b\n" +
                "ON a.assetId=b.assetId INNER JOIN tbl_fa_group c on c.assetClassId=a.assetClassId\n" +
                "INNER JOIN tbl_acc_acctype d on c.accTypeId=d.accTypeId\n" +
                "WHERE a.companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemCodeList(BigInteger assetDetailId) {
        String query = "SELECT faPurchaseDetailId as valueBigInteger, assetCode as text  FROM tbl_fa_purchase a\n" +
                "INNER JOIN tbl_fa_purchase_detail b ON a.faPurchaseId=b.faPurchaseId\n" +
                "INNER JOIN tbl_fa_item_setup_detail c ON c.assetDetailId=a.assetDetailId\n" +
                "where a.assetDetailId=:assetDetailId AND b.status='N'";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("assetDetailId", assetDetailId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional
    public BigInteger saveFaSaleRecord(FaSaleRecord faSaleRecord) {
        sessionFactory.getCurrentSession().saveOrUpdate(faSaleRecord);
        return faSaleRecord.getSaleRecordId();
    }

    @Transactional
    public void saveFaItemDetails(FaSaleRecordDetail faSaleRecordDetail) {
        sessionFactory.getCurrentSession().saveOrUpdate(faSaleRecordDetail);
    }

    @Transactional(readOnly = true)
    public Integer getReceiptSerial(Integer companyId) {
        String query = "SELECT receiptSerialCounter FROM tbl_fa_receipt_counter where companyId=:companyId limit 1";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query).setParameter("companyId", companyId);
        return (Integer) result.uniqueResult();
    }

    @Transactional
    public void insertToReceiptSerialCounter(Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "INSERT INTO tbl_fa_receipt_counter VALUES(1,0,:companyId)";
        session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

    @Transactional
    public void updateReceiptSerial(Integer currentReceiptSerialNo, Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE tbl_fa_receipt_counter SET receiptSerialCounter=:currentReceiptSerialNo where companyId=:companyId";
        session.createSQLQuery(sql)
                .setParameter("currentReceiptSerialNo", currentReceiptSerialNo)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Integer getVoucherNoByReceiptMemoNo(String receiptMemoNo, CurrentUser currentUser) {
        String query = "SELECT voucherNo FROM tbl_fa_sale_record a WHERE a.receiptMemoNo=:receiptMemoNo AND a.companyId=:companyId AND a.financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .setParameter("receiptMemoNo", receiptMemoNo)
                .uniqueResult();
    }

    @Transactional
    public void deleteSaleDetail(Integer id) {
        String query = "DELETE FROM tbl_fa_sale_record_detail where id=:id";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public void deleteSaleRecord(String receiptMemoNo, Integer companyId, Integer financialYearId) {
        String query = "DELETE FROM  tbl_fa_sale_record WHERE receiptMemoNo=:receiptMemoNo AND companyId=:companyId AND financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("receiptMemoNo", receiptMemoNo)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<OpeningAndBuyingListDTO> getAssetSaleRecordByReceiptMemoNo(String receiptMemoNo, Integer companyId) {
        String query = "SELECT SUM(b.netAmount) as totalAmount, g.accTypeId FROM tbl_fa_sale_record a\n" +
                "INNER JOIN tbl_fa_sale_record_detail b ON a.saleRecordId=b.saleRecordId\n" +
                "INNER JOIN tbl_fa_purchase_detail c ON c.assetCode=b.assetCode\n" +
                "INNER JOIN tbl_fa_purchase d ON d.faPurchaseId=c.faPurchaseId\n" +
                "INNER JOIN tbl_fa_item_setup_detail e ON e.assetDetailId=d.assetDetailId\n" +
                "INNER JOIN tbl_fa_item_setup f ON f.assetId=e.assetId\n" +
                "INNER JOIN tbl_fa_group g ON g.assetClassId=f.assetClassId\n" +
                "WHERE a.receiptMemoNo=:receiptMemoNo AND a.companyId=:companyId GROUP BY g.assetClassId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("receiptMemoNo", receiptMemoNo)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public OpeningAndBuyingListDTO getAssetDetailByAssetCode(String assetCode, Integer companyId) {
        String query = "SELECT b.purchaseDate, b.openingBalance, b.depreciatedValue,b.rate, b.qty, e.accTypeId\n" +
                " FROM tbl_fa_purchase_detail a\n" +
                "INNER JOIN tbl_fa_purchase b ON a.faPurchaseId=b.faPurchaseId\n" +
                "INNER JOIN tbl_fa_item_setup_detail c ON c.assetDetailId=b.assetDetailId\n" +
                "INNER JOIN tbl_fa_item_setup d ON d.assetId=c.assetId\n" +
                "INNER JOIN tbl_fa_group e ON e.assetClassId=d.assetClassId\n" +
                "WHERE a.assetCode=:assetCode and d.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (OpeningAndBuyingListDTO) session.createSQLQuery(query)
                .setParameter("assetCode", assetCode)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(OpeningAndBuyingListDTO.class)).uniqueResult();
    }

    @Transactional
    public void updateStatusAssetPurchase(String assetCode) {
        String query = "UPDATE tbl_fa_purchase_detail set status='S' where assetCode=:assetCode";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("assetCode", assetCode)
                .executeUpdate();
    }

    @Transactional
    public List<FixedAssetScheduleDTO> getFixedAssetSchedule(Date asOnDate, Integer companyId) {
        String query = "CALL sp_fa_fixed_asset_schedule(:companyId,:asOnDate)";

        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("asOnDate", asOnDate)
                .executeUpdate();

        String query2 = "SELECT * FROM temp_final_fixed_asset_schedule";
        return sessionFactory.getCurrentSession().createSQLQuery(query2)
                .setResultTransformer(Transformers.aliasToBean(FixedAssetScheduleDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public BigInteger getAssetDetailId(String particular,Integer companyId) {
        String query = "SELECT a.assetDetailId FROM tbl_fa_item_setup_detail a \n" +
                "inner Join tbl_fa_item_setup b on a.assetId=b.assetId\n" +
                "where b.companyId=:companyId and particular=:particular";
        return (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("particular", particular)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public void monthlyDepreciationUpdate() {
        sessionFactory.getCurrentSession().
                createSQLQuery("CALL sp_fa_depreciation_event_schedule")
                .executeUpdate();
    }
}
