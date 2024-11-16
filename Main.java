import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] grid = new int[2001][2001];
        int[][] moves = new int[2001][2001];
        int[][] directions = { {-1, 0}, {0, 1}, {1, 0}, {0, -1} };

        while (true) {
            int n = scanner.nextInt();
            if (n == 0) break;

            // Reset grid and moves arrays
            for (int i = 0; i < 2001; i++) {
                Arrays.fill(grid[i], 0);
                Arrays.fill(moves[i], -1);
            }

            Queue<int[]> queue = new LinkedList<>();
            
            // Read the coordinates for the first mall and mark them
            for (int i = 0; i < n; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                grid[x][y] = 1; // Mark as part of the first mall
                moves[x][y] = 0; // Distance from the first mall itself is 0
                queue.offer(new int[] {x, y});
            }

            int m = scanner.nextInt();
            
            // Read the coordinates for the second mall and mark them
            for (int i = 0; i < m; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                grid[x][y] = 2; // Mark as part of the second mall
            }

            int distance = -1;

            // BFS to find the shortest distance
            while (!queue.isEmpty()) {
                int[] current = queue.poll();
                int currentX = current[0];
                int currentY = current[1];

                // Check if the current position is part of the second mall
                if (grid[currentX][currentY] == 2) {
                    distance = moves[currentX][currentY];
                    break;
                }

                // Explore the 4 possible movements (up, right, down, left)
                for (int[] direction : directions) {
                    int newX = currentX + direction[0];
                    int newY = currentY + direction[1];

                    // Check boundaries and if the cell is already visited
                    if (newX >= 0 && newX < 2001 && newY >= 0 && newY < 2001 && moves[newX][newY] == -1) {
                        queue.offer(new int[] {newX, newY});
                        moves[newX][newY] = moves[currentX][currentY] + 1;
                    }
                }
            }

            // Output the shortest distance found
            System.out.println(distance);
        }

        scanner.close();
    }
}
