package com.travisb.android.simpleplatecalculator.editdialogfragments;

import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BaseEditDialog extends DialogFragment {

    protected Button saveButton;
    EditText[] editTexts;
    TextView[] textViews;
}
