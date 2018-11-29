//package com.travisb.android.simpleplatecalculator.utils;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.support.v7.widget.CardView;
//
//import com.travisb.android.simpleplatecalculator.MainActivity;
//import com.travisb.android.simpleplatecalculator.OnThemeChangeCallback;
//import com.travisb.android.simpleplatecalculator.R;
//
//
//public class ThemeUtils {
//
//    private static int sTheme;
//
//    static int accent;
//    public final static int THEME_DEFAULT = 0;
//    public final static int THEME_ROSE = 1;
//    public final static int THEME_FONZ = 2;
//    /**
//     * Set the theme of the Activity, and restart it by creating a new Activity of the same type.
//     */
//    public static void changeToTheme(Activity activity, int theme)
//    {
//        sTheme = theme;
//        activity.finish();
//        activity.startActivity(new Intent(activity, activity.getClass()));
//    }
//    /** Set the theme of the activity, according to the configuration. */
//    public static void onActivityCreateSetTheme(Activity activity, OnThemeChangeCallback callback)
//    {
//        switch (sTheme)
//        {
//            default:
//            case THEME_DEFAULT:
//                activity.setTheme(R.style.AppTheme);
//                accent = R.color.colorAccent;
//                SharedPrefUtil.saveTheme("DEFAULT", activity);
//                break;
//            case THEME_ROSE:
//                activity.setTheme(R.style.Rose);
//                accent=R.color.colorAccent_Rose;
//                SharedPrefUtil.saveTheme("ROSE", activity);
//                break;
//            case THEME_FONZ:
//                activity.setTheme(R.style.Fonz);
//                accent=R.color.colorAccent_Fonz;
//                SharedPrefUtil.saveTheme("FONZ", activity);
//                break;
//        }
//    }
//
//    public static void setTheme( Context context) {
//        String theme = SharedPrefUtil.retrieveTheme(context);
//        if(theme.equals("DEFAULT")){
//          context.setTheme(R.style.AppTheme);
//        }else if(theme.equals("ROSE")){
//            context.setTheme(R.style.Rose);
//        }else if(theme.equals("FONZ")){
//            context.setTheme(R.style.Fonz);
//        }
//    }
//
//    public static void setTheme( String theme, Context context) {
//
//        if(theme.equals("DEFAULT")){
//            context.setTheme(R.style.AppTheme);
//        }else if(theme.equals("ROSE")){
//            context.setTheme(R.style.Rose);
//        }else if(theme.equals("FONZ")){
//            context.setTheme(R.style.Fonz);
//        }
//    }
//
//
//}
//
