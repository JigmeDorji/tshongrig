 /**
 * Component Name: Spare part management
 * Name: DateUtil
 * Description: See the description at the top of class declaration
 * Project: Spare part management
 *
 * @author: bikash.rai
 * Creation: 29-Apr-2016
 * @version: 1.0.0
 * @since 2016
 * Language: Java 1.8.0_20
 * Copyright: (C) 2016
 */
package com.spms.mvc.library.helper;


import org.springframework.ui.Model;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class DateUtil {

    //region private variables
    public static final String DD_MM_YYYY = "dd-MM-yyyy";
    public static final String DD_MMM_YYYY = "dd-MMM-yyyy";
    public static final String DDMMYY = "ddMMyy";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MMM_DD = "yyyy-MMM-dd";

    private static final ZoneId DEFAULT_ZONE = ZoneId.systemDefault();

    private static final TemporalAdjuster FIRST_DAY_OF_MONTH = TemporalAdjusters
            .firstDayOfMonth();

    private static final TemporalAdjuster FIRST_DAY_OF_YEAR = TemporalAdjusters
            .firstDayOfYear();

    private static final TemporalAdjuster FIRST_DAY_OF_NEXT_MONTH = TemporalAdjusters
            .firstDayOfNextMonth();

    private static final TemporalAdjuster FIRST_DAY_OF_NEXT_YEAR = TemporalAdjusters
            .firstDayOfNextYear();

    private static final TemporalAdjuster LAST_DAY_OF_MONTH = TemporalAdjusters
            .lastDayOfMonth();

    private static final TemporalAdjuster LAST_DAY_OF_YEAR = TemporalAdjusters
            .lastDayOfYear();
    //endregion

    //region public method

    /**
     * Return date according to system zone.
     *
     * @param localDate localDate
     * @return Date
     */
    private static Date simpleDate(LocalDate localDate) {
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(DEFAULT_ZONE);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * Return datetime according to system zone.
     *
     * @param localDateTime localDateTime
     * @return Date
     */
    private static Date simpleDateTime(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(DEFAULT_ZONE);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * Return LocalDate according to system zone.
     *
     * @param date date
     * @return LocalDate
     */
    private static LocalDate localDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = instant.atZone(DEFAULT_ZONE);
        return zonedDateTime.toLocalDate();
    }

    /**
     * this method return current date .
     *
     * @return Date
     */
    public static Date applicationServerDate() {
        LocalDate localDate = LocalDate.now();
        return simpleDate(localDate);
    }

    /**
     * this method return current date Time.
     *
     * @return Date
     */
    public static Date applicationServerDateTime() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return simpleDateTime(localDateTime);
    }

    /**
     * this method return day of Week from a given date . output i.e : THURSDAY
     *
     * @return String
     */
    public static String dayOfWeekFullLength(Date date) {
        LocalDate localDate = localDate(date);
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.name();
    }

    /**
     * this method return month of year from a given date . output i.e : JANUARY
     *
     * @param date date
     * @return String
     */
    public static String monthOfYearFullLength(Date date) {
        LocalDate localDate = localDate(date);
        Month month = localDate.getMonth();
        return month.name();
    }

    /**
     * this method returns no of weeks of month from a given date. output i.e: 4
     *
     * @param date date
     * @return int
     */
    public static int weekOfMonth(Date date) {
        LocalDate localDate = localDate(date);
        TemporalField temporalField = WeekFields.of(Locale.getDefault()).weekOfMonth();
        return localDate.get(temporalField);
    }

    /**
     * this method return month of year from a given date . output i.e : 9
     *
     * @param date date
     * @return int
     */
    public static int monthOfYear(Date date) {
        LocalDate localDate = localDate(date);
        Month month = localDate.getMonth();
        return month.getValue();
    }

    /**
     * this method return year from a given date . output i.e : 2014
     *
     * @param date date
     * @return String
     */
    public static String yearFullLength(Date date) {
        LocalDate localDate = localDate(date);
        Integer year = localDate.getYear();
        return year.toString();
    }

    /**
     * This method will return last two digits of year from a given date. i.e:
     * 14
     *
     * @param date date
     * @return String
     */
    public static String yearLength(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy");
        LocalDate localDate = localDate(date);
        return localDate.format(formatter);
    }

    /**
     * this method return month length from a given date . output i.e : 31,30,28
     *
     * @param date date
     * @return int
     */
    public static int noOfDaysOfMonth(Date date) {
        LocalDate localDate = localDate(date);
        DatePicker datePicker = new DatePicker();
        datePicker.setMonths(localDate.getMonthValue());
        Month localMonth = Month.of(datePicker.getMonths());
        return localMonth.minLength();
    }

    /**
     * this method gets No of days of this year in days. output i.e : 365, 366
     *
     * @param date date
     * @return int
     */
    public static int noOfDaysOfYear(Date date) {
        LocalDate localDate = localDate(date);
        DatePicker datePicker = new DatePicker();
        datePicker.setYears(localDate.getMonthValue());
        Year localYear = Year.of(datePicker.getYears());
        return localYear.length();
    }

    /**
     * this method convert string to date with given pattern i.e : input :
     * pattern -- dd-MM-yyyy date -- 20-01-2014 i.e : output : Mon Jan 20
     * 00:00:00 ALMT 2014
     *
     * @param date    date
     * @param pattern pattern
     * @return Date
     */
    public static Date toDate(String date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = LocalDate.parse(date, formatter);
        return simpleDate(localDate);
    }


    /**
     * this method convert string to date with given pattern
     *
     * @param datePicker datePicker
     * @return Date
     */
    protected static Date toDate(DatePicker datePicker) {
        int years = datePicker.getYears();
        int months = datePicker.getMonths();
        int days = datePicker.getDays();

        LocalDate localDate = LocalDate.of(years, months, days);
        return simpleDate(localDate);
    }

    /**
     * this method convert date to DatePicker
     *
     * @param date date
     * @return DatePicker
     */
    public static DatePicker toDatePicker(Date date) {
        LocalDate localDate = localDate(date);
        DatePicker datePicker = new DatePicker();
        datePicker.setYears(localDate.getYear());
        datePicker.setMonths(localDate.getMonthValue());
        datePicker.setDays(localDate.getDayOfMonth());
        return datePicker;
    }

    /**
     * this method check leap year for given date
     *
     * @param date date
     * @return boolean
     */
    public static boolean isLeapYear(Date date) {
        LocalDate localDate = localDate(date);
        return localDate.isLeapYear();
    }

    /**
     * this method check two date equality
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean isEqual(Date date1, Date date2) {
        LocalDate localDate1 = localDate(date1);
        LocalDate localDate2 = localDate(date2);
        return localDate1.isEqual(localDate2);
    }

    /**
     * this method check if this date is after the specified date. and date1
     * greater than date2 i.e: ob1:-16-Jan-2014 and ob2:- 15-Jan-2014 then
     * output : true
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean isAfter(Date date1, Date date2) {
        LocalDate localDate1 = localDate(date1);
        LocalDate localDate2 = localDate(date2);
        return localDate1.isAfter(localDate2);
    }

    /**
     * this method check if this date is before the specified date. and date1
     * less than date2 i.e: ob1:-15-Jan-2014 and ob2:- 16-Jan-2014 then output :
     * true
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean isBefore(Date date1, Date date2) {
        LocalDate localDate1 = localDate(date1);
        LocalDate localDate2 = localDate(date2);
        return localDate1.isBefore(localDate2);
    }

    /**
     * Returns a copy of this date with the specified number of days added. i.e:
     * 15-Jan-2014 plus 2 output : 17-Jan-2014
     *
     * @param date date
     * @param days days
     * @return Date
     */
    public static Date plusDays(Date date, long days) {
        LocalDate localDate = localDate(date);
        localDate = localDate.plusDays(days);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of days minus. i.e:
     * 15-Jan-2014 plus 2 output : 13-Jan-2014
     *
     * @param date date
     * @param days days
     * @return Date
     */
    public static Date minusDays(Date date, long days) {
        LocalDate localDate = localDate(date);
        localDate = localDate.minusDays(days);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of weeks added.
     *
     * @param date  date
     * @param weeks weeks
     * @return Date
     */
    public static Date plusWeeks(Date date, long weeks) {
        LocalDate localDate = localDate(date);
        localDate = localDate.plusWeeks(weeks);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of weeks minus.
     *
     * @param date  date
     * @param weeks weeks
     * @return Date
     */
    public static Date minusWeeks(Date date, long weeks) {
        LocalDate localDate = localDate(date);
        localDate = localDate.minusWeeks(weeks);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of months added.
     *
     * @param date   date
     * @param months months
     * @return Date
     */
    public static Date plusMonths(Date date, long months) {
        LocalDate localDate = localDate(date);
        localDate = localDate.plusMonths(months);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of months minus.
     *
     * @param date   date
     * @param months months
     * @return Date
     */
    public static Date minusMonths(Date date, long months) {
        LocalDate localDate = localDate(date);
        localDate = localDate.minusMonths(months);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of years add.
     *
     * @param date  date
     * @param years years
     * @return Date
     */
    public static Date plusYears(Date date, long years) {
        LocalDate localDate = localDate(date);
        localDate = localDate.plusYears(years);
        return simpleDate(localDate);
    }

    /**
     * Returns a copy of this date with the specified number of years minus.
     *
     * @param date  date
     * @param years years
     * @return Date
     */
    public static Date minusYears(Date date, long years) {
        LocalDate localDate = localDate(date);
        localDate = localDate.minusYears(years);
        return simpleDate(localDate);
    }

    /**
     * this method formats this date using the given formatter.
     *
     * @param date    date
     * @param pattern pattern
     * @return String
     */
    public static String format(Date date, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate localDate = localDate(date);
        return localDate.format(formatter);
    }

    /**
     * this method formats this date using the default formatter (dd-MMM-yyyy).
     *
     * @param date date
     * @return String
     */
    public static String format(Date date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DD_MMM_YYYY);
        LocalDate localDate = localDate(date);
        return localDate.format(formatter);
    }

    /**
     * this method returns first day of month from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date firstDayOfMonth(Date date) {
        LocalDate localDate = localDate(date);
        localDate = localDate.with(FIRST_DAY_OF_MONTH);
        return simpleDate(localDate);
    }

    /**
     * this method returns first day of quarter from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date firstDayOfQuarter(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int previousQuarter = (calendar.get(Calendar.MONTH) / 3);
        Date outputDate;
        switch (previousQuarter) {
            case 3:
                calendar.set(calendar.get(Calendar.YEAR), 9, 1);
                outputDate = calendar.getTime();
                return outputDate;
            case 2:
                calendar.set(calendar.get(Calendar.YEAR), 6, 1);
                outputDate = calendar.getTime();
                return outputDate;
            case 1:
                calendar.set(calendar.get(Calendar.YEAR), 3, 1);
                outputDate = calendar.getTime();
                return outputDate;
            default:
                calendar.set(calendar.get(Calendar.YEAR), 0, 1);
                outputDate = calendar.getTime();
                return outputDate;
        }
    }

    /**
     * this method returns first day of half-year from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date firstDayOfHalfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int previousQuarter = (calendar.get(Calendar.MONTH) / 6);
        Date outputDate;
        switch (previousQuarter) {
            case 1:
                calendar.set(calendar.get(Calendar.YEAR), 6, 1);
                outputDate = calendar.getTime();
                return outputDate;
            default:
                calendar.set(calendar.get(Calendar.YEAR), 0, 1);
                outputDate = calendar.getTime();
                return outputDate;
        }
    }

    /**
     * this method returns first day of year from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date firstDayOfYear(Date date) {
        LocalDate localDate = localDate(date);
        localDate = localDate.with(FIRST_DAY_OF_YEAR);
        return simpleDate(localDate);
    }

    /**
     * this method returns first day of next month from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date firstDayOfNextMonth(Date date) {
        LocalDate localDate = localDate(date);
        localDate = localDate.with(FIRST_DAY_OF_NEXT_MONTH);
        return simpleDate(localDate);
    }

    /**
     * this method returns first day of next year from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date firstDayOfNextYear(Date date) {
        LocalDate localDate = localDate(date);
        localDate = localDate.with(FIRST_DAY_OF_NEXT_YEAR);
        return simpleDate(localDate);
    }

    /**
     * this method returns last day of month from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date lastDayOfMonth(Date date) {
        LocalDate localDate = localDate(date);
        localDate = localDate.with(LAST_DAY_OF_MONTH);
        return simpleDate(localDate);
    }

    /**
     * this method returns last day of Year from a given date.
     *
     * @param date date
     * @return date
     */
    public static Date lastDayOfYear(Date date) {
        LocalDate localDate = localDate(date);
        localDate = localDate.with(LAST_DAY_OF_YEAR);
        return simpleDate(localDate);
    }

    /**
     * this method Calculates the amount of days between two date . Exp : Result
     * =300 days
     *
     * @param date1 date1
     * @param date2 date2
     * @return long
     */
    public static long dayDifference(Date date1, Date date2) {
        Temporal temporal1 = localDate(date1);
        Temporal temporal2 = localDate(date2);
        return ChronoUnit.DAYS.between(temporal1, temporal2);
    }

    /**
     * this method Calculates the amount of days between two date . Exp : Result
     * =5 yrs 2 months 6 days
     *
     * @param date1 date1
     * @param date2 date2
     * @return long
     */
    public static DatePicker dateDifference(Date date1, Date date2) {
        LocalDate localDate1 = localDate(date1);
        LocalDate localDate2 = localDate(date2);
        Period period = Period.between(localDate2, localDate1);

        DatePicker datePicker = new DatePicker();
        datePicker.setYears(period.getYears());
        datePicker.setMonths(period.getMonths());
        datePicker.setDays(period.getDays());
        return datePicker;
    }

    /**
     * @param date date
     * @return Date
     */
    public static Date toDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return simpleDate(localDate);
    }

    public static Model fromTODateModel(CurrentUser currentUser,Model model) {
        String fromDate;
        String toDate;
        Date currentFinancialToDate = DateUtil.toDate(currentUser.getFinancialYearTo());
        Date currentFinancialFromDate = DateUtil.toDate(currentUser.getFinancialYearFrom());
        if (new Date().after(currentFinancialFromDate) && new Date().before(currentFinancialToDate)) {
            fromDate = currentUser.getFinancialYearFrom();
            toDate = DateUtil.format(new Date(), DateUtil.DD_MMM_YYYY);
        } else {
            toDate = currentUser.getFinancialYearTo();
            fromDate = currentUser.getFinancialYearFrom();
        }
        model.addAttribute("toDate", toDate);
        model.addAttribute("fromDate", fromDate);
        return model;
    }

}
