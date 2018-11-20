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


public class WeightDisplayKG extends BaseDisplay {

    public WeightDisplayKG() {

    }

    public static WeightDisplayKG newInstance(final double weight, final double barWeight, final WeightCalculator weightCalculator) {
        WeightDisplayKG fragment = new WeightDisplayKG();
        System.gc();
        Bundle args = new Bundle();
        args.putInt("WEIGHTKG", (int) weight);
        args.putInt("BARKG", (int)barWeight);
        args.putInt(PARAMKG25, (int)weightCalculator.getPlatesKG25());
        args.putInt(PARAMKG20, (int)weightCalculator.getPlatesKG20());
        args.putInt(PARAMKG15, (int)weightCalculator.getPlatesKG15());
        args.putInt(PARAMKG10, (int)weightCalculator.getPlatesKG10());
        args.putInt(PARAMKG5, (int)weightCalculator.getPlatesKG5());
        args.putInt(PARAMKG2pnt5, (int)weightCalculator.getPlatesKG2pnt5());
        args.putInt(PARAMKG2, (int)weightCalculator.getPlatesKG2());
        args.putInt(PARAMKG1pnt5, (int)weightCalculator.getPlatesKG1pnt5());
        args.putInt(PARAMKG1, (int)weightCalculator.getPlatesKG1());
        args.putInt(PARAMKGpnt5, (int)weightCalculator.getPlatesKGpnt5());
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

        View view = inflater.inflate(R.layout.fragment_weight_display_kg, container, false);
        if (getArguments() != null) {
            int[] quantityPlates = new int[]{getArguments().getInt(PARAMKG25),
                    getArguments().getInt(PARAMKG20),
                    getArguments().getInt(PARAMKG15),
                    getArguments().getInt(PARAMKG10),
                    getArguments().getInt(PARAMKG5),
                    getArguments().getInt(PARAMKG2pnt5),
                    getArguments().getInt(PARAMKG2),
                    getArguments().getInt(PARAMKG1pnt5),
                    getArguments().getInt(PARAMKG1),
                    getArguments().getInt(PARAMKGpnt5)};

            TextView[] textViews = new TextView[]{view.findViewById(R.id.tv25kg),
                    view.findViewById(R.id.tv20kg),
                    view.findViewById(R.id.tv15kg),
                    view.findViewById(R.id.tv10kg),
                    view.findViewById(R.id.tv5kg),
                    view.findViewById(R.id.tv2_5kg),
                    view.findViewById(R.id.tv2kg),
                    view.findViewById(R.id.tv1pnt5kg),
                    view.findViewById(R.id.tv1kg),
                    view.findViewById(R.id.tvpnt5kg)};

            LinearLayout[] linearLayouts = new LinearLayout[]{view.findViewById(R.id.linearLayout25kg),
                    view.findViewById(R.id.linearLayout20kg),
                    view.findViewById(R.id.linearLayout15kg),
                    view.findViewById(R.id.linearLayout10kg),
                    view.findViewById(R.id.linearLayout5kg),
                    view.findViewById(R.id.linearLayout2pnt5kg),
                    view.findViewById(R.id.linearLayout2kg),
                    view.findViewById(R.id.linearLayout1pnt5kg),
                    view.findViewById(R.id.linearLayout1kg),
                    view.findViewById(R.id.linearLayoutpnt5kg)};

            this.imageViews = new ImageView[]{view.findViewById(R.id.imageView25kg),
                    view.findViewById(R.id.imageView20kg),
                    view.findViewById(R.id.imageView15kg),
                    view.findViewById(R.id.imageView10kg),
                    view.findViewById(R.id.imageView5kg),
                    view.findViewById(R.id.imageView2pnt5kg),
                    view.findViewById(R.id.imageView2kg),
                    view.findViewById(R.id.imageView1pnt5kg),
                    view.findViewById(R.id.imageView1kg),
                    view.findViewById(R.id.imageViewpnt5kg)};

            int[] drawableResources = new int[]{R.drawable.plateskg25,
                    R.drawable.plateskg20,
                    R.drawable.plateskg15,
                    R.drawable.plateskg10,
                    R.drawable.plateskg5,
                    R.drawable.plateskg2pnt5,
                    R.drawable.plateskg2,
                    R.drawable.plateskg1pnt5,
                    R.drawable.plateskg1,
                    R.drawable.plateskgpnt5};

            this.displayHeader = view.findViewById(R.id.displayHeaderKG);
            FontUtil.setTextType(displayHeader, getContext());

            setUpAd(view, true);
            setViews(textViews, linearLayouts, quantityPlates, this.imageViews, drawableResources);
            changeNoType(textViews);

           checkIfBarWghtMssge(true);
        }
        return view;
    }

    @Override
    public void onDisplayUpdate(String update, boolean isDisplayable) {
        this.header = update;
        this.displayHeader.setText(update);
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }
}
