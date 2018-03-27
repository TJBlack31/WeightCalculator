package com.morticia.android.applicationproj;

import java.util.HashMap;

/**
 * Created by Morticia on 3/20/18.
 */

public class WeightCalculator {

    double lowestWeight;
    double barWeight;
    int plates45;
    int plates35;
    int plates25;
    int plates10;
    int plates5;
    int plates2pnt5;
    int weightNoBarHalf;

    public static final String[] WEIGHTS = new String[]{"FORTYFIVES", "THIRTYFIVES", "TWENTYFIVES",
            "TENS","FIVES", "TWOPNTFIVES" } ;



    public static HashMap<String, Integer> weightsUsed;

    public WeightCalculator(double lowestWeight, double barWeight, HashMap<String, Integer> availableWeights){
        this.lowestWeight = lowestWeight;
        this.barWeight = barWeight;
        this.weightsUsed = availableWeights;


    }

    public void configurePlates(int weight){

        this.weightNoBarHalf = (weight - (int) this.barWeight) / 2;

        trimAmount(WEIGHTS[0], 45);
        trimAmount(WEIGHTS[1], 35);
        trimAmount(WEIGHTS[2], 25);
        trimAmount(WEIGHTS[3], 10);
        trimAmount(WEIGHTS[4], 5);
        trimAmount(WEIGHTS[5], 2);

        System.out.println(plates45 + " " + plates35 + " " + plates25 + " " + plates10 + " " + plates5 + " " + plates2pnt5 + " " );

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

}
