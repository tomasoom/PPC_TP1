import java.util.Random;

public class Main {
    public static void main(String[] args) {
        System.out.println("Estimated Value of Pi: " + EstimatePi(8000000));
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

    public static float EstimatePi(int nThrows){
        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];
        int[] throwsLandedInsideCircle = new int[1];
        throwsLandedInsideCircle[0] = 0;
        int[] throwsLandedInsideSquare = new int[1];
        throwsLandedInsideSquare[0] = 0;
        float ePI;


        for(int th = 0; th<cores; th++){
            final int t = th;
            threads[t] = new Thread(() -> {
                int start = t*(nThrows/cores);
                int end = (t+1)*(nThrows/cores);

                if(t == cores-1){
                    end = nThrows;
                }


                for(int i = start; i < end; i++){
                    if(CheckIfInsideCirle(ThrowDart())){
                        throwsLandedInsideCircle[0]++;
                    }
                    throwsLandedInsideSquare[0]++;

                }
            } );

            threads[t].start();
        }

        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread " + thread + " was interrupted");
                throw new RuntimeException(e);
            }
        }

        ePI = (4*(throwsLandedInsideCircle[0]/(float) nThrows));

        return ePI;

    }
}

