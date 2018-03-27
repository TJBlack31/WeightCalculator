package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by Morticia on 3/24/18.
 */

public class FontUtil {

    public static void setTextType(TextView textView, Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/built titling sb.ttf");
        textView.setTypeface(type);
    }
    public static void setTextType(Button switchTxt, Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/built titling sb.ttf");
        switchTxt.setTypeface(type);
    }
    public static void setNoType(EditText editTxt, Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/39smooth.ttf");
        editTxt.setTypeface(type);
    }

    public static void setNoType(TextView num, Context context){
        Typeface type = Typeface.createFromAsset(context.getAssets(),"fonts/39smooth.ttf");
        num.setTypeface(type);
    }
}
