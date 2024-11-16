import java.io.*;
import java.util.*;

class Graph {
    private int vertices;
    private int edges;
    private List<List<Edge>> adjList; // List of adjacency lists where each entry contains edges with weights

    public Graph(String fname) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fname));
        String line = br.readLine();
        if (line != null) {
            String[] p = line.split(" ");
            vertices = Integer.parseInt(p[0]);
            edges = Integer.parseInt(p[1]);

            adjList = new ArrayList<>();
            for (int i = 0; i <= vertices; i++) {
                adjList.add(new LinkedList<>());
            }

            for (int i = 0; i < edges; i++) {
                line = br.readLine();
                if (line != null) {
                    String[] edge = line.split(" ");
                    int v1 = Integer.parseInt(edge[0]);
                    int v2 = Integer.parseInt(edge[1]);
                    int weight = Integer.parseInt(edge[2]); // Assuming weighted graph for Prim's algorithm
                    addEdge(v1, v2, weight);
                }
            }
        }
        br.close();
    }

    private void addEdge(int v1, int v2, int weight) {
        adjList.get(v1).add(new Edge(v1, v2, weight));
        adjList.get(v2).add(new Edge(v2, v1, weight));
    }

    public List<Edge> primMST() {
        boolean[] inMST = new boolean[vertices + 1];
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        List<Edge> mst = new ArrayList<>();

        // Start from an arbitrary node (node 1 in this case)
        addEdgesToPQ(1, pq, inMST);

        while (!pq.isEmpty() && mst.size() < vertices - 1) {
            Edge edge = pq.poll();

            // If the destination node is already in the MST, skip it
            if (inMST[edge.v2]) {
                continue;
            }

            // Add the edge to the MST
            mst.add(edge);

            // Include this node in the MST
            addEdgesToPQ(edge.v2, pq, inMST);
        }
        return mst;
    }

    private void addEdgesToPQ(int node, PriorityQueue<Edge> pq, boolean[] inMST) {
        inMST[node] = true;
        for (Edge edge : adjList.get(node)) {
            if (!inMST[edge.v2]) {
                pq.offer(edge);
            }
        }
    }

}

class Edge implements Comparable<Edge> {
    int v1, v2, weight;

    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class Prims {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph("input.txt");
        List<Edge> mst = graph.primMST();

        System.out.println("Edges in the Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.v1 + " - " + edge.v2 + " : " + edge.weight);
        }
    }
}
