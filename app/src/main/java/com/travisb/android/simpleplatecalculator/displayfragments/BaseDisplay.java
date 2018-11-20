package com.travisb.android.simpleplatecalculator.displayfragments;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.travisb.android.simpleplatecalculator.DisplayUpdateCallback;
import com.travisb.android.simpleplatecalculator.R;
import com.travisb.android.simpleplatecalculator.utils.FontUtil;


public abstract class BaseDisplay extends Fragment implements DisplayUpdateCallback {

    protected static final String PARAMKG25 = "TWENTYFVEKG";
    protected static final String PARAMKG20 = "TWENTIEKG";
    protected static final String PARAMKG15 = "FIFTEENKG";
    protected static final String PARAMKG10= "TENKG";
    protected static final String PARAMKG5 = "FIVEKG";
    protected static final String PARAMKG2pnt5 = "TWPNTFVEKG";
    protected static final String PARAMKG2 = "TWOKG";
    protected static final String PARAMKG1pnt5 = "ONPNTFVEKG";
    protected static final String PARAMKG1 = "ONEKG";
    protected static final String PARAMKGpnt5 = "PNTFVEKG";

    protected static final String PARAM45 = "FRTYFVE";
    protected static final String PARAM35 = "THRTFVE";
    protected static final String PARAM25 = "TWNTFVE";
    protected static final String PARAM10= "TEN";
    protected static final String PARAM5 = "FIVE";
    protected static final String PARAM2pnt5 = "TWPNTFVE";

    protected TextView displayHeader;
    protected String header;

    protected AdView mAdView;
    protected ImageView[] imageViews;



    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        Animation animation = super.onCreateAnimation(transit, enter, nextAnim);

        // HW layer support only exists on API 11+
        if (Build.VERSION.SDK_INT >= 11) {
            if (animation == null && nextAnim != 0) {
                animation = AnimationUtils.loadAnimation(getActivity(), nextAnim);
            }

            if (animation != null) {
                getView().setLayerType(View.LAYER_TYPE_HARDWARE, null);

                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(getView() != null) {
                            getView().setLayerType(View.LAYER_TYPE_NONE, null);
                        }
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }

                    // ...other AnimationListener methods go here...
                });
            }
        }

        return animation;
    }

    protected void setViews(TextView[]textViews, LinearLayout[]linearLayouts,
                            int[] quantityPlates, ImageView[]imageViews, int[]drawableResources) {
        for (int i = 0; i < quantityPlates.length; i++) {
            if (quantityPlates[i] == 0) {
                linearLayouts[i].removeAllViews();
                linearLayouts[i].setVisibility(View.GONE);
            } else {
                Drawable drawable = getResources().getDrawable(drawableResources[i]);
                imageViews[i].setAdjustViewBounds(true);
                imageViews[i].setMaxHeight(20);
                imageViews[i].setMaxWidth(20);
                imageViews[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageViews[i].setImageDrawable(drawable);
                textViews[i].setText(Integer.toString(quantityPlates[i]));

            }
        }
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        this.imageViews = null;
        this.mAdView = null;
        this.displayHeader = null;
    }

    protected void changeNoType(TextView[] array){
        for(int i = 0; i<array.length; i++){
            FontUtil.setTextType(array[i],this.getActivity());
        }
    }

    public void setDisplayHeader(boolean isBarWeight){
        if(isBarWeight) {

            displayHeader.setText("This is your bar weight.");
        }else{
            displayHeader.setText("Load these up on each side of the bar");
        }
    }

    protected void setUpAd(final View view, final boolean isKg){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout adViewLl;
                if(isKg){
                    adViewLl = view.findViewById(R.id.adviewLayoutKg);
                    mAdView = (AdView) view.findViewById(R.id.adViewKg);
                    loadAd(adViewLl);
                }else{
                    adViewLl = view.findViewById(R.id.adviewLayout);
                    mAdView = (AdView) view.findViewById(R.id.adView);
                    loadAd(adViewLl);
                }


            }
        }, 1000);
    }
    protected void loadAd(final LinearLayout adViewLl){
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("A5FD742E6319B9C82675AF7306A75D24").build();
        adViewLl.setMinimumHeight(adViewLl.getHeight());
        adViewLl.setMinimumWidth(adViewLl.getWidth());
        mAdView.loadAd(adRequest);
    }

    protected void checkIfBarWghtMssge(final boolean isKg){
        int bar=0;
        int weight=0;

        if(isKg){
            bar = getArguments().getInt("BARKG");
            weight = getArguments().getInt("WEIGHTKG");
        }else{
            bar = getArguments().getInt("BARLB");
            weight = getArguments().getInt("WEIGHTLB");
        }
        if(bar==weight){
            setDisplayHeader(true);
        }else{
            setDisplayHeader(false);
        }
    }



}
