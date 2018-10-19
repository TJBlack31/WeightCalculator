package com.travisb.android.simpleplatecalculator;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText weightAmount;
    FragmentTransaction ft;
    boolean isDisplayed;
    WeightDisplay newFragment;
    TextView weightLabel;
    Button changeAvailableWeights;
    Button calculateWeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkFirstTime();
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        weightLabel = findViewById(R.id.weightLabel);
        changeAvailableWeights = findViewById(R.id.availableWeights);
        weightAmount = findViewById(R.id.weightAmount);
        calculateWeight = findViewById(R.id.calculateButton);



        FontUtil.setTextType(weightLabel, this);
        FontUtil.setTextType(changeAvailableWeights, this);
        FontUtil.setTextType(calculateWeight, this);
        FontUtil.setNoType(weightAmount, this);


        AppRater.app_launched(this);




        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int last = 0;
                try {
                    last = SharedPrefUtil.retrieveLast(getBaseContext());
                } catch (Exception e) {

                }
                weightAmount.setText(String.format(Locale.getDefault(), "%d", last));



                calculate(SharedPrefUtil.retrieveBar(getApplicationContext()), getAvailablePlates(), getBaseContext());

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                calculateWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KeyboardUtil.hideKeyboard(MainActivity.this);

                        calculate(SharedPrefUtil.retrieveBar(getApplicationContext()), getAvailablePlates(), getBaseContext());
                    }
                });

                weightAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(newFragment != null) {
                            getSupportFragmentManager().beginTransaction().
                                    remove(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
                            newFragment = null;

                        }else{
                        }
                    }
                });

                weightAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {

                            calculate(SharedPrefUtil.retrieveBar(getApplicationContext()), getAvailablePlates(), getApplicationContext());


                        }
                        return false;
                    }
                });



                changeAvailableWeights.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showEditDialog();
                    }
                });

            }
        };

        Thread thread = new Thread(runnable);
        thread.start();




    }



    @Override
    public void onPause(){
        super.onPause();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    @Override
    public void onStart(){
        super.onStart();

    }

    @Override
    public void onResume(){
        super.onResume();

    }


    //todo make keyboard not show up when onResume
    //todo fix Toast x, y


    private void calculate(final double barWeight, final HashMap<String, Integer> availableWeights, final Context context){

        if(newFragment != null) {

            getSupportFragmentManager().popBackStack();

            newFragment = null;
        }
       final Runnable runnable = new Runnable() {
           @Override
           public void run() {


               WeightCalculator weightCalculator = new WeightCalculator(barWeight, availableWeights);
               int weight = 0;
               int barWeight = (int)weightCalculator.getBarWeight();

               try{
                   weight = Integer.parseInt(weightAmount.getText().toString());
               }catch(NumberFormatException ex){ // handle your exception

               }
               if(weight<barWeight){
                   Toast toast = Toast.makeText(context,
                           "Entered weight must meet or exceed the weight of the bar!", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 150);
                   toast.show();
                   return;
               }

               if(weight >= barWeight && weight
                       <= weightCalculator.getAvalableWeight()) {

                   boolean isDisplayable = weightCalculator.configurePlates(weight, getApplicationContext());
                   if(isDisplayable) {
                       if(newFragment == null) {
                           newFragment = WeightDisplay.newInstance(weight,barWeight, weightCalculator.getPlates45(), weightCalculator.getPlates35(),
                                   weightCalculator.getPlates25(),
                                   weightCalculator.getPlates10(),
                                   weightCalculator.getPlates5(),
                                   weightCalculator.getPlates2pnt5());


                           ft = getSupportFragmentManager().beginTransaction();
                           ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
                           ft.replace(R.id.frag, newFragment, "fragment");
                           ft.commit();
                           getSupportFragmentManager().executePendingTransactions();
                           isDisplayed = true;
                           SharedPrefUtil.saveLast(weight, getApplicationContext());


                       }
                   }
               }else{

                   Toast toast = Toast.makeText(context,
                           "Not enough plates!", Toast.LENGTH_LONG);
                   toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 150);
                   toast.show();
                   return;
               }
           }
       };
       runOnUiThread(runnable);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditWeightsDialog editWeightsDialog = new EditWeightsDialog();
        editWeightsDialog.show(fm, "fragment_edit_weights");
    }

    private void checkFirstTime(){
        if(SharedPrefUtil.isFirstTime(this)){
            for(String weight : WeightCalculator.WEIGHTS){
                SharedPrefUtil.saveAvailable(weight, 10, this);
                SharedPrefUtil.saveBar(45, this);
            }
            SharedPrefUtil.saveFirstTime(this);

        }
    }

    private HashMap<String, Integer> getAvailablePlates(){
        HashMap<String, Integer> plates = new HashMap<>();
        int length = WeightCalculator.WEIGHTS.length;
        for(int i = 0; i<length; i++){
            String key = WeightCalculator.WEIGHTS[i];
            plates.put(key, SharedPrefUtil.retrieveAvalable(key, this)/2);
        }
        return plates;
    }
}