package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;



import java.util.ArrayList;


public class WeightDisplay extends Fragment {

    private static final String PARAM45 = "FRTYFVE";
    private static final String PARAM35 = "THRTFVE";
    private static final String PARAM25 = "TWNTFVE";
    private static final String PARAM10= "TEN";
    private static final String PARAM5 = "FIVE";
    private static final String PARAM2pnt5 = "TWPNTFVE";

    public WeightDisplay() {

    }

    public static WeightDisplay newInstance(int fortyFives, int thirtyFives, int twentyFives,
                                            int tens,
                                            int fives,
                                            int twoPntFives ) {
        WeightDisplay fragment = new WeightDisplay();
        Bundle args = new Bundle();
        args.putInt(PARAM45, fortyFives);
        args.putInt(PARAM35, thirtyFives);
        args.putInt(PARAM25, twentyFives);
        args.putInt(PARAM10, tens);
        args.putInt(PARAM5, fives);
        args.putInt(PARAM2pnt5, twoPntFives);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weight_display, container, false);

        if (getArguments() != null) {

            int[] quantityPlates = new int[]{getArguments().getInt(PARAM45),
                    getArguments().getInt(PARAM35),
                    getArguments().getInt(PARAM25),
                    getArguments().getInt(PARAM10),
                    getArguments().getInt(PARAM5),
                    getArguments().getInt(PARAM2pnt5)};

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

            setViews(textViews, linearLayouts, quantityPlates);

            changeNoType(textViews);

            TextView displayHeader = view.findViewById(R.id.displayHeader);
            FontUtil.setTextType(displayHeader, getContext());
        }

        return view;
    }

    private void changeNoType(TextView[] array){
        for(int i = 0; i<array.length; i++){
            FontUtil.setTextType(array[i],this.getContext());
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();


    }
    private void setViews(TextView[]textViews, LinearLayout[]linearLayouts,
                          int[] quantityPlates){
        for(int i = 0; i<quantityPlates.length; i++){
           if(quantityPlates[i]==0){
               linearLayouts[i].removeAllViews();
               linearLayouts[i].setVisibility(View.GONE);
           }else{
               textViews[i].setText(Integer.toString(quantityPlates[i]));
           }
        }
    }
}
