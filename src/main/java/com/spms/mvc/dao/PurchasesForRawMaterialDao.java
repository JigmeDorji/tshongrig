package com.spms.mvc.dao;

import com.spms.mvc.dto.PurchaseDTO;
import com.spms.mvc.dto.PurchasesForRawMaterialListDTO;
import com.spms.mvc.dto.PurchasesForRawMaterialSaveDTO;
import com.spms.mvc.entity.PurchaseCreditSupplierDetail;
import com.spms.mvc.entity.RawMaterialLocationSetup;
import com.spms.mvc.entity.RawMaterialPurchasesStorage;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository("purchasesForRawMaterialDao")
public class PurchasesForRawMaterialDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void save(RawMaterialPurchasesStorage rawMaterialPurchasesStorage) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(rawMaterialPurchasesStorage);

    }



    @Transactional(readOnly = true)
    public PurchasesForRawMaterialListDTO getItemDetails(String itemName, Integer companyId) {
            String query = "SELECT itemName AS itemName, \n" +
                    "purchaseId AS purchaseId,\n" +
                    "qty AS qty,\n" +
                    "price AS price,\n" +
                    "locationId AS locationId\n" +
                    "from tbl_raw_material_purchases_storage WHERE itemName=:itemName and companyId=:companyId";


            Session session = sessionFactory.getCurrentSession();
            return (PurchasesForRawMaterialListDTO) session.createSQLQuery(query)
                    .setParameter("itemName", itemName)
                    .setParameter("companyId", companyId)
                    .setResultTransformer(Transformers.aliasToBean(PurchasesForRawMaterialListDTO.class)).uniqueResult();
        }



//    @Transactional(readOnly = true)

//    public PurchasesForRawMaterialSaveDTO getItemDetails(String itemName, Integer companyId) {
//            String query = "SELECT A.itemName AS itemName, \n" +
//                    "A.purchaseId AS purchaseId,\n" +
//                    "A.qty AS qty,\n" +
//                    "A.Price AS Price,\n" +
//                    "A.locationId AS locationId,\n" +
//                    "c.unitId AS unitId\n" +
//                    "FROM tbl_inv_purchase A\n" +
//                    "INNER JOIN tbl_item_code B ON A.brandId=B.brandId\n" +
//                    "INNER JOIN tbl_inv_unit c ON c.unitId=A.unitId\n" +
//                    "WHERE A.itemName=:itemName and A.companyId=:companyId";
//            Session session = sessionFactory.getCurrentSession();
//            return (PurchasesForRawMaterialSaveDTO) session.createSQLQuery(query)
//                    .setParameter("itemName", itemName)
//                    .setParameter("companyId", companyId)
//                    .setResultTransformer(Transformers.aliasToBean(PurchasesForRawMaterialSaveDTO.class)).uniqueResult();
//        }




    @Transactional(readOnly = true)
    public Boolean isCashLedgerExist(CurrentUser currentUser, Integer accTypeId) {
        String sqlQry = "select count(*) from tbl_acc_ledger where accTypeId=:accTypeId and companyId=:companyId";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQry);
        return hibernate.setParameter("accTypeId", accTypeId)
                .setParameter("companyId", currentUser.getCompanyId()).uniqueResult().equals(BigInteger.ZERO);
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
    public Double getTotalCashOutFlow(String ledgerId, Integer companyId, Date curFromDate,Date curToDate) {
      /*  String sqlQuery = "SELECT\n" +
                "SUM(c.drcrAmount) as amount\n" +
                "FROM tbl_acc_acctype a\n" +
                "LEFT JOIN tbl_acc_ledger b ON a.accTypeId=b.accTypeId\n" +
                "LEFT JOIN tbl_acc_voucher_entries_detail c ON b.ledgerId=c.ledgerId\n" +
                "LEFT JOIN tbl_acc_voucher_entries d on d.voucherId=c.voucherId\n" +
                "WHERE d.companyId=:companyId AND d.financialYearId=:financialYearId AND a.accTypeId=:accountType AND c.drcrAmount > 0\n" +
                "GROUP BY a.accTypeId";*/
        String sqlQuery="SELECT\n" +
                "SUM(IFNULL(A.drcrAmount,0)) AS drcrAmount\n" +
                "FROM tbl_acc_voucher_entries_detail A\n" +
                "INNER JOIN tbl_acc_voucher_entries B ON A.voucherId=B.voucherId\n" +
                "INNER JOIN tbl_acc_vouchertype C ON C.voucherTypeId=B.voucherTypeId\n" +
                "where A.ledgerId=:ledgerId and B.voucherEntryDate>=:curFromDate and B.voucherEntryDate<=:curToDate and B.companyId=:companyId and A.drcrAmount>0";
        SQLQuery hibernate = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery);
        return (Double) hibernate.setParameter("ledgerId", ledgerId)
                .setParameter("companyId", companyId)
                .setParameter("curFromDate", curFromDate)
                .setParameter("curToDate", curToDate)
                .uniqueResult();
    }



    @Transactional
    public void savePurchaseCreditSupplierDetail(PurchaseCreditSupplierDetail purchaseCreditSupplierDetail) {
        sessionFactory.getCurrentSession().save(purchaseCreditSupplierDetail);
    }


}

/**


 create table tbl_raw_material_purchase(
 purchaseId bigint not null auto_increment,
 purchaseDate datetime,
 purchaseInvoiceNo varchar(100),
 itemName varchar(100),
 qty int,
 unitId int,
 price decimal(18,2),
 locationId int,
 companyId int,
 setDate Date,
 createdBy varchar(15),
 primary key(purchaseId)
 );

 */
