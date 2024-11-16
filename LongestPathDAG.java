import java.util.*;
import java.io.*;

class LongestPathDAG {
    private int n; // number of vertices
    private int startVertex; // starting vertex
    private int longest; // length of the longest path
    private int destiny; // destination vertex with the longest path
    private int[] visited; // to keep track of the length of the path
    private List<List<Integer>> adjacencyList; // adjacency list for the graph

    public LongestPathDAG(int numVertices) {
        this.n = numVertices;
        this.adjacencyList = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            adjacencyList.add(new ArrayList<>());
        }
        this.visited = new int[n + 1];
    }

    // Add a directed edge from vertex u to vertex v
    public void addEdge(int u, int v) {
        adjacencyList.get(u).add(v);
    }

    public void bfs() {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(startVertex, 0));

        Arrays.fill(visited, -1);

        while (!queue.isEmpty()) {
            Pair aux = queue.poll();

            if (aux.second > visited[aux.first]) {
                visited[aux.first] = aux.second;

                if (aux.second > longest) {
                    longest = aux.second;
                    destiny = aux.first;
                } else if (aux.second == longest && aux.first < destiny) {
                    destiny = aux.first;
                }

                for (int i = 0; i < adjacencyList.get(aux.first).size(); i++) {
                    int nextVertex = adjacencyList.get(aux.first).get(i);
                    queue.add(new Pair(nextVertex, aux.second + 1));
                }
            }
        }
    }

    public void solve() {
        // Scanner scanner = new Scanner(new FileInputStream(args[0]));
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;

        while (true) {
            if (!scanner.hasNext()) {
                break;
            }
            n = scanner.nextInt();
            if (n == 0)
                break; // Stop when input is 0

            startVertex = scanner.nextInt();

            for (int i = 1; i <= n; i++) {
                adjacencyList.get(i).clear();
            }

            while (true) {
                int p = scanner.nextInt();
                int q = scanner.nextInt();
                if (p == 0 && q == 0)
                    break;
                addEdge(p, q);
            }

            destiny = startVertex;
            longest = 0;

            bfs();

            System.out.println("Case " + caseNumber + ": The longest path from " + startVertex + " has length "
                    + longest + ", finishing at " + destiny + ".");
            System.out.println();

            caseNumber++;
        }
        scanner.close();
    }

    private static class Pair {
        int first, second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        LongestPathDAG graph = new LongestPathDAG(100);

        graph.solve();

    }
}
