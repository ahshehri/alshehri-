/*
 * cpcs324
 *
 */
package Floyd_Warshall_algorithm;

public class Floyd_Warshall {

    static final int INF = 99999;
    // n is the size of matrix 
    static int n = 10;
    // x is the number of itration of the sultion 
    static int x = 0;

    public static void main(String[] args) {

        int graph[][] = {{0, 10, INF, INF, INF, 5, INF, INF, INF, INF},
        {INF, 0, 3, INF, 3, INF, INF, INF, INF, INF},
        {INF, INF, 0, 4, INF, INF, INF, 5, INF, INF},
        {INF, INF, INF, 0, INF, INF, INF, INF, 4, INF},
        {INF, INF, 4, INF, 0, INF, 2, INF, INF, INF},
        {INF, 3, INF, INF, INF, 0, INF, INF, INF, 2},
        {INF, INF, INF, 7, INF, INF, 0, INF, INF, INF},
        {INF, INF, INF, 4, INF, INF, INF, 0, 3, INF},
        {INF, INF, INF, INF, INF, INF, INF, INF, 0, INF},
        {INF, 6, INF, INF, INF, INF, 8, INF, INF, 0}};

        printMatrix(graph);
        System.out.println("\n\n\t\t    ** Solution of the Matrix: **\n");
        FloydWarshall(graph);
    }

    public static void FloydWarshall(int graph[][]) {
        int[][] Matrix = new int[n][n];
        int i, j, k;

        Matrix = graph;

        int[][] predMatrix = new int[n][n];
        /*
		 * Predecessor Matrix: is defined as the predecessor of vertex i on a shortest
		 * path from vertex j with all intermediate vertices in the set 1,2,...,k
         */
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i != j) {
                    predMatrix[i][j] = j + 1;
                }
            }
        }

        for (k = 0; k < n; k++) {
            for (j = 0; j < n; j++) {
                for (i = 0; i < n; i++) {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of Matrix[i][j]
                    if (Matrix[i][k] + Matrix[k][j] < Matrix[i][j]) {
                        Matrix[i][j] = Matrix[i][k] + Matrix[k][j];
                        predMatrix[i][j] = predMatrix[i][k];
                    }
                }

            }
            printMatrix(Matrix);
        }

    }

    public static void printMatrix(int dist[][]) {
        System.out.println("\n \t \t \t Itration number: " + x + "\n");
        for (int i = 0; i < n; ++i) {

            for (int j = 0; j < n; ++j) {

                if (dist[i][j] == INF) {
                    System.out.print("∞   \t");
                } else {
                    System.out.print(dist[i][j] + "   \t");
                }

            }
            System.out.println();

        }
        x++;
    }
}
