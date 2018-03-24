package com.morticia.android.applicationproj;

/**
 * Created by Morticia on 3/20/18.
 */

public class WeightCalculator {



    boolean is35Available;
    double lowestWeight;
    double barWeight;
    int plates45;
    int plates35;
    int plates25;
    int plates10;
    int plates5;
    int plates2pnt5;
    int weightNoBarHalf;

    public WeightCalculator(double lowestWeight, double barWeight, boolean is35Available){
        this.lowestWeight = lowestWeight;
        this.barWeight = barWeight;
        this.is35Available = is35Available;

    }

    public void configurePlates(int weight){

        this.weightNoBarHalf = (weight - (int) this.barWeight) / 2;

        if(isPlateDvsbl(45)){
            this.plates45 = weightNoBarHalf/45;
            this.weightNoBarHalf = weightNoBarHalf % 45;
        }
        if(isPlateDvsbl(35) && is35Available){
            this.plates35 = weightNoBarHalf/35;
            this.weightNoBarHalf = weightNoBarHalf % 35;
        }
        if(isPlateDvsbl(25)){
            this.plates25 = weightNoBarHalf/25;
            this.weightNoBarHalf = weightNoBarHalf % 25;
        }
        if(isPlateDvsbl(10)){
            this.plates10 = weightNoBarHalf/10;
            this.weightNoBarHalf = weightNoBarHalf % 10;
        }
        if(isPlateDvsbl(5)){
            this.plates5 = weightNoBarHalf/5;
            this.weightNoBarHalf = weightNoBarHalf % 5;
        }
        if(weightNoBarHalf <= 2.5 && weightNoBarHalf > 0){
            this.plates2pnt5 = 1;
        }


        System.out.println(plates45 + " " + plates35 + " " + plates25 + " " + plates10 + " " + plates5 + " " + plates2pnt5 + " " );

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
        return plates45;
    }

    public int getPlates35() {
        return plates35;
    }

    public int getPlates25() {
        return plates25;
    }

    public int getPlates10() {
        return plates10;
    }

    public int getPlates5() {
        return plates5;
    }

    public int getPlates2pnt5() {
        return plates2pnt5;
    }



}
