/*
 * Chua Yu Xuan, A0281524H
 * The algorithm doesn't pre-emptively decide whether 'C' should be 'L' or 'W'. 
 * It simply treats 'C' as flexible and handles it correctly during BFS, ensuring the correct number of islands is counted without prematurely classifying 'C' cells.
 * The main loop checks every cell in the grid. If the cell is 'L', we start a BFS to explore the entire island,  marking 'W' as visited.
 * The BFS explores all 4 possible directions (up, down, left, right) of 'L' as long as they are valid and not "W" (if it is 'L' or 'C', it is a valid neighbour).
 * After the BFS is done, we print the island count.
 * So C is not directly addressed, should address from pov of L
*/

 /*
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
         
        // read the grid
        for (int i = 0; i < r; i++) {
            grid[i] = sc.nextLine().toCharArray();
        }
         
        // Count the min number of islands when we choose to make C as either 'L' or 'W'
        int result = Math.min(countIslands(grid, r, c, 'L'), countIslands(grid, r, c, 'W'));
        System.out.println(result);
    }
     
    // Function to count the number of islands when 'C' is taken as 'L' or 'W'
    public static int countIslands(char[][] grid, int r, int c, char cloudAs) {
         
        char[][] pseudoGrid = new char[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 'C') {
                   pseudoGrid[i][j] = cloudAs;  // replace 'C' with 'L' or 'W'
                } else {
                   pseudoGrid[i][j] = grid[i][j]; // fill with original 'L' or 'W' 
                }
            }
        }
         
        // keep track of visited cells
        boolean[][] visited = new boolean[r][c];
        int islands = 0;
         
        // traverse the grid and perform DFS to count the number of islands
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (pseudoGrid[i][j] == 'L' && !visited[i][j]) {
                    // Found a new island 'L' so perform DFS
                    dfs(pseudoGrid, visited, i, j, r, c);
                    islands++;
                }
            }
        }
        
        return islands;
    }
     
    // DFS function to mark all connected 'L' islands
    public static void dfs(char[][] grid, boolean[][] visited, int x, int y, int r, int c) {
        Stack<int[]> stack = new Stack<>();
        stack.push(new int[] {x, y});
        visited[x][y] = true;
         
        while (!stack.isEmpty()) {
            int[] cell = stack.pop();
            int curX = cell[0];
            int curY = cell[1];
             
            // Explore the 4 possible directions
            for (int i = 0; i < 4; i++) {
                int newX = curX + gox[i];
                int newY = curY + goy[i];
                
                // Check if the new position is valid and is 'L' and have not visited before
                if (newX >= 0 && newX < r && newY >= 0 && newY < c 
                    && grid[newX][newY] == 'L' && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    stack.push(new int[] {newX, newY});
                }
            }
        }
    }
}
*/
/*
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

        // Process the grid to decide 'C' to be either 'L' or 'W'
        chooseClouds(grid, r, c);

        // Count the number of islands using BFS
        int result = countIslands(grid, r, c);
        System.out.println(result);
    }
     
    // Function to choose 'C' to be either 'L' or 'W' based on 4 neighbors
    public static void chooseClouds(char[][] grid, int r, int c) {
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 'C') {
                    // Check neighbors
                    boolean hasLandNeighbor = false;
                    
                    for (int dir = 0; dir < 4; dir++) {
                        int newX = i + gox[dir];
                        int newY = j + goy[dir];
                        
                        // Check if the up/down/left/right neighbor is valid and is 'L'
                        if (newX >= 0 && newX < r && newY >= 0 && newY < c && grid[newX][newY] == 'L') {
                            hasLandNeighbor = true;
                            break;
                        }
                    }

                    // If there's a land neighbor, treat 'C' as land, else treat it as water
                    if (hasLandNeighbor) {
                        grid[i][j] = 'L';
                    } else {
                        grid[i][j] = 'W';
                    }
                }
            }
        }
    }

    // Function to count the number of islands using BFS
    public static int countIslands(char[][] grid, int r, int c) {
        boolean[][] visited = new boolean[r][c];
        int islands = 0;

        // Traverse the grid and perform BFS to count the number of islands
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (grid[i][j] == 'L' && !visited[i][j]) {
                    // Found a new island 'L' so perform BFS
                    bfs(grid, visited, i, j, r, c);
                    islands++;
                }
            }
        }
        return islands;
    }

    // BFS function to mark all connected 'L' islands
    public static void bfs(char[][] grid, boolean[][] visited, int x, int y, int r, int c) {
        // Create a queue for BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {x, y});
        visited[x][y] = true;
        
        // Perform BFS
        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int curX = cell[0];
            int curY = cell[1];
             
            // Explore the 4 possible directions
            for (int i = 0; i < 4; i++) {
                int newX = curX + gox[i];
                int newY = curY + goy[i];
                
                // Check if the new position is valid and is 'L' and has not been visited
                if (newX >= 0 && newX < r && newY >= 0 && newY < c 
                    && grid[newX][newY] == 'L' && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.offer(new int[] {newX, newY});
                }
            }
        }
    }
}
*/

/*
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
        sc.nextLine(); // Consume the newline character after the integers
        
        char[][] grid = new char[r][c];

        // Read the grid
        for (int i = 0; i < r; i++) {
            grid[i] = sc.nextLine().toCharArray();
        }

        // To track visited cells
        boolean[][] visited = new boolean[r][c];
        int result = 0;

        // Directions for up, down, left, right
        int[][] directions = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}};

        // Perform BFS for every unvisited 'L' cell
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                // If the current cell is 'L' and hasn't been visited
                if (grid[i][j] == 'L' && !visited[i][j]) {
                    // Start BFS for this island
                    bfs(grid, visited, i, j, r, c, directions);
                    result++;
                } else if (grid[i][j] == 'W') {
                    visited[i][j] = true; // Mark 'W' cells as visited
                }
            }
        }

        // Output the number of islands found
        System.out.println(result);
        sc.close();  // Close the scanner to avoid resource leak
    }

    // BFS function to mark all connected 'L' cells as visited
    public static void bfs(char[][] grid, boolean[][] visited, int x, int y, int r, int c, int[][] directions) {
        // Queue for BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        visited[x][y] = true;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int curX = cell[0];
            int curY = cell[1];

            // Explore the 4 possible directions
            for (int i = 0; i < 4; i++) {
                int newX = curX + directions[i][0];
                int newY = curY + directions[i][1];

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

