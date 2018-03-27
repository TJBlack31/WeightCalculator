package com.morticia.android.applicationproj;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class EditWeightsDialog extends DialogFragment {

    private Button saveButton;
    EditText[] editTexts;
//    private EditText fortyFives;
//    private EditText thirtyFives;
//    private EditText twentyFives;
//    private EditText tens;
//    private EditText fives;
//    private EditText twoPntFives;


    public EditWeightsDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_weights, container);
        saveButton = view.findViewById(R.id.saveWeights);

        editTexts = new EditText[]{view.findViewById(R.id.edit_weights45), view.findViewById(R.id.edit_weights35),
                view.findViewById(R.id.edit_weights25),
                view.findViewById(R.id.edit_weights10),
                view.findViewById(R.id.edit_weights5),
                view.findViewById(R.id.edit_weights2pnt5)};

        for(int i = 0; i<WeightCalculator.WEIGHTS.length; i++){
            editTexts[i].setText(Integer.toString(SharedPrefUtil.retrieveAvalable(WeightCalculator.WEIGHTS[i], getContext())));
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i<WeightCalculator.WEIGHTS.length; i++){
                    SharedPrefUtil.saveAvailable(WeightCalculator.WEIGHTS[i], Integer.parseInt(editTexts[i].getText().toString()),
                            getContext());
                }
                dismiss();
            }
        });


        getDialog().setTitle("Hello");

        return view;
    }
}
