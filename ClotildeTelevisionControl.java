import java.util.*;

public class ClotildeTelevisionControl {
    static class State {
        int channel, presses;

        State(int channel, int presses) {
            this.channel = channel;
            this.presses = presses;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            int O = sc.nextInt(); // Source channel
            int D = sc.nextInt(); // Destination channel
            int K = sc.nextInt(); // Number of banned channels

            if (O == 0 && D == 0 && K == 0) break;

            Set<Integer> bannedChannels = new HashSet<>();
            
            for (int i = 0; i < K; i++) {
                int banned = sc.nextInt();
                bannedChannels.add(banned);
            }

            int result = bfs(O, D, bannedChannels);
            System.out.println(result);
        }
        sc.close();
    }

    private static int bfs(int source, int destination, Set<Integer> bannedChannels) {
        if (source == destination) return 0;

        boolean[] visited = new boolean[105000];
        Queue<State> queue = new LinkedList<>();
        queue.offer(new State(source, 0));
        visited[source] = true;

        while (!queue.isEmpty()) {
            State current = queue.poll();

            int currentChannel = current.channel;
            int currentPresses = current.presses;

            // Generate possible next states
            int[] nextChannels = new int[] {
                currentChannel + 1,
                currentChannel - 1,
                currentChannel * 2,
                currentChannel * 3,
                (currentChannel % 2 == 0) ? currentChannel / 2 : -1
            };

            for (int nextChannel : nextChannels) {
                if (nextChannel <= 0 || nextChannel > 100000) continue; // Out of range
                if (bannedChannels.contains(nextChannel) || visited[nextChannel]) continue;

                if (nextChannel == destination) return currentPresses + 1;

                // Mark as visited and add to queue
                visited[nextChannel] = true;
                queue.offer(new State(nextChannel, currentPresses + 1));
            }
        }

        // If we exhausted the queue without finding the destination
        return -1;
    }
}
