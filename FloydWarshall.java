import java.util.Arrays;

public class FloydWarshall {
    final static int INF = 99999; // Representing infinity

    public static void main(String[] args) {
        // Define intersections as indices: A=0, B=1, C=2, D=3, E=4
        int[][] graph = {
            {0, 5, 10, INF, INF},
            {INF, 0, 2, 8, INF},
            {INF, INF, 0, 4, 15},
            {INF, INF, INF, 0, 6},
            {INF, INF, INF, INF, 0}
        };

        floydWarshall(graph);
    }

    static void floydWarshall(int[][] graph) {
        int V = graph.length;
        int[][] dist = new int[V][V];

        // Copy the original graph into the distance matrix
        for (int i = 0; i < V; i++) {
            System.arraycopy(graph[i], 0, dist[i], 0, V);
        }

        // Floyd-Warshall algorithm
        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    if (dist[i][k] != INF && dist[k][j] != INF && dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        printSolution(dist);
    }

    static void printSolution(int[][] dist) {
        int V = dist.length;
        System.out.println("Shortest distances between intersections:");
        char[] nodes = {'A', 'B', 'C', 'D', 'E'};

        System.out.print("    ");
        for (char node : nodes) {
            System.out.print(node + "   ");
        }
        System.out.println();

        for (int i = 0; i < V; i++) {
            System.out.print(nodes[i] + "  ");
            for (int j = 0; j < V; j++) {
                if (dist[i][j] == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.printf("%3d ", dist[i][j]);
                }
            }
            System.out.println();
        }
    }
}