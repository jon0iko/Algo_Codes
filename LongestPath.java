import java.util.*;

public class LongestPath {
    static class Graph {
        int vertices;
        List<List<Integer>> adj;
        int[] dist;
        boolean[] visited;

        Graph(int v) {
            this.vertices = v;
            adj = new ArrayList<>(v + 1);
            for (int i = 0; i <= v; i++) {
                adj.add(new ArrayList<>());
            }
            dist = new int[v + 1];
            visited = new boolean[v + 1];
        }

        void addEdge(int from, int to) {
            adj.get(from).add(to);
        }

        Pair<Integer, Integer> findLongestPath(int start) {
            Arrays.fill(dist, 0);
            Arrays.fill(visited, false);

            dfs(start);

            int maxDist = 0;
            int endpoint = start;

            for (int i = 1; i <= vertices; i++) {
                if (dist[i] > maxDist) {
                    maxDist = dist[i];
                    endpoint = i;
                } else if (dist[i] == maxDist && i < endpoint) {
                    endpoint = i;
                }
            }

            return new Pair<>(maxDist, endpoint);
        }

        private int dfs(int node) {
            if (visited[node]) {
                return dist[node];
            }

            visited[node] = true;

            if (adj.get(node).isEmpty()) {
                dist[node] = 0;
                return 0;
            }

            int maxDist = 0;
            for (int neighbor : adj.get(node)) {
                maxDist = Math.max(maxDist, dfs(neighbor) + 1);
            }

            dist[node] = maxDist;
            return maxDist;
        }
    }

    static class Pair<T, U> {
        T first;
        U second;

        Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int caseNumber = 1;

        while (true) {
            int n = scanner.nextInt();
            if (n == 0) break;

            int start = scanner.nextInt();
            Graph graph = new Graph(n);

            while (true) {
                int from = scanner.nextInt();
                int to = scanner.nextInt();
                if (from == 0 && to == 0) break;

                graph.addEdge(from, to);
            }

            Pair<Integer, Integer> result = graph.findLongestPath(start);
            System.out.printf("Case %d: The longest path from %d has length %d, finishing at %d.\n",
                    caseNumber++, start, result.first, result.second);
        }

        scanner.close();
    }
}