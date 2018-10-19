package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


import java.io.ByteArrayOutputStream;


public class WeightDisplayLbs extends Fragment {

    private static final String PARAM45 = "FRTYFVE";
    private static final String PARAM35 = "THRTFVE";
    private static final String PARAM25 = "TWNTFVE";
    private static final String PARAM10= "TEN";
    private static final String PARAM5 = "FIVE";
    private static final String PARAM2pnt5 = "TWPNTFVE";

    private TextView displayHeader;
    private String header;

    private AdView mAdView;
    private ImageView[] imageViews;

    public WeightDisplayLbs() {

    }

    public static WeightDisplayLbs newInstance(int weight, int barWeight, int fortyFives, int thirtyFives, int twentyFives,
                                               int tens,
                                               int fives,
                                               int twoPntFives ) {
        WeightDisplayLbs fragment = new WeightDisplayLbs();
        System.gc();
        Bundle args = new Bundle();
        args.putInt("WEIGHT", weight);
        args.putInt("BAR", barWeight);
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




            final Handler handler = new Handler();
            final View mView = view = view;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LinearLayout adViewLl = mView.findViewById(R.id.adviewLayout);

                    mAdView = (AdView) mView.findViewById(R.id.adView);
                    AdRequest adRequest = new AdRequest.Builder().build();

                    //added these lines because for some reason on my device, fragment would
                    //go blank when calling .loadAd() .  This was because
                    // the views were being interupted by ad load. Solved it by insulating adView in its own layout.
                    //the two methods below take a snapshot of the height and width of the wrapped adView
                    //and then lock the layout to that size.
                    adViewLl.setMinimumHeight(adViewLl.getHeight());
                    adViewLl.setMinimumWidth(adViewLl.getWidth());
                    mAdView.loadAd(adRequest);


                }
            }, 1000);





            setViews(textViews, linearLayouts, quantityPlates, imageViews, drawableResources, this.mAdView);

            changeNoType(textViews);
//            final View mView = view;
//            Runnable runnable = new Runnable() {
//
//
//                @Override
//                public void run() {
//
//                }
//            };
//            getActivity().runOnUiThread(runnable);



            displayHeader = view.findViewById(R.id.displayHeader);
            FontUtil.setTextType(displayHeader, getContext());
            if(getArguments().getInt("BAR") == getArguments().getInt("WEIGHT")){
                setDisplayHeader(true);
            }else{
                setDisplayHeader(false);
            }
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

    public void editTouched(){
        mAdView.removeAllViews();
    }

    public void setDisplayHeader(boolean isBarWeight){
        if(isBarWeight) {
            displayHeader.setText("This is your bar weight.");
        }else{
            displayHeader.setText("Load these up on each side of the bar");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();


    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        for(int i = 0; i<this.imageViews.length; i++){
            this.imageViews[i] = null;
        }
    }
    private void setViews(TextView[]textViews, LinearLayout[]linearLayouts,
                          int[] quantityPlates, ImageView[]imageViews, int[]drawableResources, AdView mAdView){
        for(int i = 0; i<quantityPlates.length; i++){
           if(quantityPlates[i]==0){
               linearLayouts[i].removeAllViews();
               linearLayouts[i].setVisibility(View.GONE);
           }else{
               Drawable drawable = getResources().getDrawable(drawableResources[i]);
               imageViews[i].setAdjustViewBounds(true);
               imageViews[i].setMaxHeight(30);
               imageViews[i].setMaxWidth(30);
               imageViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
               imageViews[i].setImageDrawable(drawable);
               textViews[i].setText(Integer.toString(quantityPlates[i]));
//               resizeImage(imageViews[i]);
           }
        }


    }

    private void resizeImage(ImageView imageView){
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte[] BYTE;
        Drawable image = imageView.getDrawable();
        Bitmap bitmap1 = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmap2;

        bitmap1.compress(Bitmap.CompressFormat.JPEG,40,bytearrayoutputstream);

        BYTE = bytearrayoutputstream.toByteArray();

        bitmap2 = BitmapFactory.decodeByteArray(BYTE,0,BYTE.length);
        imageView.setBackgroundColor(Color.TRANSPARENT);

        imageView.setImageBitmap(bitmap2);
    }
}
