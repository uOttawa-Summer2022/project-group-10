package com.app.coursebooking.model;

import android.util.Log;

public class Validator {
    public static int getDaysPosition(String days){
        int position = 0;
        String[] daysString = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for(int i=0;i<daysString.length;i++){
            if(daysString[i].equalsIgnoreCase(days)){
                position = i;
            }
        }
        return position;
    }

    public static boolean isConflict(String time1, String time2){
        String[] spliced1 = time1.split("-");
        String[] spliced2 = time2.split("-");

        String start1 = spliced1[0];
        String end1 = spliced1[1];
        String start2 = spliced2[0];
        String end2 = spliced2[1];

        Log.e("conf-check", start1 + " " + end1 + " " + start2 + " " + end2);

        if(start1.compareTo(start2) >= 0 && start1.compareTo(end2) < 0){
            return true;
        }

        if(end1.compareTo(start2) > 0 && end1.compareTo(end2) <= 0){
            return true;
        }

        if(start2.compareTo(start1) >= 0 && start2.compareTo(end1) < 0){
            return true;
        }

        if(end2.compareTo(start1) > 0 && end2.compareTo(end1) <= 0){
            return true;
        }

        return false;
    }

    public static boolean isValidTime(String time){
        String[] spliced = time.split(":");
        if(spliced.length != 2){
            return false;
        }

        try {
            int hour = Integer.parseInt(spliced[0]);
            int min = Integer.parseInt(spliced[1]);

            if(hour >= 0 && hour <= 23 && min >= 0 && min <= 59){
                return true;
            }else{
                return false;
            }
        }catch (NumberFormatException e){
            return  false;
        }
    }
}
