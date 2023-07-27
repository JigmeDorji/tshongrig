package com.spms.mvc;

import com.spms.mvc.Enumeration.AccountTypeEnum;
import com.spms.mvc.library.helper.CurrentUser;
import com.spms.mvc.library.helper.DateUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws ParseException {
//


        String dateString = "2023-03-15"; // Your date string in the format "yyyy-MM-dd"

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateString);

        if (isPurchaseInCurrentYear(date)) {
            Double total = 0.0;
            for (int startMonth = purchaseFromMonth(date); startMonth <= 7; startMonth++) {
                System.out.println(startMonth);
                int numOfDays;
                if (startMonth == purchaseFromMonth(date)) {
                    numOfDays = calculateTheNumOfDays(date);

                } else {
                    numOfDays = numberOfDaysInCurrentMonth(startMonth);

                }
                double currentDepC = ((1000 * 0.15) / 365) * numOfDays;
                total+=currentDepC;

                System.out.println("===========================================");
                System.out.println("Current DepC. =" + filteredCurrentYearDepC(currentDepC));

                System.out.println("Total:= "+total);
                System.out.println(numOfDays);
                numberOfDaysInMonth(startMonth, 2023);
                System.out.println("===========================================");
            }

        }

    }


    public Double calculateDepreciationAmount(CurrentUser currentUser, Integer accTypeId, Double rate, int month, Date purchaseDate) throws ParseException {

        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        numberFormat.setMaximumFractionDigits(2);

        Double depreciationRate = accTypeId.equals(AccountTypeEnum.BUILDING_AND_AMENITIES.getValue()) ? 0.03 : 0.15;
        Calendar cal = Calendar.getInstance();

        cal.setTime(DateUtil.toDate(currentUser.getFinancialYearFrom()));
        int numOfDays = cal.getActualMaximum(Calendar.DAY_OF_YEAR);

        int totalNoOfDaysDif = 0;

        if (isPurchaseInCurrentYear(purchaseDate)) {
            totalNoOfDaysDif = numberOfDaysDiff(purchaseDate);
        } else {
            numberOfDaysInMonth(month, currentUser.getCreatedDate().getYear());
        }

        return Double.parseDouble(numberFormat.format(((rate * depreciationRate) / numOfDays) * totalNoOfDaysDif));
    }


    private static boolean isPurchaseInCurrentYear(Date purchaseDate) {
        return getYearFromDate(purchaseDate) == getYearFromDate(new Date());
    }

    private static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int numberOfDaysDiff(Date date) {
        // Create a Calendar instance and set it to the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // Extract the target year, month, and day
        int targetYear = calendar.get(Calendar.YEAR);
        int targetMonth = calendar.get(Calendar.MONTH) + 1; // Months are 0-based, so add 1
        int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
        // Create the target date with the assigned values
        LocalDate targetDate = LocalDate.of(targetYear, targetMonth, targetDay);
        // Get the current date
        LocalDate currentDate = LocalDate.now();
        // Calculate the number of days till the target date in this year (including the starting date)
        return Math.toIntExact(Math.abs(ChronoUnit.DAYS.between(currentDate, targetDate)) + 1);
    }


    private static int calculateTheNumOfDays(Date purchaseDate) {
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


    private static int purchaseFromMonth(Date purchaseDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseDate);
        // Create a YearMonth instance for the specified year and month
        return calendar.get(Calendar.MONTH);
    }


    public static void numberOfDaysInMonth(int month, int year) {

        // Create a Calendar instance and set it to the Date object
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        YearMonth yearMonth = YearMonth.of(calendar.get(Calendar.YEAR), month-1);
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

    private static double filteredCurrentYearDepC(double rawCurrentDepC) {
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        return Double.parseDouble(decimalFormat.format(rawCurrentDepC));
    }


}
