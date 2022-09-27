import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Estimate Value of Pi: " + EstimatePi(1000000));
    }

    public static float[] ThrowDart(){
        float[] dart = new float[2];
        Random r = new Random();
        dart[0] = r.nextFloat(-1, 1);
        dart[1] = r.nextFloat(-1, 1);
        return dart;
    }

    public static boolean CheckIfInsideCirle(float[] dart){
        if(dart[0]*dart[0] + dart[1]*dart[1] <= 1){
            return true;
        }
        else{
            return false;
        }
    }

    public static float EstimatePi(float nThrows){
        int throwsLandedInsideCircle = 0;
        float ePI;
        for(int i = 0; i < nThrows; i++){
            if(CheckIfInsideCirle(ThrowDart())){
                throwsLandedInsideCircle++;
            }
        }
        ePI = (float) 4*(throwsLandedInsideCircle/nThrows);
        return ePI;
    }
}
