
import java.util.*;

public class KruskalMinimumSpanningTree {
    static class Edge {
        String source, dest;
        int weight;

        public Edge(String source, String dest, int weight) {
            this.source = source;
            this.dest = dest;
            this.weight = weight;
        }
    }

    static class UnionFind {
        Map<String, String> parent = new HashMap<>();

        public void makeSet(String vertex) {
            parent.put(vertex, vertex);
        }

        public String find(String vertex) {
            if (!parent.get(vertex).equals(vertex)) {
                parent.put(vertex, find(parent.get(vertex))); // Path compression
            }
            return parent.get(vertex);
        }

        public boolean union(String v1, String v2) {
            String root1 = find(v1);
            String root2 = find(v2);

            if (!root1.equals(root2)) {
                parent.put(root1, root2);
                return true;
            }
            return false;
        }
    }

    public void kruskal(List<Edge> edges, Set<String> vertices) {
        UnionFind uf = new UnionFind();
        for (String vertex : vertices) {
            uf.makeSet(vertex);
        }

        edges.sort(Comparator.comparingInt(e -> e.weight));
        List<Edge> mst = new ArrayList<>();

        for (Edge edge : edges) {
            if (uf.union(edge.source, edge.dest)) {
                mst.add(edge);
            }
        }

        System.out.println("Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.source + " - " + edge.dest + " (" + edge.weight + ")");
        }
    }

    public static void example() {
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge("A", "B", 5));
        edges.add(new Edge("A", "C", 10));
        edges.add(new Edge("B", "C", 2));
        edges.add(new Edge("B", "D", 8));
        edges.add(new Edge("C", "D", 4));
        edges.add(new Edge("C", "E", 15));
        edges.add(new Edge("D", "E", 6));
        
       
        Set<String> vertices = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));

        KruskalMinimumSpanningTree kruskal = new KruskalMinimumSpanningTree();
        kruskal.kruskal(edges, vertices);
    }

    public static void main(String[] args) {
        example();
    }
}


