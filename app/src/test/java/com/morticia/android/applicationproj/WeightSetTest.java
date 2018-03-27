package com.morticia.android.applicationproj;

import com.travisb.android.simpleplatecalculator.WeightCalculator;

import org.junit.Test;

/**
 * Created by Morticia on 3/20/18.
 */
public class WeightSetTest {

    @Test
    public void configurePlates() throws Exception {

        WeightCalculator weightSet = new WeightCalculator(2.5, 45, false);
        weightSet.configurePlates(185);
    }

}