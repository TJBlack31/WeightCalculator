package com.travisb.android.simpleplatecalculator.calculator;

import com.travisb.android.simpleplatecalculator.DisplayUpdateCallback;
import com.travisb.android.simpleplatecalculator.R;

import java.util.HashMap;

/**
 * Created by Morticia on 3/20/18.
 */

public class WeightCalculatorLB extends WeightCalculator{

    public WeightCalculatorLB(final double barWeight, final HashMap<String, Integer> availableWeights, DisplayUpdateCallback updateCallback){
         super(barWeight, availableWeights, updateCallback);
    }

    @Override
    public boolean toPlates(final double weight, DisplayUpdateCallback updateCallback){

        this.weightNoBarHalf = (weight - this.barWeight) / 2;

        trimAmount(WEIGHTSLB[0], 45);
        trimAmount(WEIGHTSLB[1], 35);
        trimAmount(WEIGHTSLB[2], 25);
        trimAmount(WEIGHTSLB[3], 10);
        trimAmount(WEIGHTSLB[4], 5);
        trimAmount(WEIGHTSLB[5], 5/2);

        if(this.weightNoBarHalf > 3){
            displayNotEnough(this.displayUpdateCallback);
        }
        return true;
    }


    private void configure(final double amount, final String key){
        int availablePlates = weightsUsed.get(key);
        int divisiblePlatesNeeded = (int) (weightNoBarHalf/amount);
        if (divisiblePlatesNeeded > availablePlates/2){
            this.weightNoBarHalf = weightNoBarHalf - (availablePlates * amount);
        }
        else {
            this.weightNoBarHalf = weightNoBarHalf % amount;
            weightsUsed.put(key, divisiblePlatesNeeded);
        }
        System.out.println(weightsUsed.entrySet());
    }


    private void trimAmount(final String key, final double amount){
        if(isPlateDvsbl(amount)){
            configure(amount, key);
        }else{
            weightsUsed.put(key, 0);
        }
    }

    private boolean isPlateDvsbl(final double plateWeight ){
        if(this.weightNoBarHalf / plateWeight >= 1.0){
            return true;
        }
        else{
            return false;
        }
    }

    public double getBarWeight() {
        return barWeight;
    }

    @Override
    public double getAvalableWeight(){
        int weight = 0;
        for (int i = 0; i< getPlates45() *2; i ++){
            weight = weight + 45;
        }
        for (int i = 0; i< getPlates35()*2; i ++){
            weight = weight + 35;
        }
        for (int i = 0; i< getPlates25()*2; i ++){
            weight = weight + 25;
        }
        for (int i = 0; i< getPlates10()*2; i ++){
            weight = weight + 10;
        }
        for (int i = 0; i< getPlates5()*2; i ++){
            weight = weight + 5;
        }
        for (int i = 0; i< getPlates2pnt5()*2; i ++){
            weight = weight + 5/2;
        }
        return weight;
    }
}
