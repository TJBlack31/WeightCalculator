package com.travisb.android.simpleplatecalculator.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Morticia on 3/26/18.
 */

public class SharedPrefUtil {

    public static void saveAvailableLB(String key, double plateQnt, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt(key, (int) plateQnt); //3
        System.out.println("length from shared pref save lbs " +plateQnt);
        editor.commit(); //4
    }

    public static void saveAvailableKG(String key, double plateQnt, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt(key, (int)plateQnt); //3
        editor.commit(); //4
    }


    public static double retrieveAvalableKG(String key, Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt(key, 10);
        return available;
    }

    public static double retrieveAvalableLB(String key, Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
//        System.out.println()
        available = settings.getInt(key, 10);
        System.out.println("length from shared pref retrieve lbs " +available );
        return available;
    }

    public static boolean isFirstTime(Context context, boolean isKG){
        boolean isFirstTime;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        if(isKG){
            isFirstTime = settings.getBoolean("FIRST_TIMELB", true);

        }else{
            isFirstTime = settings.getBoolean("FIRST_TIMEKG", true);
        }
        if(isFirstTime){
            saveFirstTime(context, isKG);
        }


        return isFirstTime;
    }



    public static void saveFirstTime(Context context, boolean isKg){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();
        if(isKg){
            editor.putBoolean("FIRST_TIMEKG", false);
        }else{
            editor.putBoolean("FIRST_TIMELB", false);
        }

        editor.commit();
    }

    public static void saveBarLB(double barWeight, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt("BAR", (int)barWeight); //3
        editor.commit(); //4
    }

    public static void saveLastLB(double lastWeight, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt("LAST", (int)lastWeight); //3
        editor.commit(); //4
    }

    public static double retrieveLastLB(Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt("LAST",retrieveBarLB(context).intValue());
        return (double)available;
    }

    public static Double retrieveBarLB(Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt("BAR", 45);
        return (double)available;
    }







    public static boolean isKg(Context context){
        boolean isKG;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        isKG = settings.getBoolean("ISKG", false);
        return isKG;
    }

    public static void setKg(Context context, final boolean isKg){

        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor;
        editor = settings.edit();
        editor.putBoolean("ISKG", isKg);
        editor.commit();

    }


    public static void saveBarKG(Integer barWeight, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putLong("BARKG", barWeight); //3
        editor.commit(); //4
    }

    public static void saveLastKG(Double lastWeight, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt("LASTKG", lastWeight.intValue()); //3
        editor.commit(); //4
    }

    public static double retrieveLastKG(Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt("LASTKG", retrieveBarKG(context).intValue());
        return (double)available;
    }

    public static Double retrieveBarKG(Context context){
//        int available = 0;
//        SharedPreferences settings;
//        settings = PreferenceManager.getDefaultSharedPreferences(context);
//        available = settings.getInt("BARKG", 20);
//        return available;

        long available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getLong("BARKG", 20);
        return (double)available;
    }

}
