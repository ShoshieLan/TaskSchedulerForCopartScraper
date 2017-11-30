package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Created by slan on 11/30/2017.
 */
public class DateUtils {


        private static String dateFormat = "yyyy/MM/dd/HH:mm ss";

        // ---------------- Date Functions -----------------

        public static SimpleDateFormat getSimpleDateFormat() {
            return new SimpleDateFormat(dateFormat);
        }

        public static String getCurrDateTimeStr() {
            return getDateTimeStr(new Date());
        }

        public static Integer getCurrYear() {
            return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
        }

        public static String getDateTimeStr(Date date) {
            if (date == null) {
                return null;
            }
            return getSimpleDateFormat().format(date);
        }




    }
