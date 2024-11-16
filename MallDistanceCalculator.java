import java.util.*;

class Main {

    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        // Calculate Manhattan distance between two points
        int distance(Point other) {
            return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Read first mall
            int p1 = scanner.nextInt();
            if (p1 == 0) break; // Exit condition

            List<Point> mall1 = new ArrayList<>();
            for (int i = 0; i < p1; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                mall1.add(new Point(x, y));
            }

            // Read second mall
            int p2 = scanner.nextInt();
            List<Point> mall2 = new ArrayList<>();
            for (int i = 0; i < p2; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                mall2.add(new Point(x, y));
            }

            // Find minimum distance between the two malls
            int minDistance = findMinimumDistance(mall1, mall2);
            System.out.println(minDistance);
        }

        scanner.close();
    }

    // Function to find the minimum Manhattan distance between two sets of points
    private static int findMinimumDistance(List<Point> mall1, List<Point> mall2) {
        int minDistance = Integer.MAX_VALUE;
        for (Point p1 : mall1) {
            for (Point p2 : mall2) {
                int distance = p1.distance(p2);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }
}
