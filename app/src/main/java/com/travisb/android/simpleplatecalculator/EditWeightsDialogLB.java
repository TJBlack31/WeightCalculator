package com.travisb.android.simpleplatecalculator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.travisb.android.simpleplatecalculator.calculator.WeightCalculatorLB;
import com.travisb.android.simpleplatecalculator.utils.FontUtil;
import com.travisb.android.simpleplatecalculator.utils.SharedPrefUtil;


public class EditWeightsDialogLB extends DialogFragment {

    private Button saveButton;
    EditText[] editTexts;
    TextView[] textViews;

    public EditWeightsDialogLB() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weights_lb, container);
        saveButton = view.findViewById(R.id.saveWeights);

        editTexts = new EditText[]{view.findViewById(R.id.edit_weights45), view.findViewById(R.id.edit_weights35),
                view.findViewById(R.id.edit_weights25),
                view.findViewById(R.id.edit_weights10),
                view.findViewById(R.id.edit_weights5),
                view.findViewById(R.id.edit_weights2pnt5),
                view.findViewById(R.id.editBarWeight_editTxt)};
        textViews = new TextView[]{view.findViewById(R.id.edit45txtview), view.findViewById(R.id.edit35txtview),
                view.findViewById(R.id.edit25txtview),
                view.findViewById(R.id.edit10txtview),
                view.findViewById(R.id.edit5txtview),
                view.findViewById(R.id.edit2pnt5txtview),
                view.findViewById(R.id.editbarweight_txtview)};

        for(int i = 0; i< WeightCalculatorLB.WEIGHTSLB.length ; i++){
            editTexts[i].setText(Integer.toString((int)SharedPrefUtil.retrieveAvalableLB(WeightCalculatorLB.WEIGHTSLB[i], getContext())));

        }

        for(int i = 0; i<textViews.length; i++){
            FontUtil.setNoType(editTexts[i], getContext());
            FontUtil.setTextType(textViews[i], getContext());
            if(i==textViews.length-1){
                editTexts[i].setText(Integer.toString(SharedPrefUtil.retrieveBarLB(getContext()).intValue()));
            }
        }
        //todo: remove print statements and figure out shared pref issue
        FontUtil.setTextType(saveButton, getContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i< WeightCalculatorLB.WEIGHTSLB.length; i++){
                    System.out.println(WeightCalculatorLB.WEIGHTSLB.length);
                    String intString = editTexts[i].getText().toString();
                    int value = 0;
                    if(!intString.equals("") && intString.length() < 4){
                        value = Integer.parseInt(intString);
                    }else{
                        value = 10;
                        editTexts[i].setText(String.format(Integer.toString(value)));
                    }
                    SharedPrefUtil.saveAvailableLB(WeightCalculatorLB.WEIGHTSLB[i], value,
                            getContext());

                }
                SharedPrefUtil.saveBarLB(Integer.parseInt(editTexts[editTexts.length-1].getText().toString()), getContext());
                dismiss();
            }
        });


        getDialog().setTitle("Edit Available Weights");

        return view;
    }


}
