/*
 * The algorithm doesn't pre-emptively decide whether 'C' should be 'L' or 'W'. 
 * It simply treats 'C' as flexible and handles it correctly during BFS, ensuring the correct number of islands is counted without prematurely classifying 'C' cells.
 * The main loop checks every cell in the grid. If the cell is 'L', we start a BFS to explore the entire island,  marking 'W' as visited.
 * The BFS explores all 4 possible directions (up, down, left, right) of 'L' as long as they are valid and not "W" (if it is 'L' or 'C', it is a valid neighbour).
 * After the BFS is done, we print the island count.
 * So C is not directly addressed, should address from pov of L
*/
import java.util.*;

public class MinimumIslands {

    // Directions to go up, down, left, right
    static int[] gox = {-1, 1, 0, 0};  
    static int[] goy = {0, 0, -1, 1};  
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Read grid dimensions
        int r = sc.nextInt();
        int c = sc.nextInt();
        sc.nextLine();
        
        char[][] grid = new char[r][c];

        // Read the grid
        for (int i = 0; i < r; i++) {
            grid[i] = sc.nextLine().toCharArray();
        }

        // To track visited cells
        boolean[][] visited = new boolean[r][c];
        int islands = 0;

        // Perform BFS for every unvisited 'L' cell
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                // If the current cell is 'L' and hasn't been visited
                if (grid[i][j] == 'L' && !visited[i][j]) {
                    // Start BFS for this island
                    bfs(grid, visited, i, j, r, c);
                    islands++;
                } else if (grid[i][j] == 'W') {
                    visited[i][j] = true; // Mark 'W' cells as visited so that wont count them in BFS
                }
            }
        }

        // Output the number of islands found
        System.out.println(islands);
        sc.close();
    }

    // BFS function to mark all connected 'L' cells as visited
    public static void bfs(char[][] grid, boolean[][] visited, int x, int y, int r, int c) {
        // Queue for BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int curX = cell[0];
            int curY = cell[1];

            // Explore the 4 possible directions (up, down, left, right) using gox and goy
            for (int i = 0; i < 4; i++) {
                int newX = curX + gox[i];
                int newY = curY + goy[i]; 

                // Check if the new position is valid and has not been visited yet
                if (newX >= 0 && newX < r && newY >= 0 && newY < c 
                        && !visited[newX][newY] 
                        && grid[newX][newY] != 'W') {  // Ensure 'W' cells are not visited
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }
    }
}

