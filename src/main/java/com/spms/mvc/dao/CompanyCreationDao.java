package com.spms.mvc.dao;

import com.spms.mvc.dto.CompanyCreationDTO;
import com.spms.mvc.dto.FinancialYearDTO;
import com.spms.mvc.entity.CommonCompanyLoginId;
import com.spms.mvc.entity.CompanyCreation;
import com.spms.mvc.library.helper.DropdownDTO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Bcass Sawa on 5/3/2019.
 */
@Repository("companyCreationDao")
public class CompanyCreationDao {

    @Autowired
    SessionFactory sessionFactory;


    @Transactional(readOnly = true)
    public List<DropdownDTO> getBusinessTypeDropdown() {
        String query = "SELECT businessTypeId AS valueInteger, businessTypeName AS text FROM tbl_common_business_type";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<CompanyCreationDTO> getCompanyDetailList() {
//        String query = "SELECT distinct id AS companyId,\n" +
//                "                CONCAT(companyName,\"(\",ifnull(username,\"---\"),\")\") AS companyName,\n" +
//                "                mailingAddress AS mailingAddress,\n" +
//                "                mobileNo AS mobileNo,\n" +
//                "                email AS email,\n" +
//                "                website AS website, \n" +
//                "                fnYrStart AS fnYrStart,\n" +
//                "                pfPercentage AS pfPercentage,\n" +
//                "                contactPerson,\n" +
//                "                status,\n" +
//                "                trialExpiryDate,\n" +
//                "                businessType AS businessType\n" +
//                "                FROM tbl_common_company c \n" +
//                "                left join tbl_user  on id=companyId \n" +
//                "                group by companyId order by id desc";
        String query="select distinct b.id as companyId, \n" +
                "CONCAT(companyName, '(', COALESCE(c.loginId, '---'), ')') AS companyName,\n" +
                "b.mailingAddress AS mailingAddress,\n" +
                "b.mobileNo AS mobileNo,\n" +
                "b.email AS email,\n" +
                "b.website AS website,\n" +
                "b.fnYrStart AS fnYrStart,\n" +
                "b.pfPercentage AS pfPercentage,\n" +
                "b.contactPerson,\n" +
                "b.status,\n" +
                "b.trialExpiryDate,\n" +
                "b.businessType AS businessType\n" +
                "from tbl_common_company b left join tbl_common_company_login_id c on c.companyId=b.id\n" +
                "group by companyId order by companyId desc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setResultTransformer(Transformers.aliasToBean(CompanyCreationDTO.class)).list();
    }

//    public List<CompanyCreationDTO> getCompanyDetailList() {
//        String query = "SELECT distinct c.id AS companyId,\n" +
//                "                CONCAT(c.companyName,\"(\",ifnull(u.username,\"---\"),\")\") AS companyName,\n" +
//                "                c.mailingAddress AS mailingAddress,\n" +
//                "                c.mobileNo AS mobileNo,\n" +
//                "                c.email AS email,\n" +
//                "                c.website AS website, \n" +
//                "                c.fnYrStart AS fnYrStart,\n" +
//                "                c.pfPercentage AS pfPercentage,\n" +
//                "                c.contactPerson,\n" +
//                "                c.status,\n" +
//                "                c.trialExpiryDate,\n" +
//                "                c.businessType AS businessType\n" +
//                "                FROM tbl_common_company c \n" +
//                "                left join tbl_user u on c.id=u.companyId \n" +
//                "                group by companyId order by id desc";
//        Session session = sessionFactory.getCurrentSession();
//        return session.createSQLQuery(query)
//                .setResultTransformer(Transformers.aliasToBean(CompanyCreationDTO.class)).list();
//    }

    @Transactional(readOnly = true)
    public CompanyCreationDTO populateCompanyDetail(Integer companyId) {
        String query = "SELECT id AS companyId," +
                " companyName," +
                " mailingAddress," +
                " contactPerson," +
                " mobileNo," +
                " email," +
                " website, " +
                " fnYrStart," +
                " pfPercentage," +
                " status, " +
                " remarks, " +
                " businessType FROM tbl_common_company WHERE id=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (CompanyCreationDTO) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(CompanyCreationDTO.class)).uniqueResult();
    }

    @Transactional
    public void saveCompanyDetails(CompanyCreation companyCreation) {
        sessionFactory.getCurrentSession().save(companyCreation);
    }

    @Transactional
    public void updateCompanyDetails(CompanyCreation companyCreation) {
        sessionFactory.getCurrentSession().update(companyCreation);
    }

    @Transactional
    public void deleteCompanyDetails(Integer companyId) {
        sessionFactory.getCurrentSession()
                .createSQLQuery("DELETE FROM tbl_common_company WHERE id=:companyId")
                .setParameter("companyId", companyId).executeUpdate();
    }

    @Transactional(readOnly = true)
    public Integer getCompanyId() {
        String query = "SELECT id FROM tbl_common_company order by id desc limit 1";
        Session session = sessionFactory.getCurrentSession();
        return (Integer) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional
    public void saveCompanyRelatedVoucherCountDetail(Integer companyId, Integer financialYearId) {
        Session session = sessionFactory.getCurrentSession();
        String sql1 = "INSERT INTO tbl_acc_voucher_count SET voucherSerial=0,voucherTypeId=1,financialYearId=:financialYearId, companyId=:companyId \n";
        session.createSQLQuery(sql1)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();

        String sql2 = "INSERT INTO tbl_acc_voucher_count SET voucherSerial=0,voucherTypeId=2,financialYearId=:financialYearId,companyId=:companyId \n";
        session.createSQLQuery(sql2)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();

        String sql3 = "INSERT INTO tbl_acc_voucher_count SET voucherSerial=0,voucherTypeId=3,financialYearId=:financialYearId, companyId=:companyId \n";
        session.createSQLQuery(sql3)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();

        String sql4 = "INSERT INTO tbl_acc_voucher_count SET voucherSerial=0,voucherTypeId=4,financialYearId=:financialYearId,companyId=:companyId \n";
        session.createSQLQuery(sql4)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();

        String sql5 = "INSERT INTO tbl_acc_voucher_count SET voucherSerial=0,voucherTypeId=5,financialYearId=:financialYearId,companyId=:companyId \n";
        session.createSQLQuery(sql5)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();

        String sql6 = "INSERT INTO tbl_acc_voucher_count SET voucherSerial=0,voucherTypeId=6,financialYearId=:financialYearId,companyId=:companyId \n";
        session.createSQLQuery(sql6)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();


    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> loadCompanyList() {
        String query = "SELECT id AS value, companyName AS text FROM tbl_common_company ";
        return sessionFactory.getCurrentSession().createSQLQuery(query).setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();


    }

    @Transactional(readOnly = true)
    public CompanyCreationDTO getSelectedCompanyDetails(Integer companyId) {
        String query = "SELECT id AS companyId, \n" +
                "companyName AS companyName,\n" +
                "mailingAddress AS mailingAddress,\n" +
                "mobileNo AS mobileNo,\n" +
                "email AS email,\n" +
                "businessType AS businessType,\n" +
                "pfPercentage AS pfPercentage\n" +
                "FROM tbl_common_company WHERE id=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return (CompanyCreationDTO) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(CompanyCreationDTO.class)).uniqueResult();
    }

    @Transactional(readOnly = true)
    public FinancialYearDTO getCurrentFinancialYearIdByCompany(Integer companyId) {
        String query = "SELECT financialYearId, financialYearFrom,financialYearTo FROM tbl_financial_year_setup WHERE companyId=:companyId AND status='A'";
        Session session = sessionFactory.getCurrentSession();
        return (FinancialYearDTO) session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(FinancialYearDTO.class)).uniqueResult();
    }

    @Transactional
    public void performYearClosing(Integer companyId, Integer financialYearId) {
        String query = "CALL sp_acc_financial_year_closing(:companyId,:financialYearId)";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .executeUpdate();
    }

    @Transactional(readOnly = true)
    public Integer getMaxFinancialYearId() {
        String query = "SELECT max(financialYearId) FROM tbl_financial_year_setup";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).uniqueResult() == null ? 0 : (Integer) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional(readOnly = true)
    public Boolean isCompanyNameExists(String companyName) {
        String query = "select * from tbl_common_company where LOWER(`companyName`) = LOWER(:companyName)";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyName", companyName)
                .list().size() > 0;
    }

    @Transactional(readOnly = true)
    public List<CompanyCreationDTO> getTotalSale(Integer companyId, Integer financialYearId) {
        String query = "SELECT SUM(b.qty * sellingPrice) AS totalSale, saleDate  FROM tbl_inv_sale_record a\n" +
                "INNER JOIN tbl_inv_sale_record_detail b on a.saleRecordId=b.saleRecordId where a.companyId=:companyId and a.financialYearId=:financialYearId \n" +
                "GROUP BY a.saleDate order by a.saleDate desc LIMIT 5 ";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(CompanyCreationDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getLoginCompany(Integer companyId) {
        String query = "SELECT id AS value, companyName AS text FROM tbl_common_company where id=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<Integer> loadMappedCompany(BigInteger userId) {
        String query = "SELECT a.companyId FROM tbl_company_mapping a\n" +
                "INNER JOIN tbl_user b ON a.userId=b.userId\n" +
                "WHERE a.userId=:userId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("userId", userId).list();
    }

    @Transactional(readOnly = true)
    public Boolean checkIsFinancialYearExistsForCompany(Integer companyId, Integer financialYearId) {
        String query = "SELECT COUNT(*) FROM tbl_acc_voucher_count where companyId=:companyId AND financialYearId=:financialYearId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("financialYearId", financialYearId).uniqueResult()
                .equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public BigInteger getUserIdOfSuperAdmin() {
        String query = "SELECT userId FROM tbl_user where userRoleTypeId=0";
        Session session = sessionFactory.getCurrentSession();
        return (BigInteger) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<DropdownDTO> getScreenList(BigInteger userId) {
        String query = "SELECT b.screenUrl As id,\n" +
                " b.screenName as text \n" +
                "FROM bcs_db.tbl_useraccesspermission a\n" +
                "INNER JOIN tbl_screen b ON a.screenId=b.screenId\n" +
                "INNER JOIN tbl_user_role_type c ON a.userRoleTypeId=c.userRoleTypeId\n" +
                "INNER JOIN tbl_user d ON c.userRoleTypeId=d.userRoleTypeId\n" +
                "WHERE userId=:userId and a.isScreenAccessAllowed='Y'\n";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("userId", userId)
                .setResultTransformer(Transformers.aliasToBean(DropdownDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<CompanyCreationDTO> getCompanyLoginCompany(Integer companyId) {
        String query = "SELECT id AS companyId," +
                " companyName AS companyName," +
                " mailingAddress AS mailingAddress," +
                " mobileNo AS mobileNo," +
                " email AS email," +
                " website AS website, " +
                " fnYrStart AS fnYrStart," +
                " pfPercentage AS pfPercentage," +
                " contactPerson," +
                " status," +
                " trialExpiryDate," +
                " businessType AS businessType FROM tbl_common_company where id=:companyId order by id desc";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(CompanyCreationDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<CommonCompanyLoginId> getCompanyLoginDetail(Integer companyId) {
        String query = "SELECT id AS id," +
                " companyId AS companyId," +
                " loginId AS  companyLoginId," +
                " company AS company" +
                "  FROM tbl_common_company_login_id where companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setResultTransformer(Transformers.aliasToBean(CommonCompanyLoginId.class)).list();
    }

    public void saveCompanyLoginDetail(CommonCompanyLoginId commonCompanyLoginId) {

        Session session = sessionFactory.getCurrentSession();
        session.save(commonCompanyLoginId);

    }
}
