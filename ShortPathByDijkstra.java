

import java.util.*;
class NodeDsPre{
	String pre;//predecessor
	int ds;//distance
	
	public NodeDsPre(String pre,int ds) {
		this.ds=ds;
		this.pre=pre;
	}
}

class Node {
	String vertex;
	String pre;
	int ds;
	int w;//weight
	
	public Node(String vertex,int w){
		this.vertex=vertex;
		this.w=w;
		this.ds=Integer.MAX_VALUE;
		this.pre=null;
	}
}
class NodeComparator implements Comparator<Node>{
	public int compare(Node n1,Node n2) {
		return Integer.compare(n1.ds, n2.ds);
	}
}
class Graph2 {
	Map<String,ArrayList<Node>>adj=new HashMap<>();
	Map<String,NodeDsPre>dsPred=new HashMap<>();
	
	public void PrintNodes() {
		for(String key: dsPred.keySet()) {
			NodeDsPre info=dsPred.get(key);
			System.out.println("Vertex "+key);
			System.out.println("Distance: "+info.ds);
			System.out.println("Predecessor: "+info.pre);
			System.out.println("======================");

		}
	}
	public void PrintNeighbours(String Vertex) {
		ArrayList<Node>n=adj.get(Vertex);//n is short for neighbours
			if(n!=null) {
				for(Node neighbour:n) {
					System.out.println(neighbour.vertex+"(Weight: "+neighbour.w+")");
				}
			}else {
				System.out.println("There is no neighbours for the"+ Vertex +" vertex");
			}
	}
	
	public void addEdge(String s,String d,int w) {
		adj.putIfAbsent(s, new ArrayList<>());
		adj.putIfAbsent(d, new ArrayList<>());
		adj.get(s).add(new Node(d,w));
	}
}
 public class ShortPathByDijkstra{
	private final Graph2 graph;
	private final PriorityQueue<Node>pq;
	public ShortPathByDijkstra(Graph2 graph) {
		this.graph=graph;
		this.pq=new PriorityQueue<>(new NodeComparator());
	}
	
	private void initializeSingleSource(String src) {
		for(String key:graph.adj.keySet()) {
			graph.dsPred.put(key, new NodeDsPre(null,Integer.MAX_VALUE));
		}
		graph.dsPred.put(src, new NodeDsPre(null,0));
		Node sNode=new Node(src,0);
		sNode.ds=0;
		pq.add(sNode);
	}
	private void relax(Node u,Node v) {
		int newDs=u.ds+v.w;
		if(newDs<graph.dsPred.get(v.vertex).ds) {
			pq.remove(v);
			v.ds=newDs;
			pq.add(v);
			graph.dsPred.replace(v.vertex, new NodeDsPre(u.vertex,newDs));
		}
	}
	public int dijkstra(String src,String target) {
		initializeSingleSource(src);
		while(!pq.isEmpty()) {
			Node u=pq.poll();
			if(graph.adj.get(u.vertex)!=null) {
				for(Node v: graph.adj.get(u.vertex)) {
					relax(u,v);
				}
			}
		}
		return graph.dsPred.get(target).ds;
	}
	public static void printShortestPath(String src,String target,Graph2 graph) {
		if(target==null) {
			System.out.println("The path doesn't exist.");
			return;
		}
		if(src.equals(target)) {
			System.out.print(src);
			return;
		}
		printShortestPath(src,graph.dsPred.get(target).pre,graph);
		System.out.print("->"+target);
	}
	public static void example1() {
		Graph2 graph =new Graph2();
		graph.addEdge("A", "B", 4);
		graph.addEdge("A", "C", 2);
		graph.addEdge("B", "D", 5);
		graph.addEdge("C", "D", 1);
		graph.addEdge("D", "E", 3);
		
		ShortPathByDijkstra sp=new ShortPathByDijkstra(graph);
		String s="A";
		String d="E";
		int shortest=sp.dijkstra(s, d);
		System.out.println("shortest path distance from "+ s+" to "+ d+" : "+ shortest);
	
		System.out.println("Path: ");
		printShortestPath(s,d,graph);
		System.out.println();
	}
	public static void example2() {
		Graph2 graph =new Graph2();
		graph.addEdge("A", "B", 5);
		graph.addEdge("A", "C", 10);
		graph.addEdge("B", "C", 2);
		graph.addEdge("B", "D", 8);
		graph.addEdge("C", "D", 4);
		graph.addEdge("C", "E", 15);
		graph.addEdge("D", "E",6);
		
		ShortPathByDijkstra sp=new ShortPathByDijkstra(graph);
		String s="A";
		String d="E";
		int shortest=sp.dijkstra(s, d);
		System.out.println("shortest path distance from "+ s+" to "+ d+" : "+ shortest);
	
		System.out.println("Path: ");
		printShortestPath(s,d,graph);
		System.out.println();
	}
	public static void main(String[] args) {
		example1();
		example2();
	}
}