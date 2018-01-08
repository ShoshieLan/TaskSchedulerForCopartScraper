package com.company;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by slan on 11/30/2017.
 */
public class DateUtils {


    private static String dateFormat = "MM/dd/yy hh:mm:ss a";

    // ---------------- Date Functions -----------------

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat(dateFormat);
    }


    public static String getDateTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        return getSimpleDateFormat().format(date);
    }


    public static String getCurrentDateMinus60Min() {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT-5"));
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(60)));
        return getDateTimeStr(cal.getTime());
    }

}
