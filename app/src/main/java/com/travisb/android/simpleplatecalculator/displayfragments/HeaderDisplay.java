package com.travisb.android.simpleplatecalculator.displayfragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travisb.android.simpleplatecalculator.R;
import com.travisb.android.simpleplatecalculator.utils.FontUtil;

@SuppressLint("ValidFragment")
public class HeaderDisplay extends BaseDisplay {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weight_display_lb, container, false);


        TextView[] textViews = new TextView[]{view.findViewById(R.id.tv45lb),
                view.findViewById(R.id.tv35lb),
                view.findViewById(R.id.tv25lb),
                view.findViewById(R.id.tv10lb),
                view.findViewById(R.id.tv5lb),
                view.findViewById(R.id.tv2_5lb)};
        LinearLayout[] linearLayouts = new LinearLayout[]{view.findViewById(R.id.linearLayout45lb),
                view.findViewById(R.id.linearLayout35lb),
                view.findViewById(R.id.linearLayout25lb),
                view.findViewById(R.id.linearLayout10lb),
                view.findViewById(R.id.linearLayout5lb),
                view.findViewById(R.id.linearLayout2pnt5lb)};

        this.displayHeader = view.findViewById(R.id.displayHeaderLB);

        for(int i = 0; i<linearLayouts.length; i++){

            textViews[i].setVisibility(View.GONE);
            linearLayouts[i].setVisibility(View.GONE);
        }

        textViews = null;
        linearLayouts=null;


        FontUtil.setTextType(displayHeader, getActivity());
        setUpAd(view, false);
        this.displayHeader.setText(this.header);
        return view;
    }




    @SuppressLint("ValidFragment")
    public HeaderDisplay(String headerTxt){
        this.header = headerTxt;

    }
    @Override
    public void onDisplayUpdate(String updatedText, boolean isDisplayable) {
        this.displayHeader.setText(updatedText);
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }
}
