package com.spms.mvc.dao;

import com.spms.mvc.dto.EmployeeSetupDTO;
import com.spms.mvc.entity.OtherRemittance;
import com.spms.mvc.entity.StatutoryRemittance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by jigme.dorji on 5/2/2021.
 */
@Repository("otherRemittanceDao")
public class OtherRemittanceDao {
    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public List<EmployeeSetupDTO> getOtherRemittanceDetails(Integer financialYearId, Integer companyId, Integer month, Integer cost) {
        String query = "SELECT s.salarySheetId, \n" +
                "e.empName,\n" +
                "e.tpnNo,\n" +
                "s.basicSalary,\n" +
                "(s.basicSalary + s.advance) as grossSalary,\n" +
                "s.pF,\n" +
                "s.gIS,\n" +
                "(s.pF + s.gIS) as totalAmount,\n" +
                "e.accNo FROM tbl_hr_employeesetup e\n" +
                "INNER JOIN tbl_hr_salary_sheet s ON e.empId=s.empId\n" +
                "WHERE s.monthId=:month and e.companyId=:companyId and s.financialYearId=:financialYearId and e.cost=:cost";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("month", month)
                .setParameter("cost", cost)
                .setParameter("financialYearId", financialYearId)
                .setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class)).list();
    }

    @Transactional
    public void save(OtherRemittance otherRemittance) {
        sessionFactory.getCurrentSession().save(otherRemittance);
    }

    @Transactional(readOnly = true)
    public List<EmployeeSetupDTO> getOtherRemittanceDetails(Integer financialYearId, Integer companyId, Integer month, String bankLedgerId,Integer cost) {
        String query = "SELECT s.salarySheetId, \n" +
                "e.empName,\n" +
                "e.tpnNo,\n" +
                "s.basicSalary,\n" +
                "(s.pF + s.gIS) as totalAmount,\n" +
                "s.pF,\n" +
                "s.gIS,\n" +
                "e.accNo FROM tbl_hr_employeesetup e\n" +
                "INNER JOIN tbl_hr_salary_sheet s ON e.empId=s.empId\n" +
                "INNER JOIN tbl_hr_other_remittance sr ON s.salarySheetId=sr.salarySheetId\n" +
                "WHERE e.cost=:cost and s.monthId=:month and e.companyId=:companyId and s.financialYearId=:financialYearId AND (:bankLedgerId is null OR sr.bankLedgerId=:bankLedgerId)";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query)
                .setParameter("companyId", companyId)
                .setParameter("month", month)
                .setParameter("cost", cost)
                .setParameter("financialYearId", financialYearId)
                .setParameter("bankLedgerId", bankLedgerId)
                .setResultTransformer(Transformers.aliasToBean(EmployeeSetupDTO.class)).list();
    }

    @Transactional(readOnly = true)
    public Boolean isOtherRemittanceCompleted(Integer financialYearId, Integer companyId, Integer month, Integer cost) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT COUNT(*) FROM tbl_hr_other_remittance a\n" +
                "INNER JOIN tbl_hr_salary_sheet b ON a.salarySheetId=b.salarySheetId\n" +
                "INNER JOIN tbl_hr_employeesetup c ON c.empId=b.empId\n" +
                "WHERE a.month=:month and a.financialYearId=:financialYearId and a.companyId=:companyId and c.cost=:cost";

        return session.createSQLQuery(sql)
                .setParameter("companyId", companyId)
                .setParameter("month", month)
                .setParameter("financialYearId", financialYearId)
                .setParameter("cost", cost)
                .uniqueResult()
                .equals(BigInteger.ZERO);
    }
}
