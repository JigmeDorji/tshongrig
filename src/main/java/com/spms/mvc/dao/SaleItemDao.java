package com.spms.mvc.dao;


import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dto.ReceiptDT;
import com.spms.mvc.dto.SaleItemDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.entity.Discount;
import com.spms.mvc.entity.SaleRecord;
import com.spms.mvc.entity.SaleRecordDetail;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * Created by SonamPC on 16-Dec-16.
 */
@Repository("saleItemDao")
public class SaleItemDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<SaleItemDTO> getItemDetails(String itemCode, CurrentUser currentUser) {
        String query = "SELECT concat(a.itemName,' : ',type) AS itemName, a.sellingprice AS sellingPrice,unitName \n" +
                "FROM tbl_inv_purchase a\n" +
                "inner join tbl_inv_unit b on a.unitId=b.unitId\n" +
                "WHERE itemCode=:itemCode and companyId=:companyId";

        if (currentUser.getBusinessType() == 8) {
            query = "SELECT concat(a.itemName,' : ',itemCode) AS itemName, a.sellingprice AS sellingPrice,unitName \n" +
                    "FROM tbl_inv_purchase a\n" +
                    "inner join tbl_inv_unit b on a.unitId=b.unitId\n" +
                    "WHERE itemCode=:itemCode and companyId=:companyId";
        }

