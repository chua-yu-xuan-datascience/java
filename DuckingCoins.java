import java.util.*;

public class DuckingCoins {
    static class Cell {
        int x, y, ladderLength;
        
        // ladderLength is the max height to reach from x to y
        Cell(int x, int y, int ladderLength) {
            this.x = x;
            this.y = y;
            this.ladderLength = ladderLength;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        int M = scanner.nextInt();
        int N = scanner.nextInt();
        
        // Read the grid for height of stack of coins
        int[][] heights = new int[M][N];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                heights[i][j] = scanner.nextInt();
            }
        }
        
        // Directions: Up, Down, Left, Right
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        // Priority Queue for BFS (min-heap), ordered by ladderLength (max climb encountered)
        // process the cell with the smallest ladder length encountered so far
        // to get path that uses the least maximum ladder length
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(c -> c.ladderLength));
        
        // Initialize- process cells with shortest max length to reach each cell, values set to inf (very high) before visiting
        int[][] minLadder = new int[M][N];
        for (int[] row : minLadder) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        
        // Start from (0, 0), initial ladder length is 0
        minLadder[0][0] = 0;
        pq.offer(new Cell(0, 0, 0));
        
        // BFS with priority queue, pop the cell with smallest current ladder requirement
        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            int x = current.x;
            int y = current.y;
            int ladderLength = current.ladderLength;
            
            // Until we reached the bottom-right corner (south-east corner)
            if (x == M - 1 && y == N - 1) {
                System.out.println(ladderLength);
                return;
            }
            
            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if (nx >= 0 && nx < M && ny >= 0 && ny < N) {
                    // Calculate the required ladder length for the move between neighbours, should be positive only
                    int heightDiff = Math.max(0, heights[nx][ny] - heights[x][y]);  // Only consider climbing up
                    
                    int newLadderLength = Math.max(ladderLength, heightDiff); // take the higher of the two as we want to track highest/worst climb required
                    
                    // If we find a better way (smaller maximum ladder length) to reach (nx, ny)
                    // update the minLadder array
                    // add the neighbour to the priority queue for further exploration
                    if (newLadderLength < minLadder[nx][ny]) {
                        minLadder[nx][ny] = newLadderLength;
                        pq.offer(new Cell(nx, ny, newLadderLength));
                    }
                }
            }
        }
    }
}
