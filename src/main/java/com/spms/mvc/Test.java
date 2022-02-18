package com.spms.mvc;

import com.spms.mvc.library.helper.DateUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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


        /*accounting entry test*/

        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setMaximumFractionDigits(2);

        List<Double> list = new ArrayList<>();
        list.add(format.parse("8185").doubleValue());
        list.add(format.parse("2035").doubleValue());
        list.add(format.parse("11323").doubleValue());
        list.add(format.parse("1200").doubleValue());
        list.add(format.parse("2000").doubleValue());
        list.add(format.parse("6000").doubleValue());
        list.add(format.parse("172777").doubleValue());
        Double va = 0.0;
        for (Double value : list) {
            va = va + value;
        }
        System.out.println(va);


        NumberFormat format2 = NumberFormat.getInstance(Locale.getDefault());
        format2.setMaximumFractionDigits(2);
        format2.setMinimumFractionDigits(2);

        System.out.println(format2.parse("1,234.23455").doubleValue());


    }
}
