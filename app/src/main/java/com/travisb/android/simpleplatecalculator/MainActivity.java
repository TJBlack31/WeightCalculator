package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.travisb.android.utils.FontUtil;
import com.travisb.android.utils.KeyboardUtil;
import com.travisb.android.utils.SharedPrefUtil;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText weightAmount;
    FragmentTransaction ft;
    boolean isDisplayed;
    WeightDisplayLbs newFragment;
    TextView weightLabel;
    Button changeAvailableWeights;
    Button calculateWeight;
    Switch isKgSwtch;
    boolean isKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);
        weightLabel = findViewById(R.id.weightLabel);
        changeAvailableWeights = findViewById(R.id.availableWeights);
        weightAmount = findViewById(R.id.weightAmount);
        calculateWeight = findViewById(R.id.calculateButton);
        isKgSwtch = findViewById(R.id.switchlbkg);
        isKgSwtch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isKg = true;
                    //todo: update shared pref also retrieve value from SharedPref before hand
                    weightLabel.setText("Enter weight in KGs");
                }else{
                    isKg = false;
                    weightLabel.setText("Enter weight in LBs");
                }
            }
        });
        checkFirstTime();


        FontUtil.setTextType(weightLabel, this);
        FontUtil.setTextType(changeAvailableWeights, this);
        FontUtil.setTextType(calculateWeight, this);
        FontUtil.setNoType(weightAmount, this);


        AppRater.app_launched(this);




        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                double last = 0;
                final double barWeight;
                if(isKg){
                    try {
                        last = SharedPrefUtil.retrieveLastKG(getBaseContext());
                    } catch (Exception e) {

                    }
                    barWeight = SharedPrefUtil.retrieveBarKG(getApplicationContext());
                }else{
                    try {
                        last = SharedPrefUtil.retrieveLastLB(getBaseContext());
                    } catch (Exception e) {

                    }
                    barWeight = SharedPrefUtil.retrieveBarLB(getApplicationContext());
                }
                weightAmount.setText(String.format(Locale.getDefault(), "%d", (int)last));
                calculate(barWeight, getAvailablePlates(isKg), getBaseContext());
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calculateWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KeyboardUtil.hideKeyboard(MainActivity.this);
                        calculate(barWeight, getAvailablePlates(isKg), getBaseContext());
                    }
                });
                weightAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(newFragment != null) {
                            getSupportFragmentManager().beginTransaction().
                                    remove(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
                            getSupportFragmentManager().popBackStack(null,
                                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            newFragment = null;
                        }else{
                        }
                    }
                });

                weightAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                                || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            calculate(barWeight, getAvailablePlates(isKg), getApplicationContext());
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

