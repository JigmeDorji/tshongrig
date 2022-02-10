package com.spms.mvc.dao;

import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.dto.SalarySheetDTO;
import com.spms.mvc.dto.SaleItemListDTO;
import com.spms.mvc.entity.SalarySheet;
import com.spms.mvc.library.helper.CurrentUser;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by jigme.dorji on 4/7/2021.
 */
@Repository("salarySheetDao")
public class SalarySheetDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Integer getMaxSalarySheetId() {
        String query = "SELECT max(salarySheetId) FROM tbl_hr_salary_sheet";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query);
        return result.uniqueResult() == null ? 0 : (Integer) result.uniqueResult();
    }

    @Transactional
    public void save(SalarySheet salarySheet) {
        sessionFactory.getCurrentSession().saveOrUpdate(salarySheet);
    }

    @Transactional(readOnly = true)
    public Integer getMaxMonthId(Integer companyId, Integer financialYearId, Integer cost) {
        String query = "SELECT max(a.monthId) FROM tbl_hr_salary_sheet a\n " +
                "inner join tbl_hr_employeesetup b on a.empId=b.empId \n" +
                "where a.companyId=:companyId and a.financialYearId=:financialYearId and b.cost=:cost";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("cost",cost)
                .setParameter("companyId",companyId)
                .setParameter("financialYearId",financialYearId);
        return result.uniqueResult() == null ? 0 : (Integer) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public Double getTDSAmount(Double netSalary) {
        String query = "SELECT tDSAmount FROM tbl_st_tds_slab\n" +
                "where :netSalary>=fromAmount and :netSalary<=toAmount";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("netSalary",netSalary);
        return (Double) result.uniqueResult();
    }

    @Transactional(readOnly = true)
    public List<EmployeeSetupDTO> getGeneratedSalarySheet(Integer companyId, Integer selectedMonthId, Integer cost) {
        String query = "SELECT a.*,b.empName,b.tpnNo,b.incrementEffectDate,b.incrementAmount,b.dateOfAppointment FROM tbl_hr_salary_sheet a\n" +
                "inner join tbl_hr_employeesetup b on a.empId=b.empId\n" +
                "where a.monthId=:selectedMonthId and a.companyId=:companyId and b.cost=:cost";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("selectedMonthId", selectedMonthId)
                .setParameter("cost", cost)
                .setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public List<EmployeeSetupDTO> getEmpSetupList(Integer companyId, Integer cost, Integer selectedMonthId, Integer year) {
        String query = "Select *, \n" +
                "(basicSalary+ifnull(allowance,0)) as grossSalary,  \n" +
                "((basicSalary+ifnull(allowance,0))-(pF+gIS)) as netSalary, \n" +
                "((basicSalary+ifnull(allowance,0))*0.01) as hC, \n" +
                "((basicSalary+ifnull(allowance,0))*0.02) as tDS \n" +
                "FROM tbl_hr_employeesetup where status='A' and companyId=:companyId and cost=:cost and \n" +
                "CASE WHEN Year(dateOfAppointment)<:year THEN null is null ELSE Month(dateOfAppointment)<=:selectedMonthId END \n";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("cost", cost)
                .setParameter("selectedMonthId", selectedMonthId)
                .setParameter("year", year)
                .setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public double getBalanceAdvance(String ledgerId, Integer companyId) {
        String query = "SELECT sum(a.drcrAmount*-1) \n" +
                "FROM tbl_acc_voucher_entries_detail a\n" +
                "inner join tbl_acc_voucher_entries b on a.voucherId=b.voucherId\n" +
                "where a.ledgerId=:ledgerId and b.companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("ledgerId",ledgerId)
                .setParameter("companyId",companyId);
        return (Double) result.uniqueResult();
    }

    @Transactional
    public void updateBasicSalary(String empId, Double newBasicSalary, BigDecimal newPF, Integer companyId) {
        String query = "update tbl_hr_employeesetup set basicSalary=:newBasicSalary,pf=:newPF where empId=:empId and companyId=:companyId ";
        Session session = sessionFactory.getCurrentSession();
       session.createSQLQuery(query)
                .setParameter("empId",empId)
                .setParameter("newPF",newPF)
                .setParameter("companyId",companyId)
                .setParameter("newBasicSalary",newBasicSalary).executeUpdate();
    }
    @Transactional
    public void insertIncrementAffectedDetail(Integer companyId, Integer selectedMonthId, Integer currentYear, Double incrementAmount, String empId) {
        String query = "INSERT INTO tbl_hr_emp_increment_affected (empId,month,year,amt,companyId)\n" +
                "   VALUES(:empId,:selectedMonthId,:currentYear,:incrementAmount,:companyId)";
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("selectedMonthId",selectedMonthId)
                .setParameter("currentYear",currentYear)
                .setParameter("incrementAmount",incrementAmount)
                .setParameter("empId",empId).executeUpdate();
    }
    @Transactional(readOnly = true)
    public boolean checkIsIncrementAffectedForCurrentCurrentPeriod(Integer companyId, Integer selectedMonthId, Integer currentYear, String empId) {
        String query = " select * from tbl_hr_emp_increment_affected where empId=:empId and month=:selectedMonthId and year=:currentYear and companyId=:companyId";
        Session session = sessionFactory.getCurrentSession();
        org.hibernate.Query result = session.createSQLQuery(query)
                .setParameter("companyId",companyId)
                .setParameter("selectedMonthId",selectedMonthId)
                .setParameter("currentYear",currentYear)
                .setParameter("empId",empId);
        return result.uniqueResult()==null;
    }
}
