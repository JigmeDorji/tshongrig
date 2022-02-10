package com.spms.mvc.dao;

import com.spms.mvc.dto.SalaryRemittanceDTO;
import com.spms.mvc.entity.SalaryRemittance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Description: SalaryRemittanceDao
 * Date:  2021-Apr-07
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Apr-07
 * Change Description:
 * Search Tag:
 */
@Repository("salaryRemittanceDao")
public class SalaryRemittanceDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<SalaryRemittanceDTO> getEmployeeSalarySheet(Integer financialYearId, Integer companyId, Integer monthId, Integer cost) {
        String query = "SELECT s.salarySheetId, \n" +
                "e.empName,\n" +
                "s.takeHome,\n" +
                "e.accNo FROM tbl_hr_employeesetup e\n" +
                "INNER JOIN tbl_hr_salary_sheet s ON e.empId=s.empId\n" +
                "inner join tbl_hr_employeesetup r on r.empId=s.empId\n" +
                "WHERE s.monthId=:monthId and e.companyId=:companyId and s.financialYearId=:financialYearId and r.cost=:cost";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("monthId", monthId)
                .setParameter("cost", cost)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(SalaryRemittanceDTO.class)).list();
    }

    @Transactional
    public void save(SalaryRemittance salaryRemittance) {
        sessionFactory.getCurrentSession().save(salaryRemittance);
    }

    @Transactional(readOnly = true)
    public boolean isSalaryGeneratedInSalarySheet(Integer financialYearId, Integer companyId, Integer monthId, Integer cost) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT COUNT(*) FROM tbl_hr_salary_sheet a\n" +
                "inner join tbl_hr_employeesetup b on a.empId=b.empId\n" +
                "WHERE a.monthId=:monthId and a.financialYearId=:financialYearId and a.companyId=:companyId and b.cost=:cost";

        return session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("monthId", monthId)
                .setParameter("cost", cost)
                .setParameter("financialYearId", financialYearId)
                .uniqueResult()
                .equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public boolean isSalaryRemittanceCompletedForMonth(Integer financialYearId, Integer companyId, Integer month, Integer cost) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT COUNT(*) FROM tbl_hr_salary_remittance a" +
                " inner join tbl_hr_salary_sheet b on a.salarySheetId=b.salarySheetId\n" +
                " inner join tbl_hr_employeesetup c on c.empId=b.empId\n" +
                "WHERE a.month=:month and a.financialYearId=:financialYearId and a.companyId=:companyId and c.cost=:cost";

        return session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("month", month)
                .setParameter("financialYearId", financialYearId)
                .setParameter("cost", cost)
                .uniqueResult()
                .equals(BigInteger.ZERO);
    }

    @Transactional(readOnly = true)
    public List<SalaryRemittanceDTO> getEmployeeDetailFromSalaryRemittance(Integer financialYearId, Integer companyId, Integer month, String bankLedgerId, Integer cost) {
        String query = "SELECT s.salarySheetId, \n" +
                "e.empName,\n" +
                "s.takeHome,\n" +
                "e.accNo FROM tbl_hr_employeesetup e\n" +
                "INNER JOIN tbl_hr_salary_sheet s ON e.empId=s.empId\n" +
                "INNER JOIN tbl_hr_salary_remittance sr ON s.salarySheetId=sr.salarySheetId\n" +
                "WHERE s.monthId=:month and e.companyId=:companyId and e.cost=:cost and s.financialYearId=:financialYearId AND (:bankLedgerId is null OR sr.bankLedgerId=:bankLedgerId)";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("month", month)
                .setParameter("financialYearId", financialYearId)
                .setParameter("bankLedgerId", bankLedgerId)
                .setParameter("cost", cost)
                .setResultTransformer(Transformers.aliasToBean(SalaryRemittanceDTO.class)).list();
    }
}
