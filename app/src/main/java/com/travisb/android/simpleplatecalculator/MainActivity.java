package com.travisb.android.simpleplatecalculator;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.morticia.android.applicationproj.R;

import java.util.HashMap;

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
        setContentView(R.layout.activity_main);
        weightLabel = findViewById(R.id.weightLabel);
        changeAvailableWeights = findViewById(R.id.availableWeights);
        weightAmount = findViewById(R.id.weightAmount);
        calculateWeight = findViewById(R.id.calculateButton);

        FontUtil.setTextType(weightLabel, this);
        FontUtil.setTextType(changeAvailableWeights, this);
        FontUtil.setTextType(calculateWeight, this);
        FontUtil.setNoType(weightAmount, this);

        checkFirstTime();

        calculateWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculate();
            }
        });

        weightAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    System.out.println("done clicked");
                    calculate();
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

    private void calculate(){
        if(newFragment != null) {
            FragmentManager fm  = getSupportFragmentManager();
            fm.popBackStack();
        }
        if(weightAmount.getText().toString().length() > 0) {

            FrameLayout layout = findViewById(R.id.frag);
            layout.removeAllViewsInLayout();
            WeightCalculator weightCalculator = new WeightCalculator(45, getAvailablePlates());

            boolean isDisplayable = weightCalculator.configurePlates(Integer.parseInt(weightAmount.getText().toString()), this);
            if(isDisplayable) {
                newFragment = WeightDisplay.newInstance(weightCalculator.getPlates45(), weightCalculator.getPlates35(),
                        weightCalculator.getPlates25(),
                        weightCalculator.getPlates10(),
                        weightCalculator.getPlates5(),
                        weightCalculator.getPlates2pnt5());

                ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
                ft.replace(R.id.frag, newFragment, "fragment");
                ft.commit();
                isDisplayed = true;
            }
        }
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
