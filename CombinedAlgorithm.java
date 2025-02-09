import java.util.*;

public class CombinedAlgorithm {
    // Graph class representing a weighted graph
    static class Graph {
        int V, E; // Number of vertices (V) and edges (E)
        List<int[]> edges; // List to store edges, each edge is represented as an array {u, v, weight}

        // Constructor to initialize the graph with a specified number of vertices and edges
        Graph(int V, int E) {
            this.V = V;
            this.E = E;
            edges = new ArrayList<>();
        }

        // Method to add an edge to the graph
        void addEdge(int u, int v, int w) {
            edges.add(new int[]{u, v, w}); // Add edge as an array of {startVertex, endVertex, weight}
        }
    }

    // Disjoint Set (Union-Find) class for cycle detection and union operations
    static class DisjointSet {
        int[] parent, rank; // Arrays to store parent and rank of each node

        // Constructor to initialize disjoint set
        DisjointSet(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i; // Initially, each node is its own parent
                rank[i] = 0; // Rank is initialized to 0
            }
        }

        // Find operation with path compression
        int find(int u) {
            if (parent[u] != u) {
                parent[u] = find(parent[u]); // Path compression to speed up future queries
            }
            return parent[u]; // Return the root of the set
        }

        // Union operation by rank
        void union(int u, int v) {
            int rootU = find(u); // Find root of u
            int rootV = find(v); // Find root of v

            if (rootU != rootV) { // Only union if roots are different
                if (rank[rootU] > rank[rootV]) {
                    parent[rootV] = rootU; // Attach rootV to rootU
                } else if (rank[rootU] < rank[rootV]) {
                    parent[rootU] = rootV; // Attach rootU to rootV
                } else {
                    parent[rootV] = rootU; // Attach rootV to rootU
                    rank[rootU]++; // Increase rank if roots are equal
                }
            }
        }
    }

    // Initialize single source for shortest path algorithms
    static void initializeSingleSource(Graph G, int s, int[] dist, int[] prev) {
        Arrays.fill(dist, Integer.MAX_VALUE); // Set all distances to infinity
        dist[s] = 0; // Distance to source is 0
        Arrays.fill(prev, -1); // Previous nodes are initialized to -1
    }

    // Relaxation step for Bellman-Ford algorithm
    static void relax(int u, int v, int w, int[] dist, int[] prev) {
        if (dist[u] != Integer.MAX_VALUE && dist[v] > dist[u] + w) {
            dist[v] = dist[u] + w; // Update distance to vertex v
            prev[v] = u; // Update previous vertex for path reconstruction
        }
    }

    // Combined algorithm for finding MST and shortest paths
    static void combinedAlgorithm(Graph G, int[] w, int s) {
        DisjointSet disjointSet = new DisjointSet(G.V); // Initialize disjoint set for MST
        int[] dist = new int[G.V]; // Array to store shortest distances
        int[] prev = new int[G.V]; // Array to store previous vertices for shortest paths
        List<int[]> mst = new ArrayList<>(); // List to store edges of the MST

        // Step 1: Initialize Single Source
        initializeSingleSource(G, s, dist, prev);

        // Step 2: Bellman-Ford Relaxation
        for (int i = 1; i < G.V; i++) { // Repeat V-1 times
            for (int[] edge : G.edges) {
                relax(edge[0], edge[1], edge[2], dist, prev); // Relax each edge
            }
        }

        // Step 3: Sort edges by weight for Kruskal's algorithm
        G.edges.sort(Comparator.comparingInt(edge -> edge[2]));

        // Step 4: Kruskal's MST Construction
        for (int[] edge : G.edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            if (disjointSet.find(u) != disjointSet.find(v)) { // Check for cycles
                mst.add(edge); // Add edge to MST
                disjointSet.union(u, v); // Union the sets
            }
        }

        // Step 5: Negative Weight Cycle Detection
        for (int[] edge : G.edges) {
            if (dist[edge[1]] > dist[edge[0]] + edge[2]) { // Check if relaxation is possible
                System.out.println("Invalid Graph");
                return; // Exit if a negative cycle is detected
            }
        }

        // Output the constructed MST
        System.out.println("MST constructed:");
        for (int[] edge : mst) {
            System.out.println("Edge: " + (char)('A' + edge[0]) + " - " + (char)('A' + edge[1]) + " Weight: " + edge[2]);
        }

        // Output the calculated shortest paths
        System.out.println("Shortest paths calculated:");
        for (int i = 0; i < G.V; i++) {
            System.out.println("Vertex " + (char)('A' + i) + " Distance: " + dist[i]);
        }
    }

    // Method to find the busiest vertex (the one with the highest degree)
static int busiest(Graph G) {
    int[] degree = new int[G.V]; // Array to store the degree of each vertex
    for (int[] edge : G.edges) {
        degree[edge[0]]++; // Increment degree of start vertex
        degree[edge[1]]++; // Increment degree of end vertex
    }

    // Find the vertex with the highest degree
    int busiestVertex = 0;
    for (int i = 1; i < G.V; i++) {
        if (degree[i] > degree[busiestVertex]) {
            busiestVertex = i; // Update busiest vertex
        }
    }
    
    return busiestVertex; // Return the busiest vertex
}


public static void main(String[] args) {

  
        // Test Case 1: Basic graph
        System.out.println("Test Case 1: Basic Graph");
        Graph G1 = new Graph(5, 6); // 5 vertices (A-E) and 6 edges
        G1.addEdge(0, 1, 5); // A-B
        G1.addEdge(0, 2, 10); // A-C
        G1.addEdge(1, 2, 8); // B-C
        G1.addEdge(2, 3, 4); // C-D
        G1.addEdge(3, 4, 6); // D-E
        G1.addEdge(2, 4, 15); // C-E
        
        // Now call busiest() method on G1
        int busiestVertex = busiest(G1); 
        System.out.println("The busiest vertex is: " + (char)('A' + busiestVertex));
    
        // Call combinedAlgorithm for this graph
        combinedAlgorithm(G1, null, 0);  // Starting vertex is A (0)
    
        // Other test cases (as before)
        // ...
    }
    
}