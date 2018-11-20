package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.travisb.android.simpleplatecalculator.displayfragments.BaseDisplay;
import com.travisb.android.simpleplatecalculator.displayfragments.HeaderDisplay;
import com.travisb.android.simpleplatecalculator.displayfragments.WeightDisplayKG;
import com.travisb.android.simpleplatecalculator.displayfragments.WeightDisplayLbs;
import com.travisb.android.simpleplatecalculator.editdialogfragments.EditWeightsDialogKG;
import com.travisb.android.simpleplatecalculator.editdialogfragments.EditWeightsDialogLB;
import com.travisb.android.simpleplatecalculator.calculator.WeightCalculator;
import com.travisb.android.simpleplatecalculator.calculator.WeightCalculatorKG;
import com.travisb.android.simpleplatecalculator.calculator.WeightCalculatorLB;
import com.travisb.android.simpleplatecalculator.utils.FontUtil;
import com.travisb.android.simpleplatecalculator.utils.KeyboardUtil;
import com.travisb.android.simpleplatecalculator.utils.SharedPrefUtil;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity  {

    EditText weightAmount;
    FragmentTransaction ft;
    boolean isDisplayed;
    BaseDisplay newFragment;
    TextView weightLabel;
    Button changeAvailableWeights;
    Button calculateWeight;
    boolean isKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//


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
                double last = 0;
                final double barWeight;
                isKg = SharedPrefUtil.isKg(getBaseContext());
                checkFirstTime(isKg);
                if (isKg) {
                    try {
                        setUpKgs();
                        last = SharedPrefUtil.retrieveLastKG(getBaseContext());
                    } catch (Exception e) {

                    }
                    barWeight = SharedPrefUtil.retrieveBarKG(getApplicationContext());
                } else {
                    try {
                        setUpLbs();
                        last = SharedPrefUtil.retrieveLastLB(getBaseContext());

                    } catch (Exception e) {

                    }
                    barWeight = SharedPrefUtil.retrieveBarLB(getApplicationContext());
                }
                weightAmount.setText(String.format(Locale.getDefault(), "%d", (int) last));

                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                calculateWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        KeyboardUtil.hideKeyboard(MainActivity.this);
                        calculate(getAvailablePlates(isKg), getBaseContext());
                    }
                });
                weightAmount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeFrag();
                    }
                });

                weightAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                                || (actionId == EditorInfo.IME_ACTION_DONE)) {
                            calculate(getAvailablePlates(isKg), getApplicationContext());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.metric_menu, menu);
        if (SharedPrefUtil.isKg(this)) {
            menu.getItem(0).setIcon(R.mipmap.lbicon);
        } else {
            menu.getItem(0).setIcon(R.mipmap.kgicon);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean isKg = SharedPrefUtil.isKg(this);
        checkFirstTime(isKg);
        switch (item.getItemId()) {
            case R.id.weightTypeItem:
                if (isKg) {
                    removeFrag();
                    item.setIcon(R.mipmap.kgicon);
                    item.setTitle("Kilograms");
//                    item.setIcon(M)
                    setUpLbs();
                    presentFrag(getString(R.string.lbsSelected));
                } else {
                    removeFrag();
                    item.setIcon(R.mipmap.lbicon);
                    item.setTitle("Pounds");
                    setUpKgs();
                    presentFrag(getString(R.string.kgsSelected));
                }
                break;

        }
        return true;
    }

    private void setUpLbs() {
        isKg = false;
        SharedPrefUtil.setKg(this, false);
        weightLabel.setText(getString(R.string.enterWeightLbs));
    }

    private void setUpKgs() {
        SharedPrefUtil.setKg(this, true);
        isKg = true;
        weightLabel.setText(getString(R.string.enterWeightKgs));
    }


    //todo: what's up with no ads
    @Override
    public void onPause() {
        super.onPause();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void removeFrag() {
        if (newFragment != null) {
            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
            getSupportFragmentManager().popBackStack(null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE);
            newFragment = null;
        }
    }

    private void presentFrag(WeightCalculator weightCalculator, double weight, double barWeight) {

        if (newFragment == null) {
            if (!isKg) {
                newFragment = WeightDisplayLbs.newInstance(weight, barWeight,
                        weightCalculator);
            } else {
                newFragment = WeightDisplayKG.newInstance(weight, barWeight, weightCalculator);
            }

            ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
            ft.replace(R.id.frag, newFragment, "fragment");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            isDisplayed = true;
            SharedPrefUtil.saveLastLB(weight, getApplicationContext());


        }
    }

    private void presentFrag(String headerText) {

        if (newFragment == null) {
            newFragment = new HeaderDisplay(headerText);

            ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
            ft.replace(R.id.frag, newFragment, "fragment");
            ft.commit();
            getSupportFragmentManager().executePendingTransactions();
            isDisplayed = true;
        }
    }

    private void calculateLb(final double barWeight, final HashMap<String, Integer> availableWeights) {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {

                WeightCalculatorLB weightCalculator = new WeightCalculatorLB(barWeight, availableWeights, new DisplayUpdateCallback() {
                    @Override
                    public void onDisplayUpdate(String updatedText, boolean isDisplayable) {
                        newFragment.onDisplayUpdate(updatedText, true);
                    }

                    @Override
                    public Context getActivityContext() {
                        return getActivityContext();
                    }
                });
                int weight = 0;
                int barWeight = (int) weightCalculator.getBarWeight();

                try {
                    weight = Integer.parseInt(weightAmount.getText().toString());
                } catch (NumberFormatException ex) { // handle your exception

                }
                if (weight < barWeight) {
                    presentFrag(getString(R.string.exceedBar));
                    return;
                }

                if (weight >= barWeight && weight
                        <= weightCalculator.getAvalableWeight()) {

                    weightCalculator.toPlates(weight, new DisplayUpdateCallback() {
                        @Override
                        public void onDisplayUpdate(String updatedText, boolean isDisplayable) {
                            if(!isDisplayable){
                                presentFrag(updatedText);
                            }
                        }

                        @Override
                        public Context getActivityContext() {
                            return getActivityContext();
                        }
                    });

                    presentFrag(weightCalculator, weight, barWeight);
                } else {
                    presentFrag(getString(R.string.notEnoughPlts));

                    return;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(runnable);
            }
        }).start();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


    }

    private void calculate(final HashMap<String, Integer> availableWeights, final Context context) {
        if (newFragment != null) {
            removeFrag();
        }
        Double barWeight;
        if (isKg) {

            barWeight = SharedPrefUtil.retrieveBarKG(getApplicationContext());
            calculateKg(barWeight, availableWeights, context);
        } else {

            barWeight = SharedPrefUtil.retrieveBarLB(getApplicationContext());
            calculateLb(barWeight, availableWeights);
        }
    }

    //todo: right when app starts icon is set wrong, but once clicked it switches directly
    private void calculateKg(final double barWeight, final HashMap<String, Integer> availableWeights, final Context context) {

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {


                WeightCalculatorKG weightCalculator = new WeightCalculatorKG(barWeight, availableWeights, new DisplayUpdateCallback() {
                    @Override
                    public void onDisplayUpdate(String updatedText, boolean isDisplayable) {
                        newFragment.onDisplayUpdate(updatedText, true);
                    }

                    @Override
                    public Context getActivityContext() {
                        return getActivityContext();
                    }
                });
                int weight = 0;
                int barWeight = (int) weightCalculator.getBarWeight();

                try {
                    weight = Integer.parseInt(weightAmount.getText().toString());
                } catch (NumberFormatException ex) { // handle your exception

                }
                if (weight < barWeight) {
                    presentFrag(getString(R.string.exceedBar));

                }
                if (weight >= barWeight && weight
                        <= weightCalculator.getAvalableWeight()) {
                    weightCalculator.toPlates(weight, new DisplayUpdateCallback() {
                        @Override
                        public void onDisplayUpdate(String updatedText, boolean isDisplayable) {
                            if(!isDisplayable) {
                                presentFrag(updatedText);
                                return;
                            }
                        }

                        @Override
                        public Context getActivityContext() {
                            return getActivityContext();
                        }
                    });

                    presentFrag(weightCalculator, weight, barWeight);

                } else {
                    presentFrag(getString(R.string.notEnoughPlts));
                    return;
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(runnable);
            }
        }).start();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void showEditDialog() {
        if (isKg) {
            FragmentManager fm = getSupportFragmentManager();
            EditWeightsDialogKG editWeightsDialog = new EditWeightsDialogKG();
            editWeightsDialog.show(fm, "fragment_edit_weights_kg");
        } else {
            FragmentManager fm = getSupportFragmentManager();
            EditWeightsDialogLB editWeightsDialog = new EditWeightsDialogLB();
            editWeightsDialog.show(fm, "fragment_edit_weights");
        }
    }

    private void checkFirstTime(boolean isKg) {
        if (SharedPrefUtil.isFirstTime(this, isKg)) {
            SharedPrefUtil.setKg(this, isKg);

            if (isKg) {

                for (String weight : WeightCalculatorKG.WEIGHTSKG) {
                    SharedPrefUtil.saveAvailableKG(weight, 10, this);
                    SharedPrefUtil.saveBarKG(20, this);
                    weightAmount.setText(Integer.toString(20));
                }
            } else {
                for (String weight : WeightCalculatorLB.WEIGHTSLB) {
                    System.out.println("length from main");
                    SharedPrefUtil.saveAvailableLB(weight, 10, this);
                    SharedPrefUtil.saveBarLB(45, this);
                    weightAmount.setText(Integer.toString(20));
                }
            }

        }
    }


    private HashMap<String, Integer> getAvailablePlates(boolean isKg) {
        if (isKg) {
            HashMap<String, Integer> plates = new HashMap<>();
            int length = WeightCalculatorLB.WEIGHTSKG.length;
            for (int i = 0; i < length; i++) {
                String key = WeightCalculatorLB.WEIGHTSKG[i];
                plates.put(key, (int) SharedPrefUtil.retrieveAvalableKG(key, this)/2);
            }
            return plates;
        } else {
            HashMap<String, Integer> plates = new HashMap<>();
            int length = WeightCalculatorLB.WEIGHTSLB.length;
            for (int i = 0; i < length; i++) {
                String key = WeightCalculatorLB.WEIGHTSLB[i];
                System.out.println("from get available plates index of" + i + " " + " length of " );
                plates.put(key, (int) SharedPrefUtil.retrieveAvalableLB(key, this)/2);
            }
            return plates;
        }
    }
}