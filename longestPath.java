import java.util.*;

public class longestPath {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int caseNum = 1;

        while (true) {
            int n = sc.nextInt();
            if (n == 0) {
                break;
            }

            int s = sc.nextInt();
            List<List<Integer>> graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
            }

            while (true) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                if (u == 0 && v == 0) {
                    break;
                }
                graph.get(u - 1).add(v - 1);
            }

            int[] dist = new int[n];
            int[] prev = new int[n];
            Arrays.fill(dist, Integer.MIN_VALUE);
            dist[s - 1] = 0;

            PriorityQueue<Pair> pq = new PriorityQueue<>();
            pq.offer(new Pair(s - 1, 0));

            while (!pq.isEmpty()) {
                Pair p = pq.poll();
                int u = p.first;
                int d = p.second;

                if (d > dist[u]) {
                    continue;
                }

                for (int v : graph.get(u)) {
                    int newDist = d + 1;
                    if (newDist > dist[v]) {
                        dist[v] = newDist;
                        prev[v] = u;
                        pq.offer(new Pair(v, newDist));
                    }
                }
            }

            int maxDist = Integer.MIN_VALUE;
            int maxDistNode = -1;
            for (int i = 0; i < n; i++) {
                if (dist[i] > maxDist) {
                    maxDist = dist[i];
                    maxDistNode = i;
                }
            }

            int finalNode = maxDistNode;
            List<Integer> path = new ArrayList<>();
            while (finalNode != -1) {
                path.add(finalNode + 1);
                finalNode = prev[finalNode];
            }
            Collections.reverse(path);

            System.out.println("Case " + (caseNum++) + ": The longest path from " + s + " has length " + maxDist + ", finishing at " + path.get(path.size() - 1));
        }
    }

    static class Pair implements Comparable<Pair> {
        int first, second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair other) {
            return other.second - this.second;
        }
    }
}