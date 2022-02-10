/**
 * Component Name: Spare part management
 * Name: HomeController
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 * @author: bikash.rai
 * Creation: 04-May-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 */
package com.spms.mvc.web.config;

import org.hibernate.dialect.MySQLDialect;


public class MySQLCustomDialect extends MySQLDialect {
    //region public method
    public MySQLCustomDialect() {
        super();
        registerHibernateType(-9, "string");
        registerHibernateType(0, "integer");
    }
    //endregion
}


