package com.morticia.android.applicationproj;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText weightAmount;
    Button calculateButton;
    FragmentTransaction ft;
    boolean isDisplayed;
    WeightDisplay newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weightAmount = findViewById(R.id.weightAmount);
        weightAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newFragment != null) {
                    getSupportFragmentManager().beginTransaction().
                            remove(getSupportFragmentManager().findFragmentById(R.id.frag)).commit();
                }
                if(weightAmount.getText().toString().length() > 0) {
                    FrameLayout layout = (FrameLayout) findViewById(R.id.frag);
                    layout.removeAllViewsInLayout();
                    WeightCalculator weightCalculator = new WeightCalculator(2.5, 45, false);
                    weightCalculator.configurePlates(Integer.parseInt(weightAmount.getText().toString()));

                     newFragment = WeightDisplay.newInstance(weightCalculator.getPlates45(), weightCalculator.getPlates35(),
                            weightCalculator.getPlates25(),
                            weightCalculator.getPlates10(),
                            weightCalculator.getPlates5(),
                            weightCalculator.getPlates2pnt5());

                    ft = getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
                    ft.replace(R.id.frag, newFragment, "fragment");
                    ft.addToBackStack(null);
// Start the animated transition.
                    ft.commit();
                    isDisplayed = true;
                }
            }
        });
    }
}
