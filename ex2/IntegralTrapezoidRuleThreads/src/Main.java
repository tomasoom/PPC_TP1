public class Main {
    public static void main(String[] args) {
        System.out.println(CalculateIntegral(0, 1, 80000));
    }

    public static float FunctionValue(float a){
        //return a*(a-1);
        return a*a;
    }

    public static float CalculateIntegral(float lowerBoundary, float upperBoundary, int divisions){

        float dx = (upperBoundary - lowerBoundary)/divisions;
        float[] points = new float[divisions+1];
        float[] area = new float[1];
        int[] nDivisions = {0};
        area[0] = 0;

        int cores = Runtime.getRuntime().availableProcessors();
        Thread[] threads = new Thread[cores];

        for(int th = 0; th < cores; th++){
            final int t = th;
            threads[t] = new Thread(() -> {
                //System.out.println("Current Thread: "+ Thread.currentThread());
                int start = t*(divisions/cores);
                int end = (t+1)*(divisions/cores);

                if(t == cores - 1){
                    end = (int) upperBoundary;
                }

                for(int i = start; i<end; i++){
                    points[i] = i*dx;
                    nDivisions[0]++;
                    if(i == 0 || i==divisions)
                    {
                        area[0] +=(dx/2)*FunctionValue(points[i]);
                    }
                    else{
                        area[0] += (dx/2)*(2*FunctionValue(points[i]));
                    }
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
        System.out.println(nDivisions[0]);
        return area[0];
    }
}
