package com.travisb.android.simpleplatecalculator.calculator;

import com.travisb.android.simpleplatecalculator.DisplayUpdateCallback;
import com.travisb.android.simpleplatecalculator.R;

import java.util.HashMap;

public class WeightCalculatorKG extends WeightCalculator {


    public WeightCalculatorKG(double barWeight, HashMap<String, Integer> availableWeights, DisplayUpdateCallback updateCallback) {
        super(barWeight, availableWeights, updateCallback);
    }
    @Override
    public boolean toPlates(double weight, DisplayUpdateCallback updateCallback){

        this.weightNoBarHalf = (weight - this.barWeight) / 2;

        trimAmount(WEIGHTSKG[0], 25);
        trimAmount(WEIGHTSKG[1], 20);
        trimAmount(WEIGHTSKG[2], 15);
        trimAmount(WEIGHTSKG[3], 10);
        trimAmount(WEIGHTSKG[4], 5);
        trimAmount(WEIGHTSKG[5], 2.5);
        trimAmount(WEIGHTSKG[6], 2);
        trimAmount(WEIGHTSKG[7], 1.5);
        trimAmount(WEIGHTSKG[8], 1);
        trimAmount(WEIGHTSKG[9], 0.5);


        if(this.weightNoBarHalf > 3){
            //Maybe do a callback to the activity to send a toast or something
           displayNotEnough(this.displayUpdateCallback);
        }
        return true;
    }

    private void trimAmount(String key, double weightOfPlate){
        if(isPlateDvsbl(weightOfPlate)){
            configure(weightOfPlate, key);
        }else{
            weightsUsed.put(key, 0);
        }
    }

    private boolean isPlateDvsbl(double plateWeight ){
        if(this.weightNoBarHalf / plateWeight >= .5){
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
        double weight = 0;
        for (int i = 0; i< getPlatesKG25() *2; i ++){
            weight = weight + 25;
        }
        for (int i = 0; i< getPlatesKG20()*2; i ++){
            weight = weight + 20;
        }
        for (int i = 0; i< getPlatesKG15()*2; i ++){
            weight = weight + 15;
        }
        for (int i = 0; i< getPlatesKG10()*2; i ++){
            weight = weight + 10;
        }
        for (int i = 0; i< getPlatesKG5()*2; i ++){
            weight = weight + 5;
        }
        for (int i = 0; i< getPlatesKG2pnt5()*2; i ++){
            weight = weight + 5/2;
        }
        for (int i = 0; i< getPlatesKG2()*2; i ++){
            weight = weight + 2;
        }
        for (int i = 0; i< getPlatesKG1pnt5()*2; i ++){
            weight = weight + 3/2;
        }
        for (int i = 0; i< getPlatesKG1()*2; i ++){
            weight = weight + 1;
        }
        for (int i = 0; i< getPlatesKGpnt5()*2; i ++){
            weight = weight + .5;
        }
        return weight;
    }
    @Override
    protected void configure(double weightOfPlate, String key){
        double availablePlates = weightsUsed.get(key);
        double divisiblePlatesNeeded = weightNoBarHalf/weightOfPlate;
        if (divisiblePlatesNeeded > availablePlates){
            this.weightNoBarHalf = weightNoBarHalf - (availablePlates * weightOfPlate);
        }
        else {
            this.weightNoBarHalf = this.weightNoBarHalf % weightOfPlate;
            weightsUsed.put(key, (int)divisiblePlatesNeeded);
        }
    }
    public static HashMap<String, Integer> getWeightsUsed() {
        return weightsUsed;
    }

}