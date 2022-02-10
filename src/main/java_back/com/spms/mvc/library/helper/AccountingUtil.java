package com.spms.mvc.library.helper;

/**
 * Description: AccountingUtil
 * Date:  2020-Oct-25
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2020-Oct-25
 * Change Description:
 * Search Tag:
 */
public class AccountingUtil {

    public static Double drAmount(Double drAmount) {
        return (drAmount * -1);
    }

    public static Double crAmount(Double crAmount) {
        return crAmount;
    }
}
