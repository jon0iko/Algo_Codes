import java.io.*;
import java.util.*;

class Graph {
    private int vertices;
    private int edges;
    private List<List<Edge>> adjList; // Adjacency list to store edges with weights

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
                    int weight = Integer.parseInt(edge[2]);
                    addEdge(v1, v2, weight);
                }
            }
        }
        br.close();
    }

    private void addEdge(int v1, int v2, int weight) {
        adjList.get(v1).add(new Edge(v1, v2, weight));
        adjList.get(v2).add(new Edge(v2, v1, weight)); // Assuming undirected graph
    }

    public int[] dijkstra(int start) {
        int[] distances = new int[vertices + 1];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));
        pq.add(new Node(start, 0));

        boolean[] visited = new boolean[vertices + 1];

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            int u = currentNode.vertex;

            if (visited[u])
                continue;
            visited[u] = true;

            for (Edge edge : adjList.get(u)) {
                int v = edge.v2;
                int weight = edge.weight;

                if (!visited[v] && distances[u] + weight < distances[v]) {
                    distances[v] = distances[u] + weight;
                    pq.add(new Node(v, distances[v]));
                }
            }
        }

        return distances;
    }
}

class Edge {
    int v1, v2, weight;

    public Edge(int v1, int v2, int weight) {
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
}

class Node {
    int vertex, distance;

    public Node(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }
}

public class Djikstra {
    public static void main(String[] args) throws IOException {
        Graph graph = new Graph("input.txt");
        int startNode = 1; // Assuming we start from node 1
        int[] distances = graph.dijkstra(startNode);

        System.out.println("Shortest distances from node " + startNode + ":");
        for (int i = 1; i < distances.length; i++) {
            System.out.println("Node " + i + " : " + (distances[i] == Integer.MAX_VALUE ? "Infinity" : distances[i]));
        }
    }
    
}
