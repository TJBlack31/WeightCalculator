package com.morticia.android.applicationproj;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;


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
            HashMap<String, WeightStack> weightStackHashMap;

            weightStackHashMap = new HashMap<>();

            weightStackHashMap.put("fortyFives", new WeightStack(getArguments().getInt(PARAM45),
                    (TextView)view.findViewById(R.id.tv45lb), (LinearLayout)view.findViewById(R.id.linearLayout45lb),
                    (ImageView) view.findViewById(R.id.imageView45), "fortyfive", R.drawable.plates45x1));
            weightStackHashMap.put("thirtyFives", new WeightStack(getArguments().getInt(PARAM35),
                    (TextView)view.findViewById(R.id.tv35lb), (LinearLayout)view.findViewById(R.id.linearLayout35lb),
                    (ImageView) view.findViewById(R.id.imageView35), "thirtyfive", R.drawable.plates35x1));
            weightStackHashMap.put("twentyFives", new WeightStack(getArguments().getInt(PARAM25),
                    (TextView)view.findViewById(R.id.tv25lb), (LinearLayout)view.findViewById(R.id.linearLayout25lb),
                    (ImageView) view.findViewById(R.id.imageView25), "twentyfive", R.drawable.plates25x1));
            weightStackHashMap.put("tens", new WeightStack(getArguments().getInt(PARAM10),
                    (TextView)view.findViewById(R.id.tv10lb), (LinearLayout)view.findViewById(R.id.linearLayout10lb),
                    (ImageView) view.findViewById(R.id.imageView10), "ten", R.drawable.plates10x1));
            weightStackHashMap.put("fives", new WeightStack(getArguments().getInt(PARAM5),
                    (TextView)view.findViewById(R.id.tv5lb), (LinearLayout)view.findViewById(R.id.linearLayout5lb),
                    (ImageView) view.findViewById(R.id.imageView5), "five", R.drawable.plates5x1));
            weightStackHashMap.put("twoPntFives", new WeightStack(getArguments().getInt(PARAM2pnt5),
                    (TextView)view.findViewById(R.id.tv2_5lb), (LinearLayout)view.findViewById(R.id.linearLayout2pnt5lb),
                    (ImageView) view.findViewById(R.id.imageView2pnt5), "twoPntFive", R.drawable.plates2pnt5x1));

            changeNoType(weightStackHashMap);
        }

        return view;
    }

    private void changeNoType(HashMap<String, WeightStack> hash){
        for(WeightStack weightStack : hash.values()){
            weightStack.setTypeFace(this.getContext());
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

}
