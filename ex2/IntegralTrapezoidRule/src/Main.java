public class Main {
    public static void main(String[] args) {
        System.out.println(CalculateIntegral(0, 1, 10000000));
    }

    public static float FunctionValue(float a){
        //return a*(a-1);
        return a*a;
    }

    public static float CalculateIntegral(float lowerBoundary, float upperBoundary, int divisions){
        float area = 0;
        float dx = (upperBoundary - lowerBoundary)/divisions;

        float[] points = new float[divisions+1];

        for(int i = 0; i<divisions+1; i++){
            points[i] = i*dx;
            if(i == 0 || i==divisions)
            {
                area+=(dx/2)*FunctionValue(points[i]);
            }
            else{
                area+=(dx/2)*(2*FunctionValue(points[i]));
            }
        }
        return area;
    }
}
