import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Graph {
  private int vertices;
  private int edges;
  private List<List<Integer>> adjList;
  List<List<Integer>> transposedAdjList;
  Stack<Integer> stack = new Stack<>();

  public Graph() {
    Scanner sc = new Scanner(System.in);
    String line = sc.nextLine();
    if (line != null) {
      String[] parts = line.split(" ");
      vertices = Integer.parseInt(parts[0]);
      edges = Integer.parseInt(parts[1]);

      adjList = new ArrayList<>();
      for (int i = 0; i <= vertices; i++) {
        adjList.add(new LinkedList<>());
      }

      for (int i = 0; i < edges; i++) {
        line = sc.nextLine();
        if (line != null) {
          String[] edge = line.split(" ");
          int u = Integer.parseInt(edge[0]);
          int v = Integer.parseInt(edge[1]);
          addEdge(u, v);
        }
      }
    }
    sc.close();
  }

  public void addEdge(int u, int v) {
    if (u >= vertices || v < 0 || v >= vertices) {
      System.out.println("Invalid edge");
      return;
    }
    adjList.get(u).add(v);
    // adjList.get(v).add(u);
  }

  private void transposeGraph() {
    transposedAdjList = new ArrayList<>();
    for (int i = 0; i < vertices; i++) {
      transposedAdjList.add(new LinkedList<>());
    }

    for (int i = 0; i < vertices; i++) {
      for (int v : adjList.get(i)) {
        transposedAdjList.get(v).add(i);
      }
    }
  }

  public void DFS(int startVertex) {
    int[] color = new int[vertices];
    int[] prev = new int[vertices];
    int[] d = new int[vertices];
    int[] f = new int[vertices];
    int time = 0;

    for (int u = 0; u < vertices; u++) {
      color[u] = 0;
      prev[u] = -1;
    }

    for (int u = 0; u < vertices; u++) {
      if (color[u] == 0) {
        time = DFS_Visit(u, color, prev, d, f, time);
      }
    }
  }

  private int DFS_Visit(int u, int[] color, int[] prev, int[] d, int[] f, int time) {
    color[u] = 1;
    time = time + 1;
    d[u] = time;

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

  public void findSCCs() {
    // Step 1: Perform DFS to get the finish times
    DFS(0);

    // Step 2: Transpose the graph
    transposeGraph();

    // Step 3: Perform DFS on the transposed graph in decreasing order of finish times
    int[] color = new int[vertices];
    for (int i = 0; i < vertices; i++) {
      color[i] = 0;
    }

    while (!stack.isEmpty()) {
      int v = stack.pop();
      if (color[v] == 0) {
        System.out.print("SCC: ");
        DFSUtil(v, color, transposedAdjList);
        System.out.println();
      }
    }
  }

  private void DFSUtil(int v, int[] color, List<List<Integer>> adjList) {
    color[v] = 1;
    System.out.print(v + " ");
    for (int u : adjList.get(v)) {
      if (color[u] == 0) {
        DFSUtil(u, color, adjList);
      }
    }
  }
}

public class Scc1 {
  public static void main(String[] args) {
      Graph g = new Graph();
      g.findSCCs();

  }
}
