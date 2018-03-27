package com.morticia.android.applicationproj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Morticia on 3/22/18.
 */

public class WeightStack {

    int quantity = 0;
    TextView displayAmnt;
    LinearLayout linearLayout;
    ImageView weightImage;
    public String weight;

    int imageResource;

    public WeightStack(int quantity, TextView textView, LinearLayout linearLayout, ImageView img, String weight, int imageResource){
        this.quantity = quantity;
        this.displayAmnt = textView;
        this.linearLayout = linearLayout;
        this.weightImage = img;
        this.weight = weight;
        this.imageResource = imageResource;

        checkPlates();
    }

    private void checkPlates(){
        if(quantity==0){
            this.linearLayout.removeAllViews();
            this.linearLayout.setVisibility(View.GONE);
        }else{
            this.displayAmnt.setText(Integer.toString(this.quantity));
        }
        optimizePhoto();
    }

    private void optimizePhoto(){
        if(weight=="fortyfive"){
            if(quantity==2){
                weightImage.setImageResource(R.drawable.plates45x2);
            }
            if(quantity==3){
                weightImage.setImageResource(R.drawable.plates45x3);
            }
            if(quantity==4){
                weightImage.setImageResource(R.drawable.plates45x4);
            }
            if(quantity==5){
                weightImage.setImageResource(R.drawable.plates45x5);
            }
            if(quantity==6){
                weightImage.setImageResource(R.drawable.plates45x6);
            }
            if(quantity>=7){
                weightImage.setImageResource(R.drawable.plates45x7);
            }
        }
        if(weight=="ten" && this.quantity == 2){
            weightImage.setImageResource(R.drawable.plates10x2);
        }
        else if(quantity == 1){
            weightImage.setImageResource(imageResource);
        }
    }

    public void setTypeFace(Context context){
        FontUtil.setNoType(displayAmnt, context);
    }


}
