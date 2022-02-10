package com.spms.mvc;

import com.spms.mvc.library.helper.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description: Test
 * Date:  2021-Nov-03
 *
 * @author: Bikash Rai
 * @version: 1.0.0
 * ======================
 * Change History:
 * Version:1.0.0
 * Author:
 * Date: 2021-Nov-03
 * Change Description:
 * Search Tag:
 */
public class Test {
    public static void main(String args[]) throws ParseException {

        double netValue = 0.00;
        Double depreciationRate = 0.15;
        Double openingBalance = 5000.00;

        Date financialYearStartDate = new SimpleDateFormat("dd-MMM-yyyy")
                .parse("01-Mar-2021");

        Date asOnDate = new SimpleDateFormat("dd-MMM-yyyy")
                .parse("7-Nov-2021");

        Date purchaseDate = new SimpleDateFormat("dd-MMM-yyyy")
                .parse("01-Nov-2021");

        Calendar cal = Calendar.getInstance();
        cal.setTime(financialYearStartDate);
        int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);
        long totalNoOfDaysDif = DateUtil.dayDifference(financialYearStartDate, asOnDate);

        Double purchaseValue = 1000.0;

        if (openingBalance != null) {
            Double depreciationValue = 166.67;
            Double currentYearDepreciation = ((purchaseValue * depreciationRate) / numOfDays) * totalNoOfDaysDif;
            Double currentYearDepAsDate = currentYearDepreciation + depreciationValue;
            netValue = purchaseValue - currentYearDepAsDate;

        } else {
            totalNoOfDaysDif = DateUtil.dayDifference(purchaseDate, asOnDate);
            Double currentYearDepreciation = ((purchaseValue * depreciationRate) / numOfDays) * totalNoOfDaysDif;
            netValue = purchaseValue - currentYearDepreciation;
        }
        System.out.println(netValue);
    }
}
