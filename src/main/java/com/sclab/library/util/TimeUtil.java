package com.sclab.library.util;

import java.sql.Date;
import java.util.Calendar;

public class TimeUtil {

    public static java.sql.Date currentDate() {
        return getDate(0);
    }

    /**
     * @param yearAmount:  1
     * @return java.util.Date
     */
    public static java.sql.Date addYearInDate(int yearAmount) {
        java.util.Date javaDate = getDate(yearAmount);
        java.sql.Date sqlDate = new Date(javaDate.getTime());
        return sqlDate;
    }

    public static java.sql.Date getDate(int yearAmount) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, yearAmount);
        java.sql.Date sqlDate = new Date(calendar.getTimeInMillis());
        return sqlDate;
    }

}