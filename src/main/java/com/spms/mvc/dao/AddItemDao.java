package com.spms.mvc.dao;

import com.spms.mvc.Enumeration.VoucherTypeEnum;
import com.spms.mvc.dto.*;
import com.spms.mvc.entity.BrandWiseItemCode;
import com.spms.mvc.entity.Purchase;
import com.spms.mvc.entity.PurchaseCreditSupplierDetail;
import com.spms.mvc.entity.Purchase_A;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.SQLQuery;
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
 * Created by SonamPC on 13-Dec-16.
 */
@Repository("addItemDao")
public class AddItemDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemCategory() {
        String query = "SELECT itemcategoryid AS value,\n" +
                "partNumber AS text FROM tbl_itemcategory;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }


    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemNameList() {
        String query = "SELECT itemcategoryid AS value,\n" +
                "concat(partDescription,' : ',IFNULL(partName2,'Part name 2 not given'),'(',IFNULL(fits,'Fits not given'),')') AS text FROM tbl_itemcategory;";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemList(CurrentUser currentUser) {
        String query = "SELECT itemCode AS id,concat(itemCode,'-',itemName) AS text FROM tbl_inv_purchase where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getPurchaseDetail(Integer purchaseId, Date purchaseDate, Integer voucherNo) {
        String query = "SELECT B.purchaseId AS purchaseId,\n" +
                "B.purchaseDate AS purchaseDate,\n" +
                "B.purchaseInvoiceNo AS purchaseInvoiceNo,\n" +
                "B.itemName AS itemName,\n" +
                "B.itemcode AS itemCode,\n" +
                "SUM(B.qty) AS sumQty,\n" +
                "B.costPrice AS costPrice,\n" +
                "B.sellingprice AS sellingPrice,\n" +
                "B.locationId AS locationId,\n" +
                "B.brandId, \n" +
                "c.brandName, \n" +
                "B.type, \n" +
                "B.partNo, \n" +
                "B.isCash, \n" +
                "d.unitId \n" +
                "FROM tbl_inv_purchase_a B \n" +
                "inner JOIN tbl_item_code c on B.brandId=c.brandId \n" +
                "inner JOIN tbl_inv_unit d on d.unitId=B.unitId\n" +
                "where B.purchaseId=:purchaseId and B.purchaseDate=:purchaseDate and B.purchaseVoucherNo=:voucherNo group by B.purchaseId";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query)
                .setParameter("purchaseId", purchaseId)
                .setParameter("purchaseDate", purchaseDate)
                .setParameter("voucherNo", voucherNo)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }


