import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphVertexSelection {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int t = scanner.nextInt(); // Number of queries

        for (int query = 0; query < t; query++) {
            int n = scanner.nextInt(); // Number of vertices
            int m = scanner.nextInt(); // Number of edges

            // Read the edges (not used for the selection logic in this approach)
            for (int i = 0; i < m; i++) {
                scanner.nextInt(); // v
                scanner.nextInt(); // u
            }

            // Choose at most ⌊n/2⌋ vertices
            int k = n / 2; // maximum number of vertices we can choose
            List<Integer> chosenVertices = new ArrayList<>();

            // We will choose vertices 1, 3, 5, ..., (up to n)
            for (int i = 1; i <= n; i += 2) {
                chosenVertices.add(i);
                if (chosenVertices.size() == k) {
                    break; // Stop if we've selected enough vertices
                }
            }

            // Output the result for this query
            System.out.println(chosenVertices.size());
            for (int vertex : chosenVertices) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}
