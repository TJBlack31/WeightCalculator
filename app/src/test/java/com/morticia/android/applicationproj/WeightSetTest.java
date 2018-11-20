package com.morticia.android.applicationproj;

import android.app.Activity;
import android.content.Context;

import com.travisb.android.simpleplatecalculator.DisplayUpdateCallback;
import com.travisb.android.simpleplatecalculator.WeightCalculator;
import com.travisb.android.simpleplatecalculator.calculator.WeightCalculatorLB;

import org.junit.Test;
import org.junit.runners.JUnit4;
import static org.junit.Assert.*;


import java.util.HashMap;


/**
 * Created by Morticia on 3/20/18.
 */
public class WeightSetTest {

    @Test
    public void configurePlates() throws Exception {
        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<String, Integer> desiredResult = new HashMap<>();
        for(int i = 0; i< WeightCalculatorLB.WEIGHTSLB.length; i++){
            hashMap.put(WeightCalculatorLB.WEIGHTSLB[i], 10);
            desiredResult.put(WeightCalculatorLB.WEIGHTSLB[i],0);
        }
        desiredResult.put(WeightCalculatorLB.WEIGHTSLB[0], 2);

        WeightCalculatorLB weightSet = new WeightCalculatorLB(45, hashMap, new DisplayUpdateCallback() {
            @Override
            public void onDisplayUpdate(String updatedText, boolean isDisplayable) {

            }

            @Override
            public Context getActivityContext() {
                return null;
            }
        });

        weightSet.configure(225);

        assertEquals(desiredResult, weightSet.getWeightsUsed());

    }

}