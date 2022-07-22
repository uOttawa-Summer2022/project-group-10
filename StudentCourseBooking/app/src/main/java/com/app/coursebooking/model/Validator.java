package com.app.coursebooking.model;

public class Validator {
    public static int getDaysPosition(String days) {
        int position = 0;
        String[] daysString = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < daysString.length; i++) {
            if (daysString[i].equalsIgnoreCase(days)) {
                position = i;
            }
        }
        return position;
    }

    public static boolean isValidDays(String days) {

        String[] daysString = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < daysString.length; i++) {
            if (daysString[i].equalsIgnoreCase(days)) {
                return true;
            }
        }
        return false;
    }


    public static int toTime(String timeStr) {
        String[] spliced = timeStr.split(":");
        int hour = Integer.parseInt(spliced[0]);
        int min = Integer.parseInt(spliced[1]);
        return hour * 60 + min;
    }

    public static boolean isConflict(String time1, String time2) {
        String[] spliced1 = time1.split("-");
        String[] spliced2 = time2.split("-");

        Integer start1 = toTime(spliced1[0]);
        Integer end1 = toTime(spliced1[1]);
        Integer start2 = toTime(spliced2[0]);
        Integer end2 = toTime(spliced2[1]);

        if (start1.compareTo(start2) >= 0 && start1.compareTo(end2) < 0) {
            return true;
        }

        if (end1.compareTo(start2) > 0 && end1.compareTo(end2) <= 0) {
            return true;
        }

        if (start2.compareTo(start1) >= 0 && start2.compareTo(end1) < 0) {
            return true;
        }

        return end2.compareTo(start1) > 0 && end2.compareTo(end1) <= 0;
    }

    public static boolean isValidTime(String time) {
        String[] spliced = time.split(":");
        if (spliced.length != 2) {
            return false;
        }

        try {
            int hour = Integer.parseInt(spliced[0]);
            int min = Integer.parseInt(spliced[1]);

            return hour >= 0 && hour <= 23 && min >= 0 && min <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
