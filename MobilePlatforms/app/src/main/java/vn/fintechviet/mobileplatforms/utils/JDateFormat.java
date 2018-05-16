/*
 *  Copyright (C) 2018 - 2019 FINTECHVIET
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package vn.fintechviet.mobileplatforms.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by longtran on 14/02/2017.
 */

public class JDateFormat {
    /*****
     *
     * @param dateString
     * @param expectedPattern
     * @return
     */
    public static String previous7DaysString(String dateString, String expectedPattern) {
        String result = "";
        try {
            // Create a date formatter using your format string
            DateFormat dateFormat = new SimpleDateFormat(expectedPattern);
            // Parse the given date string into a Date object.
            // Note: This can throw a ParseException.
            Date myDate = dateFormat.parse(dateString);
            // Use the Calendar class to subtract one day
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, -7);
            // Use the date formatter to produce a formatted date string
            Date previousDate = calendar.getTime();
            result = dateFormat.format(previousDate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /*****
     *
     * @param dateString
     * @param expectedPattern
     * @return
     */
    public static String next7DaysString(String dateString, String expectedPattern) {
        String result = "";
        try {
            // Create a date formatter using your format string
            DateFormat dateFormat = new SimpleDateFormat(expectedPattern);
            // Parse the given date string into a Date object.
            // Note: This can throw a ParseException.
            Date myDate = dateFormat.parse(dateString);
            // Use the Calendar class to subtract one day
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            // Use the date formatter to produce a formatted date string
            Date previousDate = calendar.getTime();
            result = dateFormat.format(previousDate);
        } catch (Exception exception) {
            result = dateString;
            exception.printStackTrace();
        }
        return result;
    }

    /*****
     *
     * @param dateString
     * @param expectedPattern
     * @return
     */
    public static String previousDateString(String dateString, String expectedPattern) {
        String result = "";
        try {
            // Create a date formatter using your format string
            DateFormat dateFormat = new SimpleDateFormat(expectedPattern);
            // Parse the given date string into a Date object.
            // Note: This can throw a ParseException.
            Date myDate = dateFormat.parse(dateString);
            // Use the Calendar class to subtract one day
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            // Use the date formatter to produce a formatted date string
            Date previousDate = calendar.getTime();
            result = dateFormat.format(previousDate);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /*****
     *
     * @param dateString
     * @param expectedPattern
     * @return
     */
    public static String nextDateString(String dateString, String expectedPattern) {
        String result = "";
        try {
            // Create a date formatter using your format string
            DateFormat dateFormat = new SimpleDateFormat(expectedPattern);
            // Parse the given date string into a Date object.
            // Note: This can throw a ParseException.
            Date myDate = dateFormat.parse(dateString);
            // Use the Calendar class to subtract one day
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(myDate);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            // Use the date formatter to produce a formatted date string
            Date previousDate = calendar.getTime();
            result = dateFormat.format(previousDate);
        } catch (Exception exception) {
            result = dateString;
            exception.printStackTrace();
        }
        return result;
    }

    /*****
     *
     * @param dateString
     * @param expectedPattern
     * @return
     */
    public static String dateString(String dateString, String expectedPattern) {
        String result = "";
        try {
            // Create a date formatter using your format string
            DateFormat dateFormat = new SimpleDateFormat(expectedPattern);
            // Parse the given date string into a Date object.
            // Note: This can throw a ParseException.
            Date myDate = dateFormat.parse(dateString);
            result = dateFormat.format(myDate);
        } catch (Exception e) {
            result = dateString;
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        return result;
    }

    /***
     *
     * @param dateString
     * @param expectedPattern
     * @return
     */
    public static String formatDate(String dateString, String expectedPattern) {
        Date date;
        String formattedDate = dateString;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat(expectedPattern, Locale.getDefault()).format(date);
        } catch (Exception e) {
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }

        return formattedDate;
    }

    /****
     *
     * @param dateString
     * @return
     */
    public static Date fromString(String dateString) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.d(JDateFormat.class.getName(), e.toString());
            date = new Date(System.currentTimeMillis());
        }

        return date;
    }

    /***
     *
     * @param dateString
     * @param pattern
     * @param expectedPattern
     * @return
     */
    public static String formatDate(String dateString, String pattern, String expectedPattern) {
        Date date;
        String formattedDate = "";
        try {
            date = new SimpleDateFormat(pattern, Locale.getDefault()).parse(dateString);
            formattedDate = new SimpleDateFormat(expectedPattern, Locale.getDefault()).format(date);
        } catch (Exception e) {
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }

        return formattedDate;
    }

    /****
     *
     * @return
     */
    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("EEE, MMM d, yyyy").format(new Date());
    }

    /****
     *
     * @return
     */
    public static String getCurrentTimeStamp(String pattern) {
        return new SimpleDateFormat(pattern).format(new Date());
    }

    /*****
     *
     * @param timeString
     * @return
     */
    public static String timeString(String timeString, String pattern, String expectedPattern) {
        String result = timeString;
        try {
            // Declare a date format for parsing
            SimpleDateFormat dateParser = new SimpleDateFormat(pattern);
            // Parse the time string
            Date date = dateParser.parse(timeString);
            // Declare a date format for printing
            SimpleDateFormat dateFormater = new SimpleDateFormat(expectedPattern);
            // Print the previously parsed time
            result = dateFormater.format(date);
        } catch (Exception e) {
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        return result;
    }

    /*****
     *
     * @param timeString
     * @return
     */
    public static String timeString(String timeString) {
        String expectedPattern = "hh:mm a";
        String result = timeString;
        try {
            // Declare a date format for parsing
            SimpleDateFormat dateParser = new SimpleDateFormat("HH:mm:ss");
            // Parse the time string
            Date date = dateParser.parse(timeString);
            // Declare a date format for printing
            SimpleDateFormat dateFormater = new SimpleDateFormat(expectedPattern);
            // Print the previously parsed time
            result = dateFormater.format(date);
        } catch (Exception e) {
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        return result;
    }

    /*****
     *
     * @param timeString
     * @return
     */
    public static String setForced12hFormat(String timeString) {
        String expectedPattern = "hh:mm a";
        String result = timeString;
        try {
            // Declare a date format for parsing
            SimpleDateFormat dateParser = new SimpleDateFormat("HH:mm:ss");
            // Parse the time string
            Date date = dateParser.parse(timeString);
            // Declare a date format for printing
            SimpleDateFormat dateFormater = new SimpleDateFormat(expectedPattern);
            // Print the previously parsed time
            result = dateFormater.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        return result;
    }

    /*****
     *
     * @param timeString
     * @return
     */
    public static String setForced24hFormat(String timeString) {
        String expectedPattern = "HH:mm:ss";
        String result = timeString;
        try {
            // Declare a date format for parsing
            SimpleDateFormat dateParser = new SimpleDateFormat("hh:mm a");
            // Parse the time string
            Date date = dateParser.parse(timeString);
            // Declare a date format for printing
            SimpleDateFormat dateFormater = new SimpleDateFormat(expectedPattern);
            // Print the previously parsed time
            result = dateFormater.format(date);
        } catch (Exception e) {
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        return result;
    }

    /****
     *
     * @param timeString
     * @return
     */
    public static int[] getHoursMinutesSeconds(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf.parse(timeString);
            calendar.setTime(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            calendar.setTime(new Date(System.currentTimeMillis()));
        }
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return new int[]{hour, minute, second};
    }

    /***
     *
     * @param timeString
     * @param pattern
     * @return
     */
    public static int[] getYearMonthDay(String timeString, String pattern) {
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
            date = formatter.parseLocalDate(timeString);
            int year = date.getYear();
            int month = date.getMonthOfYear();
            int day = date.getDayOfMonth();
            return new int[]{year, month, day};
        } catch (Exception e) {
            date = LocalDate.now();
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        int year = date.getYear();
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();
        return new int[]{year, month, day};
    }

    /***
     *
     * @param timeString
     * @param pattern
     * @return
     */
    public static String findNameOfDay(String timeString, String pattern) {
        LocalDate date;
        try {
            DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
            date = formatter.parseLocalDate(timeString);
            String dayOfWeek = date.dayOfWeek().getAsText(java.util.Locale.ENGLISH);
            return dayOfWeek;
        } catch (Exception e) {
            date = LocalDate.now();
            LogHelper.d(JDateFormat.class.getName(), e.toString());
        }
        return "timeString";
    }
}
