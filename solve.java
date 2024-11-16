import java.util.*;

class Graph {
  private int vertices;
  private int edges;
  private List<List<Integer>> adjList;
  private List<List<Integer>> TransposedAdjList;
  Stack<Integer> st = new Stack<>();
  private int evenComponentSum = 0;
  private int oddComponentSum = 0;

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
    if (u > vertices || v <= 0 || v > vertices || u <= 0) {
      System.out.println("Invalid edge");
      return;
    }
    adjList.get(u).add(v);
    // adjList.get(v).add(u);
  }

  private void transposeGraph() {
    TransposedAdjList = new ArrayList<>();
    for (int i = 0; i <= vertices; i++) {
      TransposedAdjList.add(new LinkedList<>());
    }
    for (int i = 1; i <= vertices; i++) {
      for (int v : adjList.get(i)) {
        TransposedAdjList.get(v).add(i);
      }
    }
  }

  public void DFS(int src) {
    int[] color = new int[vertices+1];
    int[] prev = new int[vertices+1];
    int[] d = new int[vertices+1];
    int[] f = new int[vertices+1];
    int time = 0;

    for (int i = 1; i <= vertices; i++) {
      color[i] = 0;
      prev[i] = -1;
    }

    // time = DFSVisit(src, color, prev, d, f, time);
    for (int u = 1; u <= vertices; u++) {
      if (color[u] == 0) {
        DFSVisit(u, color, prev, d, f, time);
      }
    }

    // System.out.println("\nDFS Discovery and Finish times:");
    // for (int i = 0; i < vertices; i++) {
    // System.out.println("Vertex " + i + ": Discovered at " + d[i] + ", Finished at
    // " + f[i]);
    // }
  }

  private int DFSVisit(int u, int[] color, int[] prev, int[] d, int[] f, int time) {
    color[u] = 1;
    time = time + 1;
    d[u] = time;
    // System.out.print(u+" ");

    for (int v : adjList.get(u)) {
      if (color[v] == 0) {
        prev[v] = u;
        time = DFSVisit(v, color, prev, d, f, time);
      }
    }
    color[u] = 2;
    time = time + 1;
    f[u] = time;
    st.push(u);
    return time;
  }

  public void findSCCs() {
    int count = 0;
    // dfs on the og graph to get finishing times
    DFS(1);

    // transpose the graph
    transposeGraph();

    // dfs on the transposed graph in decreasing order of finishing times
    int[] color = new int[vertices+1];
    for (int i = 1; i <= vertices; i++) {
      color[i] = 0;
    }

    while (!st.isEmpty()) {
      int v = st.pop();
      if (color[v] == 0) {
        // System.out.println("SCC:");
        count = DFSUtill(v, color, TransposedAdjList, count);
        // System.out.println();
        if (count % 2 == 0) {
          evenComponentSum += count;
        } else {
          oddComponentSum += count;
        }
        count = 0;
      }
    }

  }

  private int DFSUtill(int v, int[] color, List<List<Integer>> adjList, int count) {
    color[v] = 1;
    // System.out.print(v + " ");
    count++;
    for (int u : adjList.get(v)) {
      if (color[u] == 0) {
        count = DFSUtill(u, color, adjList, count);
      }
    }
    // color[v] = 2;
    return count;
  }

  public int getSolve() {
    // System.out.println(evenComponentSum+" "+oddComponentSum);
    return oddComponentSum - evenComponentSum;
  }

}

public class solve {
  public static void main(String[] args) {

    Graph g = new Graph();
    g.findSCCs();
    System.out.println(g.getSolve());
  }
}