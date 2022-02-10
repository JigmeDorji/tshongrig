package com.spms.mvc.dao;

import com.spms.mvc.entity.CreditPaymentForReceiveItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Bcass Sawa on 2/13/2018.
 */
@Repository("creditPaymentForReceiveItemDao")
public class CreditPaymentForReceiveItemDao {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Integer getMaxSerialId() {
        Session session = sessionFactory.getCurrentSession();
        String sql = "SELECT MAX(id) FROM tbl_credit_payment_for_receiveitem";
        return session.createSQLQuery(sql).uniqueResult() == null ? 0 : (Integer) session.createSQLQuery(sql).uniqueResult();
    }

    @Transactional
    public void saveCreditPaymentDetail(CreditPaymentForReceiveItem creditPaymentForReceiveItem) {
        sessionFactory.getCurrentSession().saveOrUpdate(creditPaymentForReceiveItem);
    }
}
