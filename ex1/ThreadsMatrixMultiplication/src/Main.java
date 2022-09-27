public class Main {
    public static void main(String[] args) {

        int M = 2; //R1
        int N = 3; //C1 R2
        int O = 2; //C2

        int[][] matrix1 = new int[M][N];
        int[][] matrix2 = new int[N][O];
        int[][] matrix3 = new int[M][O];

        int cores = Runtime.getRuntime().availableProcessors();

        Thread[] threads = new Thread[cores];

        int aux = 0;

        matrix1[0][0] = 1;
        matrix1[0][1] = 2;
        matrix1[0][2] = 3;
        matrix1[1][0] = 3;
        matrix1[1][1] = 2;
        matrix1[1][2] = 1;

        matrix2[0][0] = 1;
        matrix2[1][0] = 2;
        matrix2[2][0] = 3;
        matrix2[0][1] = 3;
        matrix2[1][1] = 2;
        matrix2[2][1] = 1;

        /*  1 2 3     1 2
            4 5 6     3 4
                      5 6  */
        for(int th = 0; th<cores; th++){
            final int t = th;
            threads[t] = new Thread(() -> {
                int start = t * (N / cores);
                int end = (t+1) * (N / cores);

                if(t == cores - 1){
                    end = N-1;
                }

                for(int row = start; row < end; row++){
                    for(int column = 0; column < O; column++){
                        matrix3[row][column] = 0;
                        for(int i = 0; i < N; i++){
                            //aux += matrix1[row][i] * matrix2[i][column];
                            matrix3[row][column] += matrix1[row][i] * matrix2[i][column];
                        }
                    }
             }
            });
            threads[t].start();
        }

        for(Thread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }





/*
        for(int row = 0; row < M; row++){
            for(int column = 0; column < O; column++){
                for(int i = 0; i < N; i++){
                    aux += matrix1[row][i] * matrix2[i][column];
                }
                matrix3[row][column] = aux;
                aux = 0;
            }
        }
*/
    }
}