    @Transactional(readOnly = true)
    public PurchaseDTO getItemDetailsByItemCode(String itemCode, Integer companyId) {
        String query = "SELECT purchaseId AS purchaseId,\n" +
                "purchaseDate AS purchaseDate,\n" +
                "purchaseInvoiceNo AS purchaseInvoiceNo,\n" +
                "itemName AS itemName,\n" +
                "itemcode AS itemCode,\n" +
                "qty AS qty,\n" +
                "costPrice AS costPrice,\n" +
                "sellingprice AS sellingPrice,\n" +
                "locationId AS locationId\n" +
                "FROM tbl_inv_purchase where itemcode=:itemCode and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }

 /*   @Transactional
    public void generateItemCode(List<ItemMaster> itemMasters) {
        for (ItemMaster itemMaster : itemMasters) {
            sessionFactory.getCurrentSession().saveOrUpdate(itemMaster);
        }
    }
*/

    @Transactional(readOnly = true)
    public AddItemCategoryDTO getItemPrefix(Integer itemCategoryId) {
        String query = "SELECT itemcategoryprefix AS itemCategoryPrefix FROM tbl_itemcategory WHERE itemcategoryid=:itemCategoryId";
        Session session = sessionFactory.getCurrentSession();
        return (AddItemCategoryDTO) session.createSQLQuery(query)
                .setParameter("itemCategoryId", itemCategoryId)
                .setResultTransformer(Transformers.aliasToBean(AddItemCategoryDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public AddItemCategoryDTO getItemCode(Integer itemCategoryId) {
        String query = "SELECT itemlevelcode AS itemCode  FROM tbl_itemcategory where itemcategoryid=:itemCategoryId";
        Session session = sessionFactory.getCurrentSession();
        return (AddItemCategoryDTO) session.createSQLQuery(query)
                .setParameter("itemCategoryId", itemCategoryId)
                .setResultTransformer(Transformers.aliasToBean(AddItemCategoryDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public ItemCounterDTO getCurrentItemSerialNo(Integer itemCategoryId) {
        String query = "SELECT itemcategoryid AS itemCategoryId , itemserialcounter As itemSerialCounter FROM tbl_item_counter WHERE itemcategoryid=:itemCategoryId";
        Session session = sessionFactory.getCurrentSession();
        return (ItemCounterDTO) session.createSQLQuery(query)
                .setParameter("itemCategoryId", itemCategoryId)
                .setResultTransformer(Transformers.aliasToBean(ItemCounterDTO.class)).uniqueResult();
    }

//
//    @Transactional
//    public void updateItemCounter(ItemCounter counter) {
//        sessionFactory.getCurrentSession().saveOrUpdate(counter);
//    }

    @Transactional
    public AddItemDTO getItemPricePerQty(String item_ref_id) {
        String query = "SELECT  ic.partNumber AS partNumber,\n" +
                "ic.itempriceperqty AS pricePerQty,\n" +
                "ic.partDescription AS partDescription, \n" +
                "ls.locationId AS locationId,\n" +
                "ic.itemlevelcode AS itemCode \n" +
                "from tbl_itemcategory ic\n" +
                "INNER JOIN tbl_locationsetup ls \n" +
                "ON ls.locationSetUpId=ic.locationId \n" +
                "WHERE ic.itemCategoryId=:item_ref_id\n" +
                "GROUP BY ic.itemCategoryId";
        Session session = sessionFactory.getCurrentSession();
        return (AddItemDTO) session.createSQLQuery(query)
                .setParameter("item_ref_id", item_ref_id)
                .setResultTransformer(Transformers.aliasToBean(AddItemDTO.class)).uniqueResult();
    }


    @Transactional
    public SaleItemDTO getItemInfo(Integer itemId) {
        String query = "SELECT  ic.partNumber AS partNumber,\n" +
                "ic.itempriceperqty AS pricePerQty,\n" +
                "concat(ic.partDescription,' : ',IFNULL(ic.partName2,'Part name 2 not given'),'(',IFNULL(ic.fits,'Fits not given'),')') AS partDescription, \n" +
                "ls.locationId AS locationId, \n" +
                "ic.itemlevelcode AS itemCode, \n" +
                "count(*) AS qtyLeft \n" +
                "from tbl_itemmaster im \n" +
                "INNER JOIN tbl_itemcategory ic ON im.itemCategoryId=ic.itemcategoryid \n" +
                "INNER JOIN tbl_locationsetup ls ON ls.locationSetUpId=ic.locationId \n" +
                "WHERE ic.itemCategoryId=:itemId AND im.itemStatus = 'A' GROUP BY ic.itemCategoryId;";
        Session session = sessionFactory.getCurrentSession();
        return (SaleItemDTO) session.createSQLQuery(query)
                .setParameter("itemId", itemId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).uniqueResult();
    }

    @Transactional
    public SaleItemDTO getItemDetailsOnSearchByName(Integer itemId) {
        String query = "SELECT  ic.partNumber AS partNumber,\n" +
                "ic.itempriceperqty AS pricePerQty,\n" +
                "concat(ic.partDescription,' : ',IFNULL(ic.partName2,'Part name 2 not given'),'(',IFNULL(ic.fits,'Fits not given'),')') AS partDescription, \n" +
                "ls.locationId AS locationId, \n" +
                "ic.itemlevelcode AS itemCode \n" +
                "FROM tbl_itemcategory ic " +
                "INNER JOIN tbl_locationsetup ls ON ls.locationSetUpId=ic.locationId \n" +
                "WHERE ic.itemCategoryId=:itemId GROUP BY ic.itemCategoryId;";
        Session session = sessionFactory.getCurrentSession();
        return (SaleItemDTO) session.createSQLQuery(query)
                .setParameter("itemId", itemId)
                .setResultTransformer(Transformers.aliasToBean(SaleItemDTO.class)).uniqueResult();
    }

    @Transactional
    public List<AddItemCategoryDTO> getItemDeSuggestionList(String itemName) {
        String query = "SELECT itemcategoryid AS itemCategoryId,\n" +
                "concat(partDescription,' : ',IFNULL(partName2,'Part name 2 not given'),'(',IFNULL(fits,'Fits not given'),')') AS partDescription \n" +
                "FROM tbl_itemcategory \n" +
                "WHERE partDescription like :itemName";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("itemName", itemName + '%')
                .setResultTransformer(Transformers.aliasToBean(AddItemCategoryDTO.class)).list();
    }

    @Transactional
    public Integer savePurchaseItem(Purchase purchase) {
        sessionFactory.getCurrentSession().saveOrUpdate(purchase);
        return purchase.getPurchaseId();
    }

    @Transactional(readOnly = true)
    public Integer generateItemCode() {
        String query = "SELECT itemCodeCounter FROM tbl_inv_item_code_counter";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Integer getId() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT Id FROM tbl_inv_item_code_counter";
        return (Integer) session.createSQLQuery(sql).uniqueResult();
    }

    @Transactional
    public void updateItemCodeCounter(String itemCode, Integer id) {
        String query = "UPDATE tbl_inv_item_code_counter SET itemCodeCounter=:itemCode WHERE id=:id ";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public void saveItemCodeCounter() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "INSERT INTO  tbl_inv_item_code_counter values (1,1)";
        session.createSQLQuery(sql)
                .executeUpdate();

    }

    @Transactional
    public void saveToAuditTable(Purchase_A purchase_a) {
        sessionFactory.getCurrentSession().saveOrUpdate(purchase_a);
    }

    @Transactional(readOnly = true)
    public Integer getAuditId(Integer purchaseId) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT id FROM tbl_inv_purchase_a where purchaseId=:purchaseId";
        return (Integer) session.createSQLQuery(sql).setParameter("purchaseId", purchaseId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Integer getAuditIdByVoucherNo(Integer purchaseId, Integer purchaseVoucherNo) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT id FROM tbl_inv_purchase_a where purchaseId=:purchaseId and purchaseVoucherNo=:purchaseVoucherNo";
        return (Integer) session.createSQLQuery(sql)
                .setParameter("purchaseId", purchaseId)
                .setParameter("purchaseVoucherNo", purchaseVoucherNo)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Boolean checkItemName(String itemName) {
        String sqlQry = "SELECT(EXISTS(SELECT * from tbl_inv_purchase b where b.itemName=:itemName))";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("itemName", itemName).uniqueResult().equals(BigInteger.ONE);
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getRecentPurchaseData() {
        String query = "SELECT A.purchaseId AS purchaseId,\n" +
                "A.purchaseDate AS purchaseDate,\n" +
                "A.purchaseInvoiceNo AS purchaseInvoiceNo,\n" +
                "A.itemName AS itemName,\n" +
                "A.itemcode AS itemCode,\n" +
                "B.qty AS qty,\n" +
                "A.costPrice AS costPrice,\n" +
                "A.sellingprice AS sellingPrice,\n" +
                "A.locationId AS locationId\n" +
                "FROM tbl_inv_purchase A\n" +
                "INNER JOIN tbl_inv_purchase_a B ON A.purchaseId=B.purchaseId order by purchaseId desc limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigDecimal getSoldQty(String itemCode) {
        String sqlQry = "select SUM(qty) from tbl_inv_sale_record where itemCode=:itemCode group by itemCode";
        return (BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("itemCode", itemCode).uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigDecimal getReturnQty(String itemCode) {
        String sqlQry = "select SUM(returnQty) from tbl_inv_return_item where itemCode=:itemCode group by itemCode";
        return (BigDecimal) sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("itemCode", itemCode).uniqueResult();
    }

    @Transactional
    public void savePurchaseCreditSupplierDetail(PurchaseCreditSupplierDetail purchaseCreditSupplierDetail) {
        sessionFactory.getCurrentSession().save(purchaseCreditSupplierDetail);
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getBrandList(Integer companyId) {
        String query = "SELECT brandId AS value, brandName AS text FROM tbl_item_code WHERE  companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getSlNo(Integer brandId) {
        String query = "SELECT serialNo AS serialNo, brandPrefix AS prefixCode FROM tbl_item_code WHERE brandId=:brandId";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query)
                .setParameter("brandId", brandId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class))
                .uniqueResult();
    }

    @Transactional
    public void updateBrandWiseSerialNo(Integer brandId, Integer maxSerialNo, Integer companyId) {
        String query = "UPDATE  tbl_item_code SET serialNo=:maxSerialNo WHERE brandId=:brandId and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("brandId", brandId)
                .setParameter("maxSerialNo", maxSerialNo)
                .setParameter("companyId", companyId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getItemDetails(String itemCode, Integer companyId) {
        String query = "SELECT A.itemCode AS itemCode, \n" +
                "B.brandId AS brandId,\n" +
                "B.brandName AS brandName,\n" +
                "A.purchaseId AS purchaseId,\n" +
                "A.qty AS qty,\n" +
                "A.itemName AS itemName,\n" +
                "A.costPrice AS costPrice,\n" +
                "A.type AS type,\n" +
                "A.partNo AS partNo,\n" +
                "A.sellingprice AS sellingPrice,\n" +
                "A.locationId AS locationId,\n" +
                "B.brandPrefix AS prefixCode,\n" +
                "c.unitId AS unitId\n" +
                "FROM tbl_inv_purchase A\n" +
                "INNER JOIN tbl_item_code B ON A.brandId=B.brandId\n" +
                "INNER JOIN tbl_inv_unit c ON c.unitId=A.unitId\n" +
                "WHERE A.itemCode=:itemCode and A.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getOpenItemDetails(String itemCode, Integer companyId) {
        String query = "SELECT A.itemCode AS itemCode, \n" +
                "B.brandId AS brandId,\n" +
                "B.brandName AS brandName,\n" +
                "A.purchaseId AS purchaseId,\n" +
                "A.itemName AS itemName,\n" +
                "A.costPrice AS costPrice,\n" +
                "A.type AS type,\n" +
                "A.partNo AS partNo,\n" +
                "A.sellingprice AS sellingPrice,\n" +
                "A.locationId AS locationId,\n" +
                "B.brandPrefix AS prefixCode\n" +
                "FROM tbl_inv_purchase A\n" +
                "INNER JOIN tbl_item_code B ON A.brandId=B.brandId\n" +
                "WHERE A.itemCode=:itemCode and A.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigDecimal getCurrentQty(String itemCode, Integer companyId) {
        String sqlQry = "select qty from tbl_inv_purchase where itemCode=:itemCode and companyId=:companyId";
        return (BigDecimal) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }


//    @Transactional(readOnly = true)
//    public String getLedgerIdByLedgerName(Integer companyId, String ledgerName) {
//        String sqlQry = "select ledgerId from tbl_acc_ledger where companyId=:companyId and ledgerName=:ledgerName";
//        return (String) sessionFactory.getCurrentSession()
//                .createSQLQuery(sqlQry)
//                .setParameter("ledgerName", ledgerName)
//                .setParameter("companyId", companyId).uniqueResult();
//    }

    @Transactional(readOnly = true)
    public Integer getMaxBrandId() {
        String sqlQry = "SELECT max(brandId) FROM tbl_item_code";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.uniqueResult() == null ? 0 : (Integer) hibernate.uniqueResult();
    }

    @Transactional
    public Integer saveBrandDetail(BrandWiseItemCode brandWiseItemCode) {
        sessionFactory.getCurrentSession()
                .saveOrUpdate(brandWiseItemCode);
        return brandWiseItemCode.getBrandId();
    }

    @Transactional(readOnly = true)
    public Integer getCompanyWiseSerialNo(Integer brandId) {
        String sqlQry = "SELECT serialNo FROM tbl_item_code WHERE brandId=:brandId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.setParameter("brandId", brandId).uniqueResult() == null ? 0
                : (Integer) hibernate.setParameter("brandId", brandId).uniqueResult();
    }

    @Transactional(readOnly = true)
    public String getMaxSerialIDByBrand(Integer brandId, Integer companyId) {
        String sqlQry = "SELECT MAX(serialNo) FROM tbl_item_code WHERE brandId=:brandId and companyId=:companyId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate
                .setParameter("brandId", brandId)
                .setParameter("companyId", companyId)
                .uniqueResult() == null ? "0"
                : (String) hibernate
                .setParameter("brandId", brandId)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getTotalCash(Integer accountType, Integer companyId, Integer financialYearId) {
        String sqlQry = "SELECT\n" +
                "SUM(c.drcrAmount) as amount\n" +
                "FROM tbl_acc_acctype a\n" +
                "LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId\n" +
                "LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId\n" +
                "LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "WHERE d.companyId=:companyId AND d.financialYearId=:financialYearId AND a.accTypeId=:accountType AND c.drcrAmount < 0\n" +
                "GROUP BY a.accTypeId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (Double) hibernate.setParameter("accountType", accountType)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getItemDetailsByPartNo(String partNo) {
        String query = "SELECT A.itemCode AS itemCode, \n" +
                "B.brandName AS brandName,\n" +
                "A.purchaseId AS purchaseId,\n" +
                "A.itemName AS itemName,\n" +
                "A.costPrice AS costPrice,\n" +
                "A.type AS type,\n" +
                "A.partNo AS partNo,\n" +
                "A.sellingprice AS sellingPrice,\n" +
                "A.locationId AS locationId\n" +
                "FROM tbl_inv_purchase A\n" +
                "INNER JOIN tbl_item_code B ON A.brandId=B.brandId\n" +
                "WHERE A.partNo=:partNo";
        Session session = sessionFactory.getCurrentSession();
        return (PurchaseDTO) session.createSQLQuery(query).setParameter("partNo", partNo)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getTypeDetail(Integer companyId) {
        String query = "SELECT DISTINCT type AS id, type AS text FROM tbl_inv_purchase WHERE  companyId=:companyId";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Boolean checkInBrandExists(String brandName, Integer companyId) {
        String sqlQry = "SELECT count(*) FROM tbl_item_code where brandName=:brandName AND companyId=:companyId";
        return sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("brandName", brandName)
                .setParameter("companyId", companyId).uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public String getPurchaseInLedgerId(String purchaseLedgerId, Integer voucherNo, CurrentUser currentUser) {
        String sqlQry = "select b.ledgerId from tbl_acc_voucher_entries a\n" +
                "inner join tbl_acc_voucher_entries_detail b on a.voucherId=b.voucherId\n" +
                "where a.voucherNo=:voucherNo and b.ledgerId!=:purchaseLedgerId and a.voucherTypeId=:voucherTypeId and a.companyId=:companyId and a.financialYearId=:financialYearId";
        return (String) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("purchaseLedgerId", purchaseLedgerId)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .setParameter("voucherTypeId", VoucherTypeEnum.PURCHASE.getValue())
                .setParameter("voucherNo", voucherNo).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getTotalCashOutFlow(Integer accountType, Integer companyId, Integer financialYearId) {
        String sqlQry = "SELECT\n" +
                "SUM(c.drcrAmount) as amount\n" +
                "FROM tbl_acc_acctype a\n" +
                "LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId\n" +
                "LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId\n" +
                "LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "WHERE d.companyId=:companyId AND d.financialYearId=:financialYearId AND a.accTypeId=:accountType AND c.drcrAmount > 0\n" +
                "GROUP BY a.accTypeId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (Double) hibernate.setParameter("accountType", accountType)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public BigDecimal getPreviousReceivedQty(CurrentUser currentUser, Integer voucherNo, String itemCode) {
        String sqlQry = "SELECT qty FROM tbl_inv_purchase_a a where a.companyId=:companyId and a.financialYearId=:financialYearId and a.itemCode=:itemCode and a.purchaseVoucherNo=:voucherNo";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (BigDecimal) hibernate
                .setParameter("voucherNo", voucherNo)
                .setParameter("itemCode", itemCode)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getItemCodeListByBrandId(CurrentUser currentUser, Integer brandId) {
        String query = "SELECT itemCode AS id, concat(itemName,' : ',type) AS text FROM tbl_inv_purchase WHERE  companyId=:companyId AND financialYearId=:financialYearId AND brandId=:brandId ";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("financialYearId", currentUser.getFinancialYearId())
                .setParameter("brandId", brandId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public boolean checkPurchaseInvoiceNoAlreadyEntered(String itemCode, String purchaseInvoiceNo) {
        String query = "SELECT * FROM tbl_inv_purchase_a where itemCode=:itemCode and purchaseInvoiceNo=:purchaseInvoiceNo";
        return sessionFactory.getCurrentSession().createSQLQuery(query)
                .setParameter("itemCode", itemCode)
                .setParameter("purchaseInvoiceNo", purchaseInvoiceNo)
                .list().size() > 0;
    }

    @Transactional(readOnly = true)
    public Integer getBrandIdByName(String brandName, Integer companyId) {
        String sqlQry = "SELECT brandId FROM tbl_item_code where brandName=:brandName AND companyId=:companyId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (Integer) hibernate
                .setParameter("brandName", brandName)
                .setParameter("companyId", companyId)
                .uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<PurchaseDTO> getItemListByInvoiceNo(String purchaseInvoiceNo, Integer companyId) {
        String sqlQry = "select a.purchaseDate,\n" +
                "a.id as purchaseAuditId,\n" +
                "b.brandName,\n" +
                "a.purchaseId,\n" +
                "a.purchaseVoucherNo,\n" +
                "a.purchaseInvoiceNo,\n" +
                "a.isCash,\n" +
                "a.itemCode,\n" +
                "a.type,\n" +
                "a.partNo,\n" +
                "a.itemName,\n" +
                "a.qty,\n" +
                "a.brandId,\n" +
                "a.costPrice,\n" +
                "a.sellingPrice,\n" +
                "a.locationId,\n" +
                "c.unitId,\n" +
                "c.unitName\n" +
                "from tbl_inv_purchase_a a \n" +
                "inner join tbl_item_code b on a.brandId=b.brandId\n" +
                "inner join tbl_inv_unit c on c.unitId=a.unitId\n" +
                "where a.companyId=:companyId and a.purchaseInvoiceNo=:purchaseInvoiceNo";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate
                .setParameter("purchaseInvoiceNo", purchaseInvoiceNo)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public PurchaseDTO getPurchaseDetailByVoucherNo(Integer voucherNo, CurrentUser currentUser) {
        String sqlQry = "SELECT purchaseId, purchaseDate FROM tbl_inv_purchase_a where  purchaseVoucherNo=:voucherNo and companyId=:companyId limit 1;";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return (PurchaseDTO) hibernate.setParameter("voucherNo", voucherNo)
                .setParameter("companyId", currentUser.getCompanyId())
                .setResultTransformer(Transformers.aliasToBean(PurchaseDTO.class)).uniqueResult();
    }

    @Transactional
    public void deleteAllThePurchaseEntry(String purchaseInvoiceNo, Integer companyId) {
        String sqlQry = "delete FROM tbl_inv_purchase_a where companyId=:companyId and purchaseInvoiceNo=:purchaseInvoiceNo";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        hibernate.setParameter("purchaseInvoiceNo", purchaseInvoiceNo)
                .setParameter("companyId", companyId).executeUpdate();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getUnitList() {
        String query = "SELECT unitId AS value,unitName AS text FROM tbl_inv_unit";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Boolean isCashLedgerExist(CurrentUser currentUser, Integer accTypeId) {
        String sqlQry = "select count(*) from tbl_acc_ledger where accTypeId=:accTypeId and companyId=:companyId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.setParameter("accTypeId", accTypeId)
                .setParameter("companyId", currentUser.getCompanyId()).uniqueResult().equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public Boolean checkIsItemNameExist(String itemName, CurrentUser currentUser, String itemCode) {
        String sqlQry = "SELECT * FROM tbl_inv_purchase where itemName=:itemName and companyId=:companyId and itemCode!=:itemCode";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.setParameter("itemName", itemName)
                        .setParameter("itemCode", itemCode)
                .setParameter("companyId", currentUser.getCompanyId()).list().size()>0;
    }
}

