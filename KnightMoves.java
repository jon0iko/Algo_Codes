import java.util.*;

public class KnightMoves {
    
    private static final int[][] moves = {
        {2, 1}, {1, 2}, {-1, 2}, {-2, 1},
        {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] squares = line.split(" ");
            if (squares.length != 2) continue;

            String start = squares[0];
            String end = squares[1];

            int startX = start.charAt(0) - 'a'; // Convert 'a'-'h' to 0-7
            int startY = start.charAt(1) - '1'; // Convert '1'-'8' to 0-7
            int endX = end.charAt(0) - 'a'; // Convert 'a'-'h' to 0-7
            int endY = end.charAt(1) - '1'; // Convert '1'-'8' to 0-7

            int movesCount = findKnightMoves(startX, startY, endX, endY);
            System.out.printf("To get from %s to %s takes %d knight moves.%n", start, end, movesCount);
        }
        scanner.close();
    }

    private static int findKnightMoves(int startX, int startY, int endX, int endY) {
        // BFS setup
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[8][8];
        queue.add(new int[]{startX, startY, 0}); // x, y, distance
        visited[startX][startY] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int distance = current[2];

            // If reached the target
            if (x == endX && y == endY) {
                return distance;
            }

            // Explore all knight moves
            for (int[] move : moves) {
                int newX = x + move[0];
                int newY = y + move[1];

                // Check if the new position is within bounds and not visited
                if (isValid(newX, newY, visited)) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY, distance + 1});
                }
            }
        }
        return -1; // This should never happen as knight can always reach any square
    }

    private static boolean isValid(int x, int y, boolean[][] visited) {
        return x >= 0 && x < 8 && y >= 0 && y < 8 && !visited[x][y];
    }
}