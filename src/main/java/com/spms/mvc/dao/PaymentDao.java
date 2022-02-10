package com.spms.mvc.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: PaymentDao
 * Date:  2021-May-15
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-May-15
 * Change Description:
 * Search Tag:
 */
@Repository("paymentDao")
public class PaymentDao {
    @Autowired
    SessionFactory sessionFactory;
}
