package com.travisb.android.simpleplatecalculator.editdialogfragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.travisb.android.simpleplatecalculator.R;
import com.travisb.android.simpleplatecalculator.calculator.WeightCalculatorKG;
import com.travisb.android.simpleplatecalculator.utils.FontUtil;
import com.travisb.android.simpleplatecalculator.utils.SharedPrefUtil;


public class EditWeightsDialogKG extends BaseEditDialog {

    private Button saveButton;
    EditText[] editTexts;
    TextView[] textViews;

    public EditWeightsDialogKG() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weights_kg, container);
        saveButton = view.findViewById(R.id.saveWeights);

        editTexts = new EditText[]{view.findViewById(R.id.edit_weights25kg), view.findViewById(R.id.edit_weights20kg),
                view.findViewById(R.id.edit_weights15kg),
                view.findViewById(R.id.edit_weights10kg),
                view.findViewById(R.id.edit_weights5kg),
                view.findViewById(R.id.edit_weights2pnt5kg),
                view.findViewById(R.id.edit_weights2kg),
                view.findViewById(R.id.edit_weights1pnt5kg),
                view.findViewById(R.id.edit_weights1kg),
                view.findViewById(R.id.edit_weightspnt5kg),
                view.findViewById(R.id.editBarWeightkg_editTxt)};
        textViews = new TextView[]{view.findViewById(R.id.edit25kgtxtview), view.findViewById(R.id.edit20kgtxtview),
                view.findViewById(R.id.edit15kgtxtview),
                view.findViewById(R.id.edit10kgtxtview),
                view.findViewById(R.id.edit5kgtxtview),

                view.findViewById(R.id.edit2pnt5kgtxtview),
                view.findViewById(R.id.edit2kgtxtview),
                view.findViewById(R.id.edit1pnt5kgtxtview),
                view.findViewById(R.id.edit1kgtxtview),
                view.findViewById(R.id.editpnt5kgtxtview),
                view.findViewById(R.id.editbarweightkg_txtview)};

        for(int i = 0; i< WeightCalculatorKG.WEIGHTSKG.length; i++){
            editTexts[i].setText(Integer.toString((int)SharedPrefUtil.retrieveAvalableKG(WeightCalculatorKG.WEIGHTSKG[i], getContext())));

        }

        for(int i = 0; i<textViews.length; i++){
            FontUtil.setNoType(editTexts[i], getContext());
            FontUtil.setTextType(textViews[i], getContext());
            if(i==textViews.length-1){
                editTexts[i].setText(Integer.toString(SharedPrefUtil.retrieveBarKG(getContext()).intValue()));
            }
        }
        FontUtil.setTextType(saveButton, getContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i< WeightCalculatorKG.WEIGHTSKG.length; i++){
                    String intString = editTexts[i].getText().toString();
                    int value = 0;
                    if(!intString.equals("") && intString.length() < 4){
                        value = Integer.parseInt(intString);
                    }else{
                        value = 10;
                        editTexts[i].setText(String.format(Integer.toString(value)));
                    }
                    SharedPrefUtil.saveAvailableKG(WeightCalculatorKG.WEIGHTSKG[i], value,
                            getContext());

                }
                SharedPrefUtil.saveBarKG(Integer.parseInt(editTexts[editTexts.length-1].getText().toString()), getContext());
                dismiss();
            }
        });


        getDialog().setTitle("Edit Available Weights");

        return view;
    }


}
