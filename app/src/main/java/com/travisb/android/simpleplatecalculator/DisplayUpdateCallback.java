package com.travisb.android.simpleplatecalculator;

import android.content.Context;

public interface DisplayUpdateCallback{

    void onDisplayUpdate(String updatedText, boolean isDisplayable);
    Context getActivityContext();

}
