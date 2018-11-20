package com.travisb.android.simpleplatecalculator.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Morticia on 3/24/18.
 */

public class FontUtil {

    public static void setTextType(final TextView textView, final Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/built titling sb.ttf");
        textView.setTypeface(type);
    }
    public static void setTextType(final Button switchTxt, final Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/built titling sb.ttf");
        switchTxt.setTypeface(type);
    }
    public static void setNoType(final EditText editTxt, final Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/built titling sb.ttf");
        editTxt.setTypeface(type);
    }

    public static void setNoType(TextView num, Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/built titling sb.ttf");
        num.setTypeface(type);
    }
}
