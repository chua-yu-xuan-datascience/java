/*
 * find the shortest ladder that allows the duck to move from the top-left corner (entrance of the vault) to the bottom-right corner (where the special coin is), while traversing a grid of coin stacks. 
 * The duck can only climb upwards (requiring a ladder) but can freely jump down to lower stacks.
 * The priority queue is used to always process the cell with the smallest ladder length encountered so far. This ensures that we are always trying to find the path that uses the least maximum ladder length.
 * This 2D array keeps track of the minimum maximum ladder length required to reach each cell. Initially, all cells are set to Integer.MAX_VALUE, meaning we haven't visited them yet (inf)
 * For the starting cell (0, 0), the ladder length is 0 because no ladder is required to start.
 * For each neighboring cell (nx, ny), we calculate the height difference (heightDiff) between the current cell and the neighbor.
 * If heights[nx][ny] > heights[x][y], the duck needs a ladder to climb, and the heightDiff is positive.
 * If heights[nx][ny] <= heights[x][y], the duck can jump down (no ladder required), so heightDiff is 0.
 * The ladderLength is updated to the maximum of the current ladder length and the heightDiff (i.e., the larger of the two, since we're concerned with the largest climb encountered along the path).
 * The BFS works by always exploring the path with the smallest maximum ladder length first (because of the priority queue).
 * For each neighboring cell, if we can find a better path (i.e., one with a smaller maximum ladder length), we update the minLadder array and add the neighbor to the priority queue for further exploration.
 * Termination Condition:
 * The BFS continues until we reach the bottom-right corner (M-1, N-1). Once we reach it, we print the minimum maximum ladder length required to reach that cell.
 */
import java.util.*;

public class DuckingCoins {
    static class Cell {
        int x, y, ladderLength;
        
        Cell(int x, int y, int ladderLength) {
            this.x = x;
            this.y = y;
            this.ladderLength = ladderLength;
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read M and N (grid dimensions)
        int M = scanner.nextInt();
        int N = scanner.nextInt();
        
        // Read the grid of coin stack heights
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
        PriorityQueue<Cell> pq = new PriorityQueue<>(Comparator.comparingInt(c -> c.ladderLength));
        
        // Initialize visited array
        int[][] minLadder = new int[M][N];
        for (int[] row : minLadder) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }
        
        // Start BFS from top-left corner (0, 0), initial ladder length is 0
        minLadder[0][0] = 0;
        pq.offer(new Cell(0, 0, 0));
        
        // BFS with priority queue
        while (!pq.isEmpty()) {
            Cell current = pq.poll();
            int x = current.x;
            int y = current.y;
            int ladderLength = current.ladderLength;
            
            // If we reached the bottom-right corner (south-east corner)
            if (x == M - 1 && y == N - 1) {
                System.out.println(ladderLength);
                return;
            }
            
            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                
                if (nx >= 0 && nx < M && ny >= 0 && ny < N) {
                    // Calculate the required ladder length for the move
                    int heightDiff = Math.max(0, heights[nx][ny] - heights[x][y]);  // Only consider climbing up
                    
                    int newLadderLength = Math.max(ladderLength, heightDiff);
                    
                    // If we find a better way (smaller maximum ladder length) to reach (nx, ny)
                    if (newLadderLength < minLadder[nx][ny]) {
                        minLadder[nx][ny] = newLadderLength;
                        pq.offer(new Cell(nx, ny, newLadderLength));
                    }
                }
            }
        }
    }
}
