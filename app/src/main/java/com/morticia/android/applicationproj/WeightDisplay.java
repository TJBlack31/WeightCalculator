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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WeightDisplay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WeightDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeightDisplay extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String PARAM45 = "FRTYFVE";
    private static final String PARAM35 = "THRTFVE";
    private static final String PARAM25 = "TWNTFVE";
    private static final String PARAM10= "TEN";
    private static final String PARAM5 = "FIVE";
    private static final String PARAM2pnt5 = "TWPNTFVE";

    // TODO: Rename and change types of parameters
//    private WeightStack fortyFives;
//    private WeightStack twentyFives;
//    private WeightStack thirtyFives;
//    private WeightStack tens;
//    private WeightStack fives;
//    private WeightStack twoPntFives;



    TextView fortyfiveTV ;
    TextView thirtyfiveTV;
    TextView twentyfiveTV;
    TextView tenTV;
    TextView fiveTV ;
    TextView twopntfiveTV;

    LinearLayout fortyfiveLL;
    LinearLayout thirtyfiveLL;
    LinearLayout twentyfiveLL;
    LinearLayout tenLL;
    LinearLayout fiveLL ;
    LinearLayout twopntfiveLL;

    public WeightDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeightDisplay.
     */
    // TODO: Rename and change types and number of parameters
//    public static WeightDisplay newInstance(int fortyFives, int twentyFives, int thirtyFives,
//             int tens,
//             int fives,
//             int twoPntFives ) {
//        WeightDisplay fragment = new WeightDisplay();
//        Bundle args = new Bundle();
//        args.putInt(PARAM45, fortyFives);
//        args.putInt(PARAM35, thirtyFives);
//        args.putInt(PARAM25, twentyFives);
//        args.putInt(PARAM10, tens);
//        args.putInt(PARAM5, fives);
//        args.putInt(PARAM2pnt5, twoPntFives);
//        fragment.setArguments(args);
//        return fragment;
//    }

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
        // Inflate the layout for this fragment

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

        }

//
//        fortyfiveTV = view.findViewById(R.id.tv45lb);
//        thirtyfiveTV = view.findViewById(R.id.tv35lb);
//        twentyfiveTV = view.findViewById(R.id.tv25lb);
//        tenTV = view.findViewById(R.id.tv10lb);
//        fiveTV = view.findViewById(R.id.tv5lb);
//        twopntfiveTV = view.findViewById(R.id.tv2_5lb);
//
//        fortyfiveLL = view.findViewById(R.id.linearLayout45lb);
//        thirtyfiveLL = view.findViewById(R.id.linearLayout35lb);
//        twentyfiveLL = view.findViewById(R.id.linearLayout25lb);
//        tenLL = view.findViewById(R.id.linearLayout10lb);
//        fiveLL = view.findViewById(R.id.linearLayout5lb);
//        twopntfiveLL = view.findViewById(R.id.linearLayout2pnt5lb);

//
//        fortyfiveTV.setText(Integer.toString(fortyFives));
//        thirtyfiveTV.setText(Integer.toString(thirtyFives));
//        twentyfiveTV.setText(Integer.toString(twentyFives));
//        tenTV.setText(Integer.toString(tens));
//        fiveTV.setText(Integer.toString(fives));
//        twopntfiveTV.setText(Integer.toString(twoPntFives));




        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    @Override
    public void onDetach() {
        super.onDetach();


    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
