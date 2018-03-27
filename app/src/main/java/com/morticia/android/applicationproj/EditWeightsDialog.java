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
import android.widget.TextView;


public class EditWeightsDialog extends DialogFragment {

    private Button saveButton;
    EditText[] editTexts;
    TextView[] textViews;

    public EditWeightsDialog() {

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
                view.findViewById(R.id.edit_weights2pnt5),
                view.findViewById(R.id.editBarWeight_editTxt)};
        textViews = new TextView[]{view.findViewById(R.id.edit45txtview), view.findViewById(R.id.edit35txtview),
                view.findViewById(R.id.edit25txtview),
                view.findViewById(R.id.edit10txtview),
                view.findViewById(R.id.edit5txtview),
                view.findViewById(R.id.edit2pnt5txtview),
                view.findViewById(R.id.editbarweight_txtview)};

        for(int i = 0; i<WeightCalculator.WEIGHTS.length; i++){
            editTexts[i].setText(Integer.toString(SharedPrefUtil.retrieveAvalable(WeightCalculator.WEIGHTS[i], getContext())));

        }
        for(int i = 0; i<textViews.length; i++){
            FontUtil.setNoType(editTexts[i], getContext());
            FontUtil.setTextType(textViews[i], getContext());
            if(i==textViews.length-1){
                editTexts[i].setText(Integer.toString(SharedPrefUtil.retrieveBar(getContext())));
            }
        }
        FontUtil.setTextType(saveButton, getContext());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i<WeightCalculator.WEIGHTS.length; i++){
                    SharedPrefUtil.saveAvailable(WeightCalculator.WEIGHTS[i], Integer.parseInt(editTexts[i].getText().toString()),
                            getContext());

                }
                SharedPrefUtil.saveBar(Integer.parseInt(editTexts[editTexts.length-1].getText().toString()), getContext());
                dismiss();
            }
        });


        getDialog().setTitle("Hello");

        return view;
    }
}
