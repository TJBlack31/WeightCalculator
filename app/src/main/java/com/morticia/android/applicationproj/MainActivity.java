package com.morticia.android.applicationproj;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText weightAmount;

    FragmentTransaction ft;
    boolean isDisplayed;
    WeightDisplay newFragment;
    TextView weightLabel;
    Button changeAvailableWeights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weightLabel = findViewById(R.id.weightLabel);
        changeAvailableWeights = findViewById(R.id.availableWeights);
        weightAmount = findViewById(R.id.weightAmount);

        FontUtil.setTextType(weightLabel, this);
        FontUtil.setTextType(changeAvailableWeights, this);
        FontUtil.setNoType(weightAmount, this);

        checkFirstTime();

        weightAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    System.out.println("done clicked");
                    onDoneClicked();
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

    private void onDoneClicked(){
        if(newFragment != null) {
            FragmentManager fm  = getSupportFragmentManager();
            fm.popBackStack();
        }
        if(weightAmount.getText().toString().length() > 0) {

            FrameLayout layout = findViewById(R.id.frag);
            layout.removeAllViewsInLayout();
            WeightCalculator weightCalculator = new WeightCalculator(2.5, 45, getAvailablePlates());

            weightCalculator.configurePlates(Integer.parseInt(weightAmount.getText().toString()));

            newFragment = WeightDisplay.newInstance(weightCalculator.getPlates45(), weightCalculator.getPlates35(),
                    weightCalculator.getPlates25(),
                    weightCalculator.getPlates10(),
                    weightCalculator.getPlates5(),
                    weightCalculator.getPlates2pnt5());

            ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
            ft.replace(R.id.frag, newFragment, "fragment");

// Start the animated transition.
            ft.commit();
            isDisplayed = true;
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
            }
            SharedPrefUtil.saveFirstTime(this);
        }
    }

    private HashMap<String, Integer> getAvailablePlates(){
        HashMap<String, Integer> plates = new HashMap<>();
        int length = WeightCalculator.WEIGHTS.length;
        for(int i = 0; i<length; i++){
            String key = WeightCalculator.WEIGHTS[i];
            plates.put(key, SharedPrefUtil.retrieveAvalable(key, this));
        }
        return plates;
    }
}
