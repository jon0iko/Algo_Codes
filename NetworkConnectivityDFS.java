import java.util.*;

public class NetworkConnectivityDFS {
    
    // Helper class to store graph details and perform DFS
    static class Graph {
        private List<List<Integer>> adjList;
        private boolean[] visited;

        public Graph(int n) {
            adjList = new ArrayList<>();
            visited = new boolean[n];
            for (int i = 0; i < n; i++) {
                adjList.add(new ArrayList<>());
            }
        }

        public void addEdge(int u, int v) {
            adjList.get(u).add(v);
            adjList.get(v).add(u);
        }

        public int dfs(int node) {
            Stack<Integer> stack = new Stack<>();
            stack.push(node);
            visited[node] = true;
            int size = 0;

            while (!stack.isEmpty()) {
                int current = stack.pop();
                size++;
                for (int neighbor : adjList.get(current)) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack.push(neighbor);
                    }
                }
            }
            return size;
        }

        public List<Integer> getComponentSizes() {
            List<Integer> componentSizes = new ArrayList<>();
            for (int i = 0; i < adjList.size(); i++) {
                if (!visited[i]) {
                    int componentSize = dfs(i);
                    componentSizes.add(componentSize);
                }
            }
            return componentSizes;
        }
    }

    public static int largestNetwork(int n, int m, int k, int[][] connections) {
        // Create a graph from the given connections
        Graph graph = new Graph(n);

        // Add edges based on the connections
        for (int[] conn : connections) {
            int u = conn[0] - 1; // convert to zero-indexed
            int v = conn[1] - 1; // convert to zero-indexed
            graph.addEdge(u, v);
        }

        // Get the sizes of all connected components
        List<Integer> componentSizes = graph.getComponentSizes();

        // Sort the component sizes in descending order
        Collections.sort(componentSizes, Collections.reverseOrder());

        // Merge the largest components by using K wires
        int largestNetwork = componentSizes.get(0);
        for (int i = 1; i <= k && i < componentSizes.size(); i++) {
            largestNetwork += componentSizes.get(i);
        }

        return largestNetwork;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(); // Number of computers
        int m = scanner.nextInt(); // Number of initial connections
        int k = scanner.nextInt(); // Number of additional wires

        int[][] connections = new int[m][2];
        for (int i = 0; i < m; i++) {
            connections[i][0] = scanner.nextInt();
            connections[i][1] = scanner.nextInt();
        }

        int result = largestNetwork(n, m, k, connections);
        System.out.println(result);

        scanner.close();
    }
}
