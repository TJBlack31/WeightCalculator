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

public class MainActivity extends AppCompatActivity {

    EditText weightAmount;

    FragmentTransaction ft;
    boolean isDisplayed;
    WeightDisplay newFragment;
    TextView weightLabel;
    Switch is35Available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weightLabel = findViewById(R.id.weightLabel);
        is35Available = findViewById(R.id.available35);
        weightAmount = findViewById(R.id.weightAmount);

        FontUtil.setTextType(weightLabel, this);
        FontUtil.setTextType(is35Available, this);
        FontUtil.setNoType(weightAmount, this);

        weightAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    onDoneClicked();
                }
                return false;
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
            WeightCalculator weightCalculator = new WeightCalculator(2.5, 45, is35Available.isChecked());
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
}
