import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Pair {
    int u;
    int v;

    Pair(int u, int v) {
        this.u = u;
        this.v = v;
    }
}

class Graph {
    private int vertices;
    private int edges;
    private List<List<Integer>> adjList;
    Stack<Integer> stack = new Stack<>();
    private List<Integer> articulationPoints = new ArrayList<>();
    private List<Pair> bridges = new ArrayList<>();

    public Graph(String fname) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader((fname)));
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
                    addEdge(v1, v2);
                }
            }
        }
        br.close();
    }

    public void addEdge(int u, int v) {
        if (u > vertices || v <= 0 || v > vertices || u <= 0) {
            System.out.println("Invalid edge");
            return;
        }
        adjList.get(u).add(v);
        adjList.get(v).add(u);
    }


    public void DFS() {
        int[] color = new int[vertices+1];
        int[] prev = new int[vertices+1];
        int[] d = new int[vertices+1];
        int[] f = new int[vertices+1];
        int[] low = new int[vertices+1];
        int time = 0;

        for (int u = 0; u <= vertices; u++) {
            color[u] = 0;
            prev[u] = -1;
        }

        for (int u = 1; u <= vertices; u++) {
            if (color[u] == 0) {
                time = DFS_Visit(u, color, prev, d, f, time, low);
            }
        }
    }

    private int DFS_Visit(int u, int[] color, int[] prev, int[] d, int[] f, int time, int[] low) {
        color[u] = 1;
        time = time + 1;
        d[u] = time;
        // System.out.print(u+" ");
        low[u] = d[u];
        boolean isArticulationPoint = false;

        for (int v : adjList.get(u)) {
            if (color[v] == 0) {
                prev[v] = u;
                time = DFS_Visit(v, color, prev, d, f, time, low);
                low[u] = Math.min(low[u], low[v]);

                if(low[v] > d[u]) {
                    System.out.println("reached");
                    bridges.add(new Pair(u, v));
                }
                if(prev[u] == -1 && adjList.get(u).size() > 1) {
                    isArticulationPoint = true;
                }
                if(prev[u] != -1 && low[v] >= d[u]) {
                    isArticulationPoint = true;
                }
            }
            else if (v != prev[u]) {
                low[u] = Math.min(low[u], d[v]);
            }
        }

        if (isArticulationPoint) {
            articulationPoints.add(u);
        }

        color[u] = 2;
        time = time + 1;
        f[u] = time;
        stack.push(u);
        return time;
    }

    public void getArticulationPoints() {
        System.out.println("Articulation Points:");
        for (int i : articulationPoints) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public void getBridges() {
        System.out.println("Bridges:");
        for (Pair p : bridges) {
            System.out.println(p.u + " " + p.v);
        }
    }

}


public class ArticulationPoints {
    public static void main(String[] args) throws IOException {
        Graph g = new Graph("input.txt");
        g.DFS();
        g.getArticulationPoints();
        g.getBridges();
    }
}