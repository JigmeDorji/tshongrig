package com.spms.mvc.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Description: AssetBuyingDao
 * Date:  2021-Oct-04
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Oct-04
 * Change Description:
 * Search Tag:
 */
@Repository("assetBuyingDao")
public class AssetBuyingDao {

    @Autowired
    SessionFactory sessionFactory;


}
