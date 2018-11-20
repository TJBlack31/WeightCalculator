package com.travisb.android.simpleplatecalculator.displayfragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travisb.android.simpleplatecalculator.R;
import com.travisb.android.simpleplatecalculator.calculator.WeightCalculator;
import com.travisb.android.simpleplatecalculator.utils.FontUtil;

public class WeightDisplayLbs extends com.travisb.android.simpleplatecalculator.displayfragments.BaseDisplay {

    public WeightDisplayLbs() {

    }

    public static WeightDisplayLbs newInstance(double weight, double barWeight, WeightCalculator weightCalculator ) {
        WeightDisplayLbs fragment = new WeightDisplayLbs();
        System.gc();
        Bundle args = new Bundle();
        args.putInt("WEIGHTLB", (int)weight);
        args.putInt("BARLB", (int)barWeight);
        args.putInt(PARAM45, (int)weightCalculator.getPlates45());
        args.putInt(PARAM35, (int)weightCalculator.getPlates35());
        args.putInt(PARAM25, (int)weightCalculator.getPlates25());
        args.putInt(PARAM10, (int)weightCalculator.getPlates10());
        args.putInt(PARAM5, (int)weightCalculator.getPlates5());
        args.putInt(PARAM2pnt5, (int)weightCalculator.getPlates2pnt5());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weight_display_lb, container, false);

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

            this.imageViews = new ImageView[]{view.findViewById(R.id.imageView45),
                    view.findViewById(R.id.imageView35),
                    view.findViewById(R.id.imageView25),
                    view.findViewById(R.id.imageView10),
                    view.findViewById(R.id.imageView5),
                    view.findViewById(R.id.imageView2pnt5)};

            int[] drawableResources = new int[]{R.drawable.plates45x1,
                    R.drawable.plates35x1,
                    R.drawable.plates25x1,
                    R.drawable.plates10x1,
                    R.drawable.plates5x1,
                    R.drawable.plates2pnt5x1};

            this.displayHeader = view.findViewById(R.id.displayHeaderLB);

            setUpAd(view, false);
            setViews(textViews, linearLayouts, quantityPlates, this.imageViews, drawableResources);
            changeNoType(textViews);
            checkIfBarWghtMssge(false);
            FontUtil.setTextType(this.displayHeader, getActivity());
        }

        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDisplayUpdate(String update, boolean isDisplayable) {
        header = update;
        displayHeader.setText(update);
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }
}
