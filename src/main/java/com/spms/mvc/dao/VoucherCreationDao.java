package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.dto.VoucherDetailDTO;
import com.spms.mvc.entity.*;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Query;
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
 * Created by jigmePc on 5/8/2019.
 */
@Repository("voucherCreationDao")
public class VoucherCreationDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getAccTypeList() {
        String query = "SELECT voucherTypeId AS valueInteger,voucherTypeName AS text FROM tbl_acc_vouchertype";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getLedgerList(Integer companyId) {
        String query = "SELECT ledgerId AS id,ledgerName AS text FROM tbl_acc_ledger where companyId=:companyId" +
                " ORDER BY ledgerName";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional
    public void save(Voucher voucher) {
        sessionFactory.getCurrentSession().saveOrUpdate(voucher);
    }

    @Transactional(readOnly = true)
    public Integer getCurrentVoucherId() {
        String query = "SELECT voucherId FROM tbl_acc_voucher_entries order by voucherId desc limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional
    public void saveDetail(VoucherDetail voucherDetail) {
        sessionFactory.getCurrentSession().save(voucherDetail);
    }

    @Transactional(readOnly = true)
    public Integer getCurrentVoucherNo(Integer voucherTypeId, Integer companyId, Integer financialYearId) {
        String query = "SELECT voucherSerial FROM tbl_acc_voucher_count where voucherTypeId=:voucherTypeId and companyId=:companyId AND financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        Query response = session.createSQLQuery(query)
                .setParameter("voucherTypeId", voucherTypeId)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId);
        return response.uniqueResult() == null ? 0 : (Integer) response.uniqueResult();
    }

    @Transactional
    public void updateVoucherNo(int voucherNo, Integer companyId, int voucherTypeId, Integer financialYearId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE tbl_acc_voucher_count SET voucherSerial=:voucherNo WHERE voucherTypeId=:voucherTypeId AND companyId=:companyId AND financialYearId=:financialYearId";
        session.createSQLQuery(sql)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("voucherTypeId", voucherTypeId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<VoucherDetailDTO> getVoucherDetailsByVoucherNo(Integer voucherNo, Integer voucherTypeId, Integer companyId) {
        String sqlQry = "SELECT \n" +
                "a.voucherDetailId AS voucherDetailId,\n" +
                "b.voucherTypeId AS voucherTypeId,\n" +
                "b.voucherEntryDate AS voucherEntryDate,\n" +
                "a.ledgerId AS ledgerId ,\n" +
                "CASE WHEN(a.drcrAmount > 0) THEN CAST(ABS(a.drcrAmount) AS  CHAR(250)) end AS creditAmount,\n" +
                "CASE WHEN(a.drcrAmount < 0) THEN  CAST(ABS(a.drcrAmount) AS  CHAR(250)) end debitAmount  \n" +
                "FROM tbl_acc_voucher_entries_detail a\n" +
                "INNER JOIN tbl_acc_voucher_entries b ON a.voucherId=b.voucherId\n" +
                "WHERE b.voucherNo=:voucherNo and b.voucherTypeId=:voucherTypeId AND b.companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("voucherNo", voucherNo)
                .setParameter("voucherTypeId", voucherTypeId)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(VoucherDetailDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public Boolean isVoucherIdExists(Integer voucherNo, Integer companyId, Integer financialYearId, Integer voucherTypeId) {
        String query = "SELECT (EXISTS(SELECT * FROM tbl_acc_voucher_entries where voucherNo=:voucherNo and companyId=:companyId AND voucherTypeId=:voucherTypeId AND financialYearId=:financialYearId))";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("voucherTypeId", voucherTypeId)
                .uniqueResult().equals(BigInteger.ONE);
    }

//    @Transactional(readOnly = true)
    public List<Integer> getVoucherIdByVoucherNo(Integer voucherNo, Integer companyId, Integer financialYearId, Integer voucherTypeId) {
        String query = "SELECT voucherId AS voucherId FROM tbl_acc_voucher_entries where voucherNo=:voucherNo and companyId=:companyId and financialYearId=:financialYearId and voucherTypeId=:voucherTypeId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("voucherTypeId", voucherTypeId)
                .list();
    }


    @Transactional
    public void deleteVoucherDetail(Integer voucherDetailId) {
        VoucherDetail voucherDetail;
        Session session = sessionFactory.getCurrentSession();
        voucherDetail = (VoucherDetail) session.load(VoucherDetail.class, voucherDetailId);
        session.delete(voucherDetail);
        session.flush();
    }

    @Transactional
    public void deleteVoucherDetailList(Integer voucherId) {
        String query = "DELETE FROM tbl_acc_voucher_entries_detail where voucherId=:voucherId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("voucherId", voucherId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Integer getVoucherIdFromVoucherTable(Integer voucherNo, Integer companyId, Integer financialYearId, Integer voucherTypeId) {
        String query = "SELECT voucherId FROM tbl_acc_voucher_entries where voucherNo=:voucherNo and companyId=:companyId AND voucherTypeId=:voucherTypeId and financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("voucherTypeId", voucherTypeId)
                .uniqueResult();
    }

    @Transactional
    public void deleteVoucherItemsFromVoucherTable(Integer voucherId) {
        Session session = sessionFactory.getCurrentSession();
        Voucher voucher = (Voucher) session.load(Voucher.class, voucherId);
        session.delete(voucher);
        session.flush();

    }

    @Transactional
    public void savePurchaseVoucherDetail(InvVoucherDetail purchaseVoucherDetail) {
        sessionFactory.getCurrentSession().save(purchaseVoucherDetail);
    }

    @Transactional(readOnly = true)
    public String getCashLedgerId(Integer companyId) {
        String query = "SELECT ledgerId FROM tbl_acc_ledger WHERE accTypeId=:accountTypeId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("accountTypeId", AccountTypeEnum.CASH.getValue())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getBankLedgerId(Integer companyId) {
        String query = "SELECT ledgerId FROM tbl_acc_ledger WHERE accTypeId=:accountTypeId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("accountTypeId", AccountTypeEnum.BANK.getValue())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Integer checkAccountHeadType(Integer ledgerId, Integer companyId) {
        String query = " SELECT accTypeId FROM tbl_acc_ledger WHERE ledgerId=:ledgerId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public void saveDepreciationDetails(DepreciationDetail depreciationDetail) {
        sessionFactory.getCurrentSession().save(depreciationDetail);
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getParticularList(Integer companyId) {
        String query = "SELECT depreciationId AS valueInteger,itemName AS text FROM tbl_acc_depreciation_item_setup " +
                "WHERE companyId=:companyId ORDER BY itemName;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Integer getRateOfDepreciation(Integer particularId, Integer companyId) {
        String query = "SELECT rateOfDepreciation FROM tbl_acc_depreciation_item_setup WHERE companyId=:companyId AND depreciationId=:particularId ;";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("particularId", particularId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Integer getSundryCreditorLedgerId(Integer companyId) {
        String query = "SELECT ledgerId FROM tbl_acc_ledger WHERE accTypeId=22 and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    public void updateDepreciationItemDetails(Depreciation depreciation) {
        sessionFactory.getCurrentSession().update(depreciation);
    }

    @Transactional
    public Integer saveDepreciationItemDetails(Depreciation depreciation) {
        sessionFactory.getCurrentSession().save(depreciation);
        return depreciation.getDepreciationId();
    }

    @Transactional(readOnly = true)
    public Boolean checkItemNameAlreadyExist(String itemName, Integer companyId) {
        String query = "SELECT (EXISTS(SELECT * FROM tbl_acc_depreciation WHERE itemName=:itemName AND companyId=:companyId))";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("itemName", itemName)
                .uniqueResult().equals(BigInteger.ONE);
    }

    @Transactional(readOnly = true)
    public Integer getDepreciationIdForUpdate(String itemName, Integer companyId) {
        String query = "SELECT depreciationId FROM tbl_acc_depreciation WHERE itemName=:itemName AND companyId=:companyId limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("itemName", itemName)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getSaleLedgerId(Integer companyId) {
        String query = "SELECT ledgerId FROM tbl_acc_ledger WHERE accTypeId=:accountTypeId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (String) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("accountTypeId", AccountTypeEnum.SALES.getValue())
                .uniqueResult();
    }

    @Transactional
    public void deleteFromSaleDetail(Integer saleRecordId) {
        String query = "DELETE FROM tbl_inv_sale_record_detail WHERE saleRecordId=:saleRecordId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("saleRecordId", saleRecordId)
                .executeUpdate();

    }

    @Transactional(readOnly = true)
    public SaleItemListDTO getSaleDetailByVoucherNo(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String sqlQry = "SELECT  a.saleRecordId, a.qty, a.itemCode FROM tbl_inv_sale_record_detail a\n" +
                "INNER JOIN tbl_inv_sale_record b ON a.saleRecordId=b.saleRecordId\n" +
                "WHERE b.voucherNo=:voucherNo AND b.companyId=:companyId AND b.financialYearId=:financialYearId ";
        return (SaleItemListDTO) sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class))
                .uniqueResult();
    }

    @Transactional
    public void updatePurchaseTable(BigDecimal qty, String itemCode, Integer companyId, Integer financialYearId) {
        String query = "update tbl_inv_purchase set qty=qty+ :qty where itemCode=:itemCode AND companyId=:companyId AND financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("qty", qty)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();

    }

    @Transactional(readOnly = true)
    public Integer getAccTypeId(String ledgerId, Integer companyId) {
        String query = "SELECT accTypeId FROM tbl_acc_ledger WHERE ledgerId=:ledgerId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("ledgerId", ledgerId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional
    public void deleteFromSale(Integer saleRecordId) {
        String query = "DELETE FROM tbl_inv_sale_record WHERE saleRecordId=:saleRecordId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("saleRecordId", saleRecordId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Integer getSaleRecordIdByVoucherNo(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT saleRecordId FROM tbl_inv_sale_record WHERE voucherNo=:voucherNo and companyId=:companyId and financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Integer getAssetSaleRecordIdByVoucherNo(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT saleRecordId FROM tbl_fa_sale_record WHERE voucherNo=:voucherNo and companyId=:companyId and financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult();
    }

    @Transactional
    public void deleteFromSaleAssetDetail(Integer saleAssetRecordId) {
        String query = "DELETE FROM tbl_fa_sale_record_detail WHERE saleRecordId=:saleRecordId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("saleRecordId", saleAssetRecordId)
                .executeUpdate();
    }

    @Transactional
    public void deleteFromAssetSale(Integer saleAssetRecordId) {
        String query = "DELETE FROM tbl_fa_sale_record WHERE saleRecordId=:saleRecordId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("saleRecordId", saleAssetRecordId)
                .executeUpdate();
    }
}
