package com.morticia.android.applicationproj;

import android.app.Activity;

import com.travisb.android.simpleplatecalculator.WeightCalculator;

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
        for(int i = 0; i<WeightCalculator.WEIGHTS.length; i++){
            hashMap.put(WeightCalculator.WEIGHTS[i], 10);
            desiredResult.put(WeightCalculator.WEIGHTS[i],0);
        }
        desiredResult.put(WeightCalculator.WEIGHTS[0], 2);

        WeightCalculator weightSet = new WeightCalculator( 45, hashMap);

        weightSet.configurePlates(225);

        assertEquals(desiredResult, weightSet.getWeightsUsed());

    }

}