import java.io.*;
import java.util.*;

class Graph {
    private List<List<Integer>> adjacencyList;
    private int numVertices;
    private int numEdges;

    public Graph(String fn) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fn));

        String[] line = br.readLine().split(" ");
        numVertices = Integer.parseInt(line[0]);
        numEdges = Integer.parseInt(line[1]);

        adjacencyList = new ArrayList<>();
        for (int i = 0; i <= numVertices; i++) {
            adjacencyList.add(new ArrayList<>());
        }

        for (int i = 0; i < numEdges; i++) {
            String[] edge = br.readLine().split(" ");
            int u = Integer.parseInt(edge[0]);
            int v = Integer.parseInt(edge[1]);
            addEdge(u, v);
        }

        br.close();
    }

    public void addEdge(int u, int v) {
        if (u <= 0 || v <= 0 || u > numVertices || v > numVertices) {
            System.out.println("Vertex error");
            return;
        }

        adjacencyList.get(u).add(v);
        adjacencyList.get(v).add(u);
    }

    public void addVertex(int n) {
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        numVertices += n;
    }

    public int getNumberOfVertices() {
        return numVertices;
    }

    public List<Integer> getAdjacentVertices(int vertex) {
        if (vertex <= 0 || vertex > numVertices) {
            System.out.println("Vertex not found.");
            return Collections.emptyList();
        }
        return adjacencyList.get(vertex);
    }

    public void displayGraph() {
        for (int i = 1; i <= numVertices; i++) {
            System.out.print(i + " -> ");
            for (int adj : adjacencyList.get(i)) {
                System.out.print(adj + " ");
            }
            System.out.println();
        }
    }

    public void bfs(int startVertex) {
        int[] color = new int[numVertices + 1];
        int[] distance = new int[numVertices + 1];
        int[] previous = new int[numVertices + 1];
        Queue<Integer> queue = new LinkedList<>();

        for (int u = 1; u <= numVertices; u++) {
            color[u] = 0; // WHITE
            distance[u] = Integer.MAX_VALUE; // Infinity
            previous[u] = -1; // NIL
        }

        color[startVertex] = 1; // GRAY
        distance[startVertex] = 0;
        previous[startVertex] = -1;
        queue.add(startVertex);

        System.out.println("BFS Order:");

        // Process the queue until empty
        while (!queue.isEmpty()) {
            int u = queue.poll(); // Dequeue
            System.out.print(u + " ");

            // Explore all adjacent vertices
            for (int v : adjacencyList.get(u)) {
                if (color[v] == 0) { // If the vertex is WHITE (unvisited)
                    color[v] = 1; // Mark it as GRAY (visited but not fully explored)
                    distance[v] = distance[u] + 1; // Set distance
                    previous[v] = u; // Set predecessor
                    queue.add(v); // Enqueue the vertex for further exploration
                }
            }
            color[u] = 2; // Mark vertex as BLACK (fully explored)
        }
        System.out.println();

        System.out.println("Distances from start vertex:");
        for (int i = 1; i <= numVertices; i++) {
            
            System.out.println("Vertex " + i + ": Distance = " + ((distance[i]!=Integer.MAX_VALUE)? distance[i]:"infinity"));
        }

        System.out.println("\nShortest paths from vertex " + startVertex + ":");
        for (int v = 1; v < adjacencyList.size(); v++) { // Iterate through all vertices
            if (v != startVertex) {
                System.out.print("Path to vertex " + v + ": ");
                printPath(startVertex, v, previous); // Print the shortest path
                System.out.println();
            }
        }
    }

    private void printPath(int startVertex, int v, int previous[]) {
        if (v == startVertex) {
            System.out.print(startVertex); // Base case: if we reached the start vertex, print it
        } else if (previous[v] == -1) {
            System.out.print("No path"); // No path if the predecessor is NIL (-1)
        } else {
            printPath(startVertex, previous[v], previous); // Recursively print the path to the predecessor
            System.out.print(" -> " + v); // Print the current vertex after the recursive call
        }
    }

}

public class Lab1 {
    public static void main(String[] args) {
        try {
            Graph g = new Graph("input.txt");
            g.bfs(3);
            g.displayGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}