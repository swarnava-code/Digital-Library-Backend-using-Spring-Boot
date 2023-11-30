package com.sclab.library.util;

import java.sql.Date;
import java.time.temporal.ChronoUnit;

public class TimeUtil {

    public static Date currentDate() {
        return plusDay(0);
    }

    /**
     * @param yearAmount: 1
     * @return java.util.Date
     */
    public static Date addYearInDate(int yearAmount) {
        return plusYear(yearAmount);
    }

    public static Date addDayInDate(int dayAmount) {
        return plusDay(dayAmount);
    }

    private static Date plusYear(int yearAmount) {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().plusDays(yearAmount));
    }

    public static Date plusDay(int dayAmount) {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().plusDays(dayAmount));
    }

    public static Date plusDay(Date date, int dayAmount) {
        return Date.valueOf(date.toLocalDate().plusDays(dayAmount));
    }

    public static Date minusDay(int dayAmount) {
        Date date = new Date(System.currentTimeMillis());
        return Date.valueOf(date.toLocalDate().minusDays(dayAmount));
    }

    public static Date minusDay(Date date, int dayAmount) {
        return Date.valueOf(date.toLocalDate().minusDays(dayAmount));
    }

    public static long differenceInDays(Date date1, Date date2) {
        long days = ChronoUnit.DAYS.between(date1.toLocalDate(), date2.toLocalDate());
        return days;
    }
}

//        Calendar calendar = Calendar.getInstance();
//        calendar.add(calendarUnit, yearAmount);
//        java.sql.Date sqlDate = new Date(calendar.getTimeInMillis());
//        return sqlDate;