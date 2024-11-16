import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Graph {
  private int vertices;
  private int edges;
  private List<List<Integer>> adjList;
  Stack<Integer> stack = new Stack<>();

  public Graph(String fname) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader((fname)));
    String line = br.readLine();
    if (line != null) {
      String[] p = line.split(" ");
      vertices = Integer.parseInt(p[0]);
      edges = Integer.parseInt(p[1]);

      adjList = new LinkedList<>();
      for (int i = 0; i <= vertices; i++) {
        adjList.add(new LinkedList<>());
      }

      for (int i = 0; i < edges; i++) {
        line = br.readLine();
        if (line != null) {
          String[] edge = line.split(" ");
          int v1 = Integer.parseInt(edge[0]);
          int v2 = Integer.parseInt(edge[1]);
          addEdge(v1, v2);
        }
      }
    }
    br.close();

  }

  public void addEdge(int u, int v) {
    if (u > vertices || v < 0 || v > vertices) {
      System.out.println("Invalid edge");
      return;
    }
    adjList.get(u).add(v);
  }

  public void addVertex(int n) {
    for (int i = 0; i < n; i++) {
      adjList.add(new LinkedList<>());
    }
    vertices += n;
  }

  public int getNumOfVertices() {
    return vertices;
  }

  public List<Integer> getAdjV(int v) {
    if (v <= 0 || v > vertices) {
      System.out.println("Invalid vertex");
      return null;
    }
    return adjList.get(v);
  }

  public void displayGraph() {
    for (int i = 0; i < vertices; i++) {
      System.out.print(i + " -> ");
      for (int j : adjList.get(i)) {
        System.out.print(j + " ");
      }
      System.out.println();
    }
  }

  public void DFS(int startVertex) {
    int[] color = new int[vertices+1];
    int[] prev = new int[vertices+1];
    int[] d = new int[vertices+1];
    int[] f = new int[vertices+1];
    int time = 0;

    for (int u = 1; u <= vertices; u++) {
      color[u] = 0;
      prev[u] = -1;
    }

    time = DFS_Visit(startVertex, color, prev, d, f, time);
    // for (int u = 1; u <= vertices; u++) {
    //   if (color[u] == 0) {
    //     time = DFS_Visit(u, color, prev, d, f, time);
    //   }
    // }

    System.out.println("\nDFS Discovery and Finish times:");
    for (int i = 1; i <= vertices; i++) {
    System.out.println("Vertex " + i + ": Discovered at " + d[i] + ", Finished at " + f[i]);
    }
  }

  private int DFS_Visit(int u, int[] color, int[] prev, int[] d, int[] f, int time) {
    color[u] = 1;
    time = time + 1;
    d[u] = time;
    System.out.print(u + " ");

    for (int v : adjList.get(u)) {
      if (color[v] == 0) {
        prev[v] = u;
        time = DFS_Visit(v, color, prev, d, f, time);
      }
    }

    color[u] = 2;
    time = time + 1;
    f[u] = time;

    stack.push(u);
    return time;
  }

  public List<Integer> topologicalSort() {
    List<Integer> list = new LinkedList<>();

    while (!stack.isEmpty()) {
      list.add(stack.pop());
    }

    return list;
  }

  public void longestPath(int startVertex) {
    int[] dist = new int[vertices + 1];
    Arrays.fill(dist, Integer.MIN_VALUE);
    dist[startVertex] = 0;

    List<Integer> topoOrder = topologicalSort();

    // Process vertices in topological order
    for (int u : topoOrder) {
      if (dist[u] != Integer.MIN_VALUE) {
        for (int v : adjList.get(u)) {
          if (dist[v] < dist[u] + 1) { // Assuming each edge has a weight of 1
            dist[v] = dist[u] + 1;
          }
        }
      }
    }

    // Print the longest distances from the start vertex
    System.out.println("Longest path distances from vertex " + startVertex + ":");
    for (int i = 1; i <= vertices; i++) {
      if (dist[i] == Integer.MIN_VALUE) {
        System.out.println("Vertex " + i + ": No path");
      } else {
        System.out.println("Vertex " + i + ": " + dist[i]);
      }
    }
  }

}


public class Lab2 {
  public static void main(String[] args) {
    try {
      Graph g = new Graph("input.txt");

      System.out.println("Graph adjacency list:");
      g.displayGraph();

      // System.out.println("\nPerforming DFS starting from vertex:");
      g.DFS(3);

      // System.out.println("\n\nPerforming Topological Sort:");
      // List<Integer> topoOrder = g.topologicalSort();
      // System.out.println("Topological Sort order: " + topoOrder);
      g.longestPath(3);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}