package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Morticia on 3/26/18.
 */

public class SharedPrefUtil {

    public static void saveAvailable(String key, Integer plateQnt, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt(key, plateQnt); //3
        editor.commit(); //4
    }

    public static int retrieveAvalable(String key, Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt(key, 0);
        return available;
    }

    public static boolean isFirstTime(Context context){
        boolean isFirstTime;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        isFirstTime = settings.getBoolean("FIRST_TIME", true);
        return isFirstTime;
    }

    public static void saveFirstTime(Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();
        editor.putBoolean("FIRST_TIME", false);
        editor.commit();
    }

    public static void saveBar(Integer barWeight, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt("BAR", barWeight); //3
        editor.commit(); //4
    }

    public static void saveLast(Integer lastWeight, Context context){
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = PreferenceManager.getDefaultSharedPreferences(context); //1
        editor = settings.edit(); //2
        editor.putInt("LAST", lastWeight); //3
        editor.commit(); //4
    }

    public static int retrieveLast(Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt("LAST", retrieveBar(context));
        return available;
    }

    public static int retrieveBar(Context context){
        int available = 0;
        SharedPreferences settings;
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        available = settings.getInt("BAR", 45);
        return available;
    }

}
