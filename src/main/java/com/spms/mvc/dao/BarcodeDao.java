package com.spms.mvc.dao;

import com.spms.mvc.dto.BarcodeDTO;
import com.spms.mvc.dto.SaleRecordDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by SonamPC on 20-Dec-16.
 */
@Repository("barcodeDao")
public class BarcodeDao {
    @Autowired
    SessionFactory sessionFactory;
    private List<SaleRecordDTO> report;

    @Transactional(readOnly = true)
    public SaleRecordDTO getItemCodeList(BarcodeDTO barcodeDTO) {
        String query = "SELECT itemlevelcode AS itemCode,\n" +
                "partDescription AS itemName,\n" +
                "itempriceperqty AS pricePerQty\n" +
                "FROM tbl_itemcategory WHERE itemcategoryid=:itemId";
        Session session = sessionFactory.getCurrentSession();
        return (SaleRecordDTO) session.createSQLQuery(query)
                .setParameter("itemId", barcodeDTO.getItemCategoryId())
                .setResultTransformer(Transformers.aliasToBean(SaleRecordDTO.class)).uniqueResult();
    }




    /*@Transactional(readOnly = true)
    public List<SaleReportDTO> getReport() {
        String query = "SELECT ic.itemcategoryname AS itemName,\n" +
                "sr.sellingprice AS sellingPrice \n" +
                "FROM tbl_salerecord sr\n" +
                "inner join tbl_itemcategory ic";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(SaleReportDTO.class)).list();
    }*/
}
