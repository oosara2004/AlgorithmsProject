import java.util.*;

public class BruteForceShortestPath {
    static final int INF = 99999;
    static Map<Character, List<Edge>> graph = new HashMap<>();
    static char[] nodes = {'A', 'B', 'C', 'D', 'E'};

    public static void main(String[] args) {
        // Define the graph as an adjacency list
        addEdge('A', 'B', 5);
        addEdge('A', 'C', 10);
        addEdge('B', 'C', 2);
        addEdge('B', 'D', 8);
        addEdge('C', 'D', 4);
        addEdge('C', 'E', 15);
        addEdge('D', 'E', 6);

        // Find the shortest path between all pairs using brute force
        for (char start : nodes) {
            for (char end : nodes) {
                if (start != end) {
                    int shortest = findShortestPath(start, end, new HashSet<>(), 0);
                    System.out.println("Shortest path from " + start + " to " + end + " is: " + (shortest == INF ? "No Path" : shortest) + " minutes");
                }
            }
        }
    }

    static void addEdge(char from, char to, int weight) {
        graph.putIfAbsent(from, new ArrayList<>());
        graph.get(from).add(new Edge(to, weight));
    }

    static int findShortestPath(char current, char target, Set<Character> visited, int cost) {
        if (current == target) return cost;
        visited.add(current);
        int minCost = INF;

        for (Edge edge : graph.getOrDefault(current, Collections.emptyList())) {
            if (!visited.contains(edge.to)) {
                minCost = Math.min(minCost, findShortestPath(edge.to, target, new HashSet<>(visited), cost + edge.weight));
            }
        }

        return minCost;
    }

    static class Edge {
        char to;
        int weight;
        Edge(char to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}