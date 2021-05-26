package org.extvos.common.utils;

import java.util.Date;

/**
 * @author Mingcai SHEN
 */
public class Calendar {
    /**
     * 获得当天是周几
     */
    public static String getWeekDay() {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }
}