//    private void calculate(final double barWeight, final HashMap<String, Integer> availableWeights, final Context context){
//        System.out.println(availableWeights);
//        String weightAm = weightAmount.getText().toString();
//        if(weightAm == ""){
//            weightAm = "0";
//        }
//        final String weightAmnt = weightAm;
//        if(newFragment != null) {
//            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            newFragment = null;
//        }
//       final Runnable runnable = new Runnable() {
//           @Override
//           public void run() {
//
//               double weight = Integer.parseInt(weightAmnt);
//
//               System.out.println( weight + " YEAH" );
//
//               if(weight<barWeight){
//                   System.out.println( " YEAH19");
//
//                   Toast toast = Toast.makeText(context,
//                           "Entered weight must meet or exceed the weight of the bar!", Toast.LENGTH_LONG);
//                   toast.setGravity(Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 150);
//                   toast.show();
//                   return;
//               }
//               System.out.println( barWeight + " YEAH21");
//
//               if(weight >= barWeight) {
//                   System.out.println( " YEAH22");
//
//                   if(newFragment == null) {
//                       System.out.println( " YEAH23" + isKg);
//
//                       if(isKg){
//                               WeightCalculatorKG weightCalculator = new WeightCalculatorKG(barWeight, availableWeights);
//
//                               boolean isDisplayable = weightCalculator.configurePlates(weight, getApplicationContext());
//                               int barWeight = (int)weightCalculator.getBarWeight();
//
//
//                               if(hasSufficientPlts(weight, weightCalculator.getAvalableWeight()) && isDisplayable) {
//                                   newFragment = WeightDisplayKG.newInstance(weight, barWeight, weightCalculator.getPlatesKG25(), weightCalculator.getPlatesKG20(),
//                                           weightCalculator.getPlatesKG15(),
//                                           weightCalculator.getPlatesKG10(),
//                                           weightCalculator.getPlatesKG5(),
//                                           weightCalculator.getPlatesKG2pnt5(), weightCalculator.getPlatesKG1());
//                                   SharedPrefUtil.saveLastKG(weight, getApplicationContext());
//                                   presentFrag(newFragment);
//                               }
//                           }else{
//                               WeightCalculatorLB weightCalculator = new WeightCalculatorLB(barWeight, availableWeights);
//
//                               boolean isDisplayable = weightCalculator.configurePlates(weight, getApplicationContext());
//                               int barWeight = (int)weightCalculator.getBarWeight();
//                           System.out.println("yeah " + weightCalculator.getAvalableWeight());
//                               if(hasSufficientPlts(weight, weightCalculator.getAvalableWeight()) && isDisplayable) {
//                                   newFragment = WeightDisplayLbs.newInstance(weight, barWeight, weightCalculator.getPlates25(), weightCalculator.getPlates35(),
//                                           weightCalculator.getPlates25(),
//                                           weightCalculator.getPlates10(),
//                                           weightCalculator.getPlates5(),
//                                           weightCalculator.getPlates2pnt5());
//                                   SharedPrefUtil.saveLastLB(weight, getApplicationContext());
//                                   presentFrag(newFragment);
//                               }
//                           }
//
//                       }
//
//               }else{
//
//               }
//           }
//       };
//       new Thread(new Runnable() {
//           @Override
//           public void run() {
//               runOnUiThread(runnable);
//           }
//       }).start();
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//    }

    private void presentFrag(BaseFragment base){
        ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
        ft.replace(R.id.frag, base, "fragment");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ft.commit();
        isDisplayed = true;
    }

    private boolean hasSufficientPlts(double weight, double availableWeight)
    {
        System.out.println(weight + " Weight available: " + availableWeight);
        if(weight <= (int)availableWeight){
            return true;
        }else {

            Toast toast = Toast.makeText(this,
                    "Not enough plates! " + availableWeight, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 150);
            toast.show();
            return false;
        }
    }

    private void calculateLb(final double barWeight, final HashMap<String, Integer> availableWeights, final Context context){

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {


                WeightCalculatorLB weightCalculator = new WeightCalculatorLB(barWeight, availableWeights);
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
                            newFragment = WeightDisplayLbs.newInstance(weight,barWeight, weightCalculator.getPlates45(), weightCalculator.getPlates35(),
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
                            SharedPrefUtil.saveLastLB(weight, getApplicationContext());
                            weightCalculator=null;


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

    private void calculate(final double barWeight, final HashMap<String, Integer> availableWeights, final Context context){
        if(newFragment != null) {
            System.out.println("called from calculate");

            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

            newFragment = null;
        }
        if(isKg){
            calculateKg(barWeight, availableWeights, context);
        }else{
            calculateLb(barWeight, availableWeights, context);
        }
    }

    private void calculateKg(final double barWeight, final HashMap<String, Integer> availableWeights, final Context context){

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {


                WeightCalculatorKG weightCalculator = new WeightCalculatorKG(barWeight, availableWeights);
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
                            newFragment = WeightDisplayKG.newInstance(weight,barWeight, weightCalculator.getPlatesKG25(), weightCalculator.getPlatesKG20(),
                                    weightCalculator.getPlatesKG15(),
                                    weightCalculator.getPlatesKG10(),
                                    weightCalculator.getPlatesKG5(),
                                    weightCalculator.getPlatesKG2pnt5(),
                                    weightCalculator.getPlatesKG1());


                            ft = getSupportFragmentManager().beginTransaction();
                            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
                            ft.replace(R.id.frag, newFragment, "fragment");
                            ft.commit();
                            getSupportFragmentManager().executePendingTransactions();
                            isDisplayed = true;
                            SharedPrefUtil.saveLastLB(weight, getApplicationContext());
                            weightCalculator=null;


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
        EditWeightsDialogLB editWeightsDialog = new EditWeightsDialogLB();
        editWeightsDialog.show(fm, "fragment_edit_weights");
    }

    private void checkFirstTime(){
        if(SharedPrefUtil.isFirstTime(this)){
            if(isKg){
                for(String weight : WeightCalculatorKG.WEIGHTSKG){
                    SharedPrefUtil.saveAvailableKG(weight, 10, this);
                    SharedPrefUtil.saveBarKG(20, this);
                }
            }else{
                for(String weight : WeightCalculatorLB.WEIGHTSLB){
                    SharedPrefUtil.saveAvailableLB(weight, 10, this);
                    SharedPrefUtil.saveBarLB(45, this);
                }
            }
            SharedPrefUtil.saveFirstTime(this);
        }
    }


    private HashMap<String, Integer> getAvailablePlates(boolean isKg){
        if(isKg){
            HashMap<String, Integer> plates = new HashMap<>();
            int length = WeightCalculatorLB.WEIGHTSKG.length;
            for(int i = 0; i<length; i++){
                String key = WeightCalculatorLB.WEIGHTSKG[i];
                plates.put(key, (int)SharedPrefUtil.retrieveAvalableKG(key, this));
            }
            return plates;
        }else{
            HashMap<String, Integer> plates = new HashMap<>();
            int length = WeightCalculatorLB.WEIGHTSLB.length;
            for(int i = 0; i<length; i++){
                String key = WeightCalculatorLB.WEIGHTSLB[i];
                System.out.println("from get available plates index of" + i + " " + " length of " + (int)SharedPrefUtil.retrieveAvalableLB(key, this));
                plates.put(key, (int)SharedPrefUtil.retrieveAvalableLB(key, this));
            }
            return plates;
        }
    }
}