import java.util.*;

public class AssassinProblem {
    static int n, m;
    static List<List<Integer>> graph, reverseGraph;
    static boolean[] visited;
    static Stack<Integer> stack;
    static List<List<Integer>> sccComponents;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int T = sc.nextInt();
        for (int t = 1; t <= T; t++) {
            // Reading input
            n = sc.nextInt();
            m = sc.nextInt();
            graph = new ArrayList<>();
            reverseGraph = new ArrayList<>();
            for (int i = 0; i <= n; i++) {
                graph.add(new ArrayList<>());
                reverseGraph.add(new ArrayList<>());
            }

            for (int i = 0; i < m; i++) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                graph.get(u).add(v);
                reverseGraph.get(v).add(u);
            }

            // Step 1: Perform DFS to get the finishing order
            visited = new boolean[n + 1];
            stack = new Stack<>();
            for (int i = 1; i <= n; i++) {
                if (!visited[i]) {
                    dfs(i);
                }
            }

            // Step 2: Reverse the graph is already created (reverseGraph)

            // Step 3: Perform DFS on reversed graph based on the stack order
            visited = new boolean[n + 1];
            sccComponents = new ArrayList<>();
            while (!stack.isEmpty()) {
                int node = stack.pop();
                if (!visited[node]) {
                    List<Integer> scc = new ArrayList<>();
                    reverseDfs(node, scc);
                    sccComponents.add(scc);
                }
            }

            // Step 4: Calculate the minimum number of assassins required
            int[] inDegree = new int[sccComponents.size()];

            // Create a mapping from node to its SCC component index
            int[] componentIndex = new int[n + 1];
            for (int i = 0; i < sccComponents.size(); i++) {
                for (int node : sccComponents.get(i)) {
                    componentIndex[node] = i;
                }
            }

            // Calculate in-degrees of each component in the original graph
            for (int u = 1; u <= n; u++) {
                for (int v : graph.get(u)) {
                    if (componentIndex[u] != componentIndex[v]) {
                        inDegree[componentIndex[v]]++;
                    }
                }
            }

            // Count the number of components with zero in-degrees
            int minAssassins = 0;
            for (int in : inDegree) {
                if (in == 0) {
                    minAssassins++;
                }
            }

            // Output the result for this test case
            System.out.println("Case " + t + ": " + minAssassins);
        }
        sc.close();
    }

    // Normal DFS to fill the stack
    private static void dfs(int node) {
        visited[node] = true;
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                dfs(neighbor);
            }
        }
        stack.push(node);
    }

    // Reverse DFS to collect SCCs
    private static void reverseDfs(int node, List<Integer> scc) {
        visited[node] = true;
        scc.add(node);
        for (int neighbor : reverseGraph.get(node)) {
            if (!visited[neighbor]) {
                reverseDfs(neighbor, scc);
            }
        }
    }
}
