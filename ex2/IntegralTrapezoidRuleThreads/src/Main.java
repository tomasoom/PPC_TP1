public class Main {
    public static void main(String[] args) {
        System.out.println(CalculateIntegral(0, 1, 16000));
    }

    public static float FunctionValue(float a) {
        return a*(a-1);
    }

    public static float CalculateIntegral(int lowerBoundary, int upperBoundary, int divisions){

        float dx = (upperBoundary - lowerBoundary)/(float) divisions;
        float[] points = new float[divisions+1];
        int cores = Runtime.getRuntime().availableProcessors();
        int[] nDivisions = new int[cores];
        int nDivisionsF = 0;
        float areaF = 0;


        Thread[] threads = new Thread[cores];
        float[] area = new float[cores];

        for(int th = 0; th < cores; th++){
            final int t = th;
            threads[t] = new Thread(() -> {
                //System.out.println("Current Thread: "+ Thread.currentThread());
                int start = t*(divisions/cores);
                int end = (t+1)*(divisions/cores);

                if(t == cores - 1){
                    end = divisions;
                }

                for(int i = start; i<end; i++){
                    points[i] = i*dx;
                    if(i == 0 || i == divisions)
                    {
                        area[t] += (dx/2)*FunctionValue(points[i]);
                    }
                    else{
                        area[t] += (dx/2)*(2*FunctionValue(points[i]));
                    }
                    nDivisions[t]++;
                }
            });
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
        System.out.println(nDivisions);
        for(float value : area){
            areaF += value;
        }

        for(int division : nDivisions){
            nDivisionsF += division;
        }
        return areaF;
    }
}
