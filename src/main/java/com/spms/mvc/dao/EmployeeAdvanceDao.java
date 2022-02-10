package com.spms.mvc.dao;

import com.spms.mvc.entity.EmployeeAdvance;
import com.spms.mvc.entity.MoneyReceipt;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jigme.dorji on 4/18/2021.
 */
@Repository("employeeAdvanceDao")
public class EmployeeAdvanceDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Integer getMaxEmployeeAdvanceId() {
        String query = "SELECT MAX(id) FROM tbl_hr_employeeadvance";
        Session session = sessionFactory.getCurrentSession();
        return session.createSQLQuery(query).uniqueResult()==null?0:(Integer) session.createSQLQuery(query).uniqueResult();
    }

    @Transactional
    public Integer save(EmployeeAdvance employeeAdvance) {
        sessionFactory.getCurrentSession().saveOrUpdate(employeeAdvance);
        return employeeAdvance.getId();
    }
}
