package com.spms.mvc;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.library.helper.DatePicker;
import com.spms.mvc.library.helper.DateUtil;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(2);

        System.out.println(Math.round(((200 * 0.15) / 365) * 120 * 100.00) / 100.00);
    }

    public static int numberOfDaysInMonth(int month, int year) {
        Calendar monthStart = new GregorianCalendar(year, month, 1);
        return monthStart.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
