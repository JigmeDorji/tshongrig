package com.spms.mvc.dao;

import com.spms.mvc.dto.AccBalanceSheetDTO;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by jigmePc on 5/22/2019.
 */
@Repository("accBalanceSheetReportDao")
public class AccBalanceSheetReportDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<AccBalanceSheetDTO> getAccBalanceSheetReport(CurrentUser currentUser, Date asOnDate) throws ParseException {
        String sqlQry = "CALL sp_acc_balance_sheet_report(:companyId,'BS',:asOnDate,:financialYearFromDate,:financialYearToDate)";
        return sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", currentUser.getCompanyId())
                .setParameter("asOnDate", asOnDate)
                .setParameter("financialYearFromDate", DateUtil.toDate(currentUser.getFinancialYearFrom()))
                .setParameter("financialYearToDate",  DateUtil.toDate(currentUser.getFinancialYearTo()))
                .setResultTransformer(Transformers.aliasToBean(AccBalanceSheetDTO.class))
                .list();
    }

    @Transactional(readOnly = true)
    public Double getInventoryBalance(Integer companyId) {
        String sqlQry = "SELECT SUM(amount) FROM (SELECT \n" +
                "(a.costPrice * (a.qty+SUM(IFNULL(c.returnQty,0)))) AS amount\n" +
                "FROM tbl_inv_purchase a\n" +
                "LEFT JOIN tbl_inv_return_item c on c.itemCode=a.itemCode\n" +
                "WHERE a.companyId=:companyId\n" +
                "GROUP BY a.itemCode) AB ";
        Double totalAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId).uniqueResult();
        return totalAmount == null ? 0 : totalAmount;
    }

    @Transactional(readOnly = true)
    public Double getTotalPurchase(Integer companyId, Date fromDate, Date toDate) {
        String sqlQry = "SELECT SUM(a.costPrice*a.qty) \n" +
                "FROM tbl_inv_purchase_a a\n" +
                "WHERE a.companyId=:companyId and a.cmdFlag='C' and \n" +
                "(:fromDate is null OR a.purchaseDate>=:fromDate) and \n" +
                "a.purchaseDate<=:toDate\n";
        Double totalAmount = (Double) sessionFactory.getCurrentSession()
                .createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .uniqueResult();
        return totalAmount == null ? 0 : totalAmount;
    }


    @Transactional
    public Double getOpenBalDiffAmt(Integer companyId, Date financialYearFrom, Date financialYearTo) {
        String sqlQry = "select ufn_get_opening_balance_diff(:companyId,:financialYearFromDate,:financialYearToDate)";
        return (Double) sessionFactory.getCurrentSession().createSQLQuery(sqlQry)
                .setParameter("companyId", companyId)
                .setParameter("financialYearFromDate", financialYearFrom)
                .setParameter("financialYearToDate",  financialYearTo)
                .uniqueResult();
    }


}