        Session session = sessionFactory.getCurrentSession();
        return (List<SaleItemDTO>) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).list();

    }

    @Transactional(readOnly = true)
    public Integer getReceiptSerial() {
        String query = "SELECT receiptSerialCounter FROM tbl_inv_receipt_counter limit 1";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query);
        return result.uniqueResult() == null ? 0 : (Integer) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<ReceiptDT> getDealerSoldItemList(String memoNumber, Date date) {
        String query = "SELECT tic.partDescription AS itemName,\n" +
                "tic.itempriceperqty AS pricePerQty,\n" +
                "Count(tic.partNumber) as qty,\n" +
                "(( Count(tic.partNumber))*(tic.itempriceperqty)) AS amount,\n" +
                "(( Count(tic.partNumber))*(tsr.discountRate)) AS discountAmt\n" +
                "FROM tbl_dealeritemsalerecord tsr\n" +
                "INNER JOIN tbl_itemcategory tic\n" +
                "ON tsr.itemid=tic.itemcategoryid \n" +
                "WHERE tsr.memono=:memoNumber  group  by tic.partNumber";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("memoNumber", memoNumber)
                .setResultTransformer(Transformers.aliasToBean(ReceiptDT.class)).list();
    }

    @Transactional
    public void saveItemDetails(SaleRecordDetail saleRecordDetail) {
        sessionFactory.getCurrentSession().save(saleRecordDetail);

    }

    @Transactional
    public void updateAvailableQty(String itemCode, BigDecimal qty, Integer companyId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE tbl_inv_purchase SET qty=(qty - :qty) WHERE itemCode=:itemCode AND companyId=:companyId";
        session.createSQLQuery(sql)
                .setParameter("itemCode", itemCode)
                .setParameter("qty", qty)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

    @Transactional
    public void updateReceiptSerial(Integer currentReceiptSerialNo) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE tbl_inv_receipt_counter SET receiptSerialCounter=:currentReceiptSerialNo  ";
        session.createSQLQuery(sql)
                .setParameter("currentReceiptSerialNo", currentReceiptSerialNo)
                .executeUpdate();
    }

    @Transactional
    public void insertToReceiptSerialCounter() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "INSERT INTO tbl_inv_receipt_counter VALUES(1,0)";
        session.createSQLQuery(sql).executeUpdate();
    }

    @Transactional(readOnly = true)
    public SaleItemDTO getMasterSaleDetail(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT\n" +
                "a.saleDate AS saleDate,\n" +
                "a.issueTo AS issueTo,\n" +
                "a.discount AS discountRate, \n" +
                "a.invoiceNo AS invoiceNo \n" +
                "FROM tbl_inv_sale_record a \n" +
                "WHERE a.voucherNo=:voucherNo AND a.companyId=:companyId AND a.financialYearId=:financialYearId group by receiptMemoNo";
        Session session = sessionFactory.getCurrentSession();
        return (SaleItemDTO) session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<SaleItemListDTO> getSaleDetail(Integer voucherNo, Integer companyId, Integer financialYearId) {
/*        String query = "SELECT\n" +
                "b.itemCode as itemCode,\n" +
                "c.itemName AS itemName,\n" +
                "b.sellingPrice AS sellingPrice,\n" +
                "a.saleDate AS saleDate,\n" +
                "d.unitName AS unitName,\n" +
                "SUM(b.qty) AS qty,\n" +
                "SUM(b.qty) * b.sellingPrice AS totalAmount,\n" +
                "a.voucherNo AS voucherNo\n" +
                "FROM tbl_inv_sale_record a \n" +
                "INNER JOIN tbl_inv_sale_record_detail b ON a.saleRecordId=b.saleRecordId\n" +
                "INNER JOIN tbl_inv_purchase c ON b.itemCode=c.itemCode\n" +
                "INNER JOIN tbl_inv_unit d ON d.unitId=c.unitId\n" +
                "WHERE a.voucherNo=:voucherNo GROUP BY itemCode";  */

        String query = "SELECT\n" +
                "b.itemCode as itemCode,\n" +
                "c.itemName AS itemName,\n" +
                "b.sellingPrice AS sellingPrice,\n" +
                "a.saleDate AS saleDate,\n" +
                "d.unitName AS unitName,\n" +
                "b.qty AS qty,\n" +
                "b.qty * b.sellingPrice AS totalAmount,\n" +
                "a.voucherNo AS voucherNo\n" +
                "FROM tbl_inv_sale_record a \n" +
                "INNER JOIN tbl_inv_sale_record_detail b ON a.saleRecordId=b.saleRecordId\n" +
                "INNER JOIN (SELECT itemCode, itemName, unitId FROM tbl_inv_purchase group  by itemCode) c ON b.itemCode=c.itemCode\n" +
                "INNER JOIN tbl_inv_unit d ON d.unitId=c.unitId\n" +
                "WHERE a.voucherNo=:voucherNo and a.companyId=:companyId and a.financialYearId=:financialYearId GROUP BY itemCode";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<SaleItemListDTO> getSaleItemDetailByReceiptNo(String receiptMemoNo) {
        String query = "\n" +
                "select b.itemName as itemName,\n" +
                "a.qty as qty,\n" +
                "a.itemCode as itemCode,\n" +
                "a.sellingPrice as sellingPrice,\n" +
                "(a.qty * a.sellingPrice) AS totalAmount\n" +
                "from tbl_inv_sale_record_detail a\n" +
                "inner join tbl_inv_purchase b on a.itemCode=b.itemCode\n" +
                "inner join tbl_inv_sale_record c on a.saleRecordId=c.saleRecordId\n" +
                "where c.receiptMemoNo=:receiptMemoNo group by c.receiptMemoNo,a.itemCode";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("receiptMemoNo", receiptMemoNo)
                .setResultTransformer(Transformers.aliasToBean(SaleItemListDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public BigDecimal getAvailableQty(String itemCode, Integer companyId) {
        String query = "SELECT ufn_get_available_qty(:itemCode,:companyId)";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);
        return result.uniqueResult() == null ? BigDecimal.ZERO : (BigDecimal) result.uniqueResult();
    }

    @Transactional
    public void saveDiscount(Discount discount) {
        sessionFactory.getCurrentSession().save(discount);
    }

    @Transactional
    public Integer saveSaleRecord(SaleRecord saleRecord) {
        sessionFactory.getCurrentSession().save(saleRecord);
        return saleRecord.getSaleRecordId();
    }

    @Transactional(readOnly = true)
    public String getLatestSalePurchaseInvoiceNo(String itemCode, Integer companyId) {
        String query = "SELECT purchaseInvoiceNo FROM tbl_inv_sale_record_detail a inner join tbl_inv_sale_record b\n" +
                "ON a.saleRecordId=b.saleRecordId WHERE a.itemCode=:itemCode AND b.companyId=:companyId order by a.id desc limit 1";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);
        return (String) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigDecimal getTopPurchaseInvoiceNoQty(String itemCode, Integer companyId) {
        String query = "SELECT qty FROM tbl_inv_purchase_a where itemCode=:itemCode  AND companyId=:companyId order by purchaseId asc limit 1;";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);
        return result.uniqueResult() == null ? BigDecimal.ZERO : (BigDecimal) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getTopPurchaseInvoiceNo(String itemCode, Integer companyId) {
        String query = "SELECT purchaseInvoiceNo FROM tbl_inv_purchase_a  WHERE itemCode=:itemCode AND companyId=:companyId order by purchaseId asc limit 1";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);
        return (String) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getNextPurchaseInvoiceNo(String purchaseInvoiceNo, String itemCode, Integer companyId) {

//        String query = "SELECT purchaseInvoiceNo FROM tbl_inv_purchase_a WHERE itemCode=:itemCode AND id > \n" +
//                "(SELECT id FROM tbl_inv_purchase_a where purchaseInvoiceNo=:purchaseInvoiceNo AND itemCode=:itemCode) \n" +
//                "ORDER BY id asc LIMIT 1;";

        String query = "SELECT ufn_get_next_purchase_invoice_no(:purchaseInvoiceNo,:itemCode, :companyId)";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("purchaseInvoiceNo", purchaseInvoiceNo)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);
        return (String) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigDecimal getRemainingQtyOfPurchaseInvoice(String latestSalePurchaseInvoiceNo, String itemCode, Integer companyId) {

        String query1 = "SELECT qty FROM tbl_inv_purchase_a where purchaseInvoiceNo=:purchaseInvoiceNo AND itemCode=:itemCode AND companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query1)
                .setParameter("purchaseInvoiceNo", latestSalePurchaseInvoiceNo)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);
        BigDecimal availableQty = result.uniqueResult() == null ? BigDecimal.ZERO : (BigDecimal) result.uniqueResult();

        String query2 = "select SUM(qty) from tbl_inv_sale_record_detail a inner join tbl_inv_sale_record b ON a.saleRecordId=b.saleRecordId  \n" +
                "where purchaseInvoiceNo=:purchaseInvoiceNo AND itemCode=:itemCode AND companyId=:companyId group by purchaseInvoiceNo";
        org.hibernate.Query result2 = sessionFactory.getCurrentSession().createSQLQuery(query2)
                .setParameter("purchaseInvoiceNo", latestSalePurchaseInvoiceNo)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId);

        BigDecimal totalSaleQty = result2.uniqueResult() == null ? BigDecimal.ZERO : (BigDecimal) result2.uniqueResult();

        return availableQty.subtract(totalSaleQty);
    }

//    @Transactional
//    public void updateSpillOverQty(String itemCode, Integer spillOverQty) {
//        Session session = sessionFactory.getCurrentSession();
//        String sql = "UPDATE tbl_inv_purchase SET spillOverQty=(spillOverQty - :spillOverQty) WHERE itemCode=:itemCode ";
//        session.createSQLQuery(sql)
//                .setParameter("itemCode", itemCode)
//                .setParameter("spillOverQty", spillOverQty)
//                .executeUpdate();
//    }

//    @Transactional(readOnly = true)
//    public Integer getSpillOverQty(String itemCode, Integer companyId, Integer financialYearId) {
//        String query = "SELECT spillOverQty FROM tbl_inv_purchase where itemCode=:itemCode AND companyId=:companyId AND financialYearId=:financialYearId";
//        Session session = sessionFactory.getCurrentSession();
//        org.hibernate.Query result = session.createSQLQuery(query)
//                .setParameter("itemCode", itemCode)
//                .setParameter("companyId", companyId)
//                .setParameter("financialYearId", financialYearId);
//        return result.uniqueResult() == null ? 0 : (Integer) result.uniqueResult();
//    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemCodeList(Integer companyId) {
        String query = "SELECT itemCode AS id, concat(itemName,' : ', itemCode) AS text FROM tbl_inv_purchase WHERE  companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<SaleItemDTO> isSaleInCash(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT c.accTypeId AS accTypeId, b.drcrAmount AS amount FROM tbl_acc_voucher_entries a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.voucherId=b.voucherId \n" +
                "INNER JOIN tbl_acc_ledger c ON c.ledgerId=b.ledgerId\n" +
                "where a.voucherNo=:voucherNo AND c.accTypeId !=:accountType AND a.voucherTypeId=:voucherTypeId \n" +
                "AND a.companyId=:companyId and financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("accountType", AccountTypeEnum.SALES.getValue())
                .setParameter("voucherTypeId", VoucherTypeEnum.SALES.getValue())
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class));

        return result.list();
    }

    @Transactional(readOnly = true)
    public List<SaleItemDTO> isSaleInBank(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT b.drcrAmount AS amount,c.ledgerId AS ledgerId,c.accTypeId FROM tbl_acc_voucher_entries a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.voucherId=b.voucherId \n" +
                "INNER JOIN tbl_acc_ledger c ON c.ledgerId=b.ledgerId \n" +
                "where a.voucherNo=:voucherNo AND c.accTypeId !=:accountType AND c.accTypeId=:accountTypeBankID AND a.voucherTypeId=:voucherTypeId \n" +
                "AND a.companyId=:companyId and financialYearId=:financialYearId";

        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("accountType", AccountTypeEnum.SALES.getValue())
                .setParameter("voucherTypeId", VoucherTypeEnum.SALES.getValue())
                .setParameter("accountTypeBankID", AccountTypeEnum.BANK.getValue())
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public SaleItemDTO isSaleInCredit(Integer voucherNo, Integer companyId, Integer financialYearId) {

        String query = "SELECT c.ledgerId AS ledgerId FROM tbl_acc_voucher_entries a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.voucherId=b.voucherId \n" +
                "INNER JOIN tbl_acc_ledger c ON c.ledgerId=b.ledgerId\n" +
                "where a.voucherNo=:voucherNo AND c.accTypeId=:accountType AND a.voucherTypeId=:voucherTypeId \n" +
                "and a.companyId=:companyId and financialYearId=:financialYearId";

        return (SaleItemDTO) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("accountType", AccountTypeEnum.RECEIVABLE.getValue())
                .setParameter("voucherTypeId", VoucherTypeEnum.SALES.getValue())
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<Integer> isSaleCompoundVoucher(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT c.accTypeId FROM tbl_acc_voucher_entries a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.voucherId=b.voucherId \n" +
                "INNER JOIN tbl_acc_ledger c ON c.ledgerId=b.ledgerId \n" +
                "where a.voucherNo=:voucherNo AND a.voucherTypeId=:voucherTypeId \n" +
                "AND a.companyId=:companyId and financialYearId=:financialYearId";

        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("voucherTypeId", VoucherTypeEnum.SALES.getValue())
                .list();
    }

    @Transactional(readOnly = true)
    public List<SaleItemDTO> getCompoundVoucherList(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT  c.ledgerId AS ledgerId, c.accTypeId, b.drcrAmount AS amount FROM tbl_acc_voucher_entries a\n" +
                "INNER JOIN tbl_acc_voucher_entries_detail b ON a.voucherId=b.voucherId \n" +
                "INNER JOIN tbl_acc_ledger c ON c.ledgerId=b.ledgerId \n" +
                "where a.voucherNo=:voucherNo AND a.voucherTypeId=:voucherTypeId \n" +
                "AND c.accTypeId !=:accountType AND a.companyId=:companyId and financialYearId=:financialYearId";

        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setParameter("voucherTypeId", VoucherTypeEnum.SALES.getValue())
                .setParameter("accountType", AccountTypeEnum.SALES.getValue())
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public BigInteger getSalePaymentDetail(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT salePaymentModeId FROM tbl_inv_sale_payment_mode WHERE voucherNo=:voucherNo AND companyId=:companyId AND financialYearId=:financialYearId";
        return (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public SaleItemDTO getSaleItemPaymentModeDetail(Integer voucherNo, Integer companyId, Integer financialYearId) {
        String query = "SELECT partyId, saleInType FROM tbl_inv_sale_record WHERE voucherNo=:voucherNo AND companyId=:companyId AND financialYearId=:financialYearId group by receiptMemoNo";

        return (SaleItemDTO) sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("voucherNo", voucherNo)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class))
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<SaleItemDTO> getIssueItemDetails(String itemCode, Integer companyId) {
        String query = "SELECT a.itemName AS itemName, a.costPrice AS costPrice,unitName \n" +
                "FROM tbl_inv_purchase a\n" +
                "inner join tbl_inv_unit b on a.unitId=b.unitId\n" +
                "WHERE itemCode=:itemCode and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (List<SaleItemDTO>) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).list();
    }

    @Transactional
    public Double getTotalAmountByItemCode(String itemCode, Integer companyId, BigDecimal qty) {
        String query = "CALL sp_acc_get_inv_item_total_amount(:companyId,:itemCode,:qty)";
        Session session = sessionFactory.getCurrentSession();
        return (double) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setParameter("qty", qty)
                .uniqueResult();
    }
}
