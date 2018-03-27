package com.travisb.android.simpleplatecalculator;

import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by Morticia on 3/20/18.
 */

public class WeightCalculator {


    private double barWeight;

    private int weightNoBarHalf;

    public static final String[] WEIGHTS = new String[]{"FORTYFIVES", "THIRTYFIVES", "TWENTYFIVES",
            "TENS","FIVES", "TWOPNTFIVES" } ;



    private static HashMap<String, Integer> weightsUsed;

    public WeightCalculator( double barWeight, HashMap<String, Integer> availableWeights){
        this.barWeight = barWeight;
        this.weightsUsed = availableWeights;


    }

    public boolean configurePlates(int weight, Context context){

        this.weightNoBarHalf = (weight - (int) this.barWeight) / 2;

        trimAmount(WEIGHTS[0], 45);
        trimAmount(WEIGHTS[1], 35);
        trimAmount(WEIGHTS[2], 25);
        trimAmount(WEIGHTS[3], 10);
        trimAmount(WEIGHTS[4], 5);
        trimAmount(WEIGHTS[5], 2);

        if(this.weightNoBarHalf > 3){
            //Maybe do a callback to the activity to send a toast or something
            Toast.makeText(context, "You don't have enough weight available.  Change your available" +
                    " weights", Toast.LENGTH_LONG).show();
            return false;
        }


        return true;
    }

    public boolean configurePlates(int weight){

        this.weightNoBarHalf = (weight - (int) this.barWeight) / 2;

        trimAmount(WEIGHTS[0], 45);
        trimAmount(WEIGHTS[1], 35);
        trimAmount(WEIGHTS[2], 25);
        trimAmount(WEIGHTS[3], 10);
        trimAmount(WEIGHTS[4], 5);
        trimAmount(WEIGHTS[5], 2);

        if(this.weightNoBarHalf > 3){

            return false;
        }


        return true;
    }
    private void trimAmount(String key, int amount){
        if(isPlateDvsbl(amount)){
            configure(amount, key);
        }else{
            weightsUsed.put(key, 0);
        }
    }

    private boolean isPlateDvsbl(int plateWeight ){
        if(this.weightNoBarHalf / plateWeight >= 1){
            return true;
        }
        else{
            return false;
        }
    }

    public int getPlates45() {
        return weightsUsed.get(WEIGHTS[0]);
    }


    private void configure(int amount, String key){
        int availablePlates = weightsUsed.get(key);
        int divisiblePlatesNeeded = weightNoBarHalf/amount;
        if (divisiblePlatesNeeded > availablePlates){
            this.weightNoBarHalf = weightNoBarHalf - (availablePlates * amount);
        }
        else {
            this.weightNoBarHalf = weightNoBarHalf % amount;
            weightsUsed.put(key, divisiblePlatesNeeded);
        }
    }

    public int getPlates35() {
        return weightsUsed.get(WEIGHTS[1]);
    }

    public int getPlates25() {
        return weightsUsed.get(WEIGHTS[2]);
    }

    public int getPlates10() {
        return weightsUsed.get(WEIGHTS[3]);
    }

    public int getPlates5() {
        return weightsUsed.get(WEIGHTS[4]);
    }

    public int getPlates2pnt5() {
        return weightsUsed.get(WEIGHTS[5]);
    }

    public static HashMap<String, Integer> getWeightsUsed() {
        return weightsUsed;
    }
}
