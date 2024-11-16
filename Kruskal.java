import java.io.*;
import java.util.*;

class Graph {
    private int vertices;
    private int edges;
    private List<List<Integer>> adjList;
    private List<Edge> edgeList = new ArrayList<>();

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
                    int weight = Integer.parseInt(edge[2]); // Assuming weighted graph for Kruskal
                    addEdge(v1, v2, weight);
                }
            }
        }
        br.close();
    }

    private void addEdge(int v1, int v2, int weight) {
        adjList.get(v1).add(v2);
        adjList.get(v2).add(v1);
        edgeList.add(new Edge(v1, v2, weight));
    }

    public List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        Collections.sort(edgeList); // Sort edges by weight

        UnionFind uf = new UnionFind(vertices);

        for (Edge edge : edgeList) {
            int root1 = uf.find(edge.v1);
            int root2 = uf.find(edge.v2);

            if (root1 != root2) {
                mst.add(edge);
                uf.union(root1, root2);
            }
        }
        return mst;
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

class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int size) {
        parent = new int[size + 1];
        rank = new int[size + 1];
        for (int i = 0; i <= size; i++) {
            parent[i] = i;
            rank[i] = 0;
        }
    }

    public int find(int p) {
        if (p != parent[p]) {
            parent[p] = find(parent[p]); // Path compression
        }
        return parent[p];
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            if (rank[rootP] > rank[rootQ]) {
                parent[rootQ] = rootP;
            } else if (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ;
            } else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
        }
    }
}

public class Kruskal {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph("input.txt");
        List<Edge> mst = g.kruskalMST();

        System.out.println("Edges in the Minimum Spanning Tree:");
        for (Edge edge : mst) {
            System.out.println(edge.v1 + " - " + edge.v2 + " : " + edge.weight);
        }
    }
}