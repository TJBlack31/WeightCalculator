package com.travisb.android.simpleplatecalculator.calculator;

import com.travisb.android.simpleplatecalculator.DisplayUpdateCallback;
import com.travisb.android.simpleplatecalculator.R;

import java.util.HashMap;

public abstract class WeightCalculator {


    double barWeight;
    protected DisplayUpdateCallback displayUpdateCallback;
    protected double weightNoBarHalf;
    protected static HashMap<String, Integer> weightsUsed;


    public static final String[] WEIGHTSLB = new String[]{"FORTYFIVES", "THIRTYFIVES", "TWENTYFIVES",
            "TENS","FIVES", "TWOPNTFIVES" } ;
    public static final String[] WEIGHTSKG = new String[]{"TWENTYFIVESKG", "TWENTIESKG", "FIFTEENSKG",
            "TENSKG","FIVESKG", "TWOPNTFIVESKG", "TWOSKG", "ONEPNTFIVESKG", "ONESKG", "PNTFIVESKG" } ;


    public WeightCalculator(double barWeight, HashMap<String, Integer> availableWeights, DisplayUpdateCallback displayUpdate){
        this.barWeight = barWeight;
        this.weightsUsed = availableWeights;
        this.displayUpdateCallback = displayUpdate;


    }

    public static HashMap<String, Integer> getWeightsUsed() {
        return weightsUsed;
    }

    public double getBarWeight() {
        return this.barWeight;
    }

    protected void displayNotEnough(DisplayUpdateCallback updateCallback){
        String message = updateCallback.getActivityContext().getString(R.string.notEnoughPlts);
        updateCallback.onDisplayUpdate(message, false);
    }

    public abstract boolean toPlates(double weight, DisplayUpdateCallback updateCallback);


    public abstract double getAvalableWeight();

    protected abstract void configure(double weightOfPlate, String key);

    public double getPlates45() {
        System.out.println(weightsUsed.get(WEIGHTSLB[0]) + " from 45");
        return weightsUsed.get(WEIGHTSLB[0]);
    }

    public double getPlates35() {

        System.out.println(weightsUsed.get(WEIGHTSLB[1]) + " from 35");
        return weightsUsed.get(WEIGHTSLB[1]);
    }

    public double getPlates25() {
        System.out.println(weightsUsed.get(WEIGHTSLB[2]) + " from 25");
        return weightsUsed.get(WEIGHTSLB[2]);
    }

    public double getPlates10() {
        System.out.println(weightsUsed.get(WEIGHTSLB[3]) + " from 10");
        return weightsUsed.get(WEIGHTSLB[3]);
    }

    public double getPlates5() {
        System.out.println(weightsUsed.get(WEIGHTSLB[4]) + " from 5");
        return weightsUsed.get(WEIGHTSLB[4]);
    }

    public double getPlates2pnt5() {
        System.out.println(weightsUsed.get(WEIGHTSLB[5]) + " from 2pnt5");
        return weightsUsed.get(WEIGHTSLB[5]);
    }


    public double getPlatesKG25() {
        return weightsUsed.get(WEIGHTSKG[0]);
    }

    public double getPlatesKG20() {
        return weightsUsed.get(WEIGHTSKG[1]);
    }

    public double getPlatesKG15() {
        return weightsUsed.get(WEIGHTSKG[2]);
    }

    public double getPlatesKG10() {
        return weightsUsed.get(WEIGHTSKG[3]);
    }

    public double getPlatesKG5() {
        return weightsUsed.get(WEIGHTSKG[4]);
    }

    public double getPlatesKG2pnt5() {
        return weightsUsed.get(WEIGHTSKG[5]);
    }

    public double getPlatesKG2() {
        return weightsUsed.get(WEIGHTSKG[6]);
    }

    public double getPlatesKG1pnt5() {
        return weightsUsed.get(WEIGHTSKG[7]);
    }
    public double getPlatesKG1() {
        return weightsUsed.get(WEIGHTSKG[8]);
    }

    public double getPlatesKGpnt5() {
        return weightsUsed.get(WEIGHTSKG[9]);
    }

}
