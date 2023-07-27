package com.spms.mvc;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class CalculateCurrentYearDepCUtil {

    public static boolean isPurchaseInCurrentYear(Date purchaseDate) {
        return getYearFromDate(purchaseDate) == getYearFromDate(new Date());
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }


    public static int calculateTheNumOfDays(Date purchaseDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseDate);
        // Extract the target year, month, and day
        int targetYear = calendar.get(Calendar.YEAR);
        int targetMonth = calendar.get(Calendar.MONTH) + 1; // Months are 0-based, so add 1
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Create the target date with the assigned values
        LocalDate date = LocalDate.of(targetYear, targetMonth, targetDay);
        // Get the end of the month for the given date
        LocalDate endOfMonth = date.with(TemporalAdjusters.lastDayOfMonth());
        // Calculate the number of days from the given date to the end of the month
        int daysToEndOfMonth = (date.lengthOfMonth() - date.getDayOfMonth()) + 1;
        System.out.println("Number of days from " + date + " to the end of the month: " + daysToEndOfMonth);
        return daysToEndOfMonth;
    }


    public static int purchaseFromMonth(Date purchaseDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseDate);
        // Create a YearMonth instance for the specified year and month
        return calendar.get(Calendar.MONTH) + 1;
    }


    public static void numberOfDaysInMonth(int month, int year) {

        // Create a Calendar instance and set it to the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), month);
        // Get the number of days in the specified month
        int daysInMonth = yearMonth.lengthOfMonth();
        System.out.println("Number of days in " + year + "-" + month + ": " + daysInMonth);
    }

    public static int numberOfDaysInCurrentMonth(int month) {
        // Create a Calendar instance and set it to the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), month);
        // Get the number of days in the specified month
        return yearMonth.lengthOfMonth();

    }

    public static double formattedDoubleValue(double rawCurrentDepC) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        return Double.parseDouble(decimalFormat.format(rawCurrentDepC));
    }

//    public static int numberOfDaysDiff(Date date) {
//        // Create a Calendar instance and set it to the Date object
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//
//        // Extract the target year, month, and day
//        int targetYear = calendar.get(Calendar.YEAR);
//        int targetMonth = calendar.get(Calendar.MONTH) + 1; // Months are 0-based, so add 1
//        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
//        // Create the target date with the assigned values
//        LocalDate targetDate = LocalDate.of(targetYear, targetMonth, targetDay);
//        // Get the current date
//        LocalDate currentDate = LocalDate.now();
//        // Calculate the number of days till the target date in this year (including the starting date)
//        return Math.toIntExact(Math.abs(ChronoUnit.DAYS.between(currentDate, targetDate)) + 1);
//    }


}
