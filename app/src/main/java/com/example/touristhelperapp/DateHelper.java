package com.example.touristhelperapp;

import java.util.Calendar;
import java.util.Date;

import java.text.SimpleDateFormat;

public class DateHelper {

    /**
     * This will take a start date and end date within in the same day
     * and it will return a user friendly start time and end time. e.g
     * 2:30 PM - 3:00 PM
     *
     * @param start The start date
     * @param end The end date
     * @return User-friendly start time and end time.
     */
    static public String startTimeToEndTime(Date start, Date end) {

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        String startTime = timeFormat.format(start);
        String endTime = timeFormat.format(end);

        return startTime + " - " + endTime;
    }

    /**
     * This will accept a full date and return with the month and the day
     * with a suffix e.g January 1st
     * @param date The date to be formatted
     * @return User-friendly date
     */
    public static String formatDateWithSuffix(Date date) {
        SimpleDateFormat monthDayFormat = new SimpleDateFormat("MMMM d");
        return monthDayFormat.format(date) + getDaySuffix(date);
    }

    private static String getDaySuffix(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day >= 11 && day <= 13) return "th";
        switch (day % 10) {
            case 1: return "st";
            case 2: return "nd";
            case 3: return "rd";
            default: return "th";
        }
    }


}
