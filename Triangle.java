/*
read inputs, stop when n = -1. 
fill up adjacency matrix by row and column
use a hashset to track of unique vertices that are in a triangle
use nested for loop to track presence of edges btw a, b and c, with b and c bigger than the one before to prevent visiting the same edges again in symmetic adjacency matrix
if edges btw them = 1, add a,b and c to hashset
a simple list to store vertices NOT in hashset - weak vertices and then sort them in asc order and then print them
*/
import java.util.*;

public class Triangle {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            // Read the number of vertices
            int n = scanner.nextInt();
            if (n == -1) {
                break;
            }
            
            // create adjacency matrix
            int[][] adjmatrix = new int[n][n];
            
            // input number into adjacency matrix
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++) {
                    adjmatrix[r][c] = scanner.nextInt();
                }
            }
            
            // hashset to track of unique vertices that are in a triangle
            Set<Integer> triangleVertices = new HashSet<>();
            
            // Check for triangles, with b and c bigger than the other to prevent repetitions
            for (int a = 0; a < n; a++) {
                for (int b = a + 1; b < n; b++) {
                    if (adjmatrix[a][b] == 1) { // First determine if there is an edge between a and b
                        for (int c = b + 1; c < n; c++) {
                            if (adjmatrix[a][c] == 1 && adjmatrix[b][c] == 1) { // then look for edges between a and c & b and c
                                triangleVertices.add(a);
                                triangleVertices.add(b);
                                triangleVertices.add(c);
                            }
                        }
                    }
                }
            }
            
            // Find the weak vertices
            List<Integer> weakVertices = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!triangleVertices.contains(i)) {
                    weakVertices.add(i);
                }
            }
            
            // sort weak vertices sorted in ascending order and output
            Collections.sort(weakVertices);
            for (int i = 0; i < weakVertices.size(); i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(weakVertices.get(i));
            }
            System.out.println();
        }
        
        scanner.close();
    }
}
