/*
I hope you know the beautiful Union-Find structure. 
In this problem, you’re to implement something similar, but not identical. 
The data structure you need to write is also a collection of disjoint sets, supporting 3 operations:

1 p q
Union the sets containing p and q. If p and q are already in the same set, ignore this command.

2 p q
Move p to the set containing q. If p and q are already in the same set, ignore this command.

3 p
Return the number of elements and the sum of elements in the set containing p.

Initially, the collection contains n sets: {1}, {2}, {3}, ...{n}.

As an example, consider the sequence of operations in sample input 1 below.

Initially: {1}, {2}, {3}, {4}, {5}

Collection after operation 1 1 2: {1,2}, {3}, {4}, {5}

Collection after operation 2 3 4: {1,2}, {3,4}, {5} 
(we omit the empty set that is produced when taking out 3 from {3})

Collection after operation 1 3 5: {1,2}, {3,4,5}

Collection after operation 2 4 1: {1,2,4}, {3,5}

Input
There are several test cases. Each test case begins with a line containing two integers n and m (
1 <=n, m <= 100,000), the number of integers, and the number of commands. Each of the next 
 lines contains a command. For every operation, 1 <= p,q <= n
. The input is terminated by end-of-file (EOF). There are at most 20 cases, and the size of the input file does not exceed 5 MB.

Output
For each type-3 command, output 2 integers: the number of elements and the sum of elements.
*/

import java.util.*;

class UnionFind {
    private int[] p;  // Parent array
    private int[] rank;  // Rank array
    private int[] size;  // Size of each set
    private long[] sum;  // Sum of each set
    private Map<Integer, Set<Integer>> actualSets; // Tracks the actual elements in each set for debugging purposes

    public UnionFind(int N) {
        p = new int[N + 1];
        rank = new int[N + 1];
        size = new int[N + 1];
        sum = new long[N + 1];
        actualSets = new HashMap<>();

        for (int i = 1; i <= N; i++) {
            p[i] = i;  // Initially, every element is its own parent
            rank[i] = 1;
            size[i] = 1;
            sum[i] = i;
            actualSets.put(i, new HashSet<>(Set.of(i))); // Each element starts in its own set
        }
    }

    // Find with path compression
    public int findSet(int i) {
        if (p[i] != i) {
            p[i] = findSet(p[i]);
        }
        return p[i];
    }

    // Union by rank
    public void unionSet(int i, int j) {
        int x = findSet(i);
        int y = findSet(j);

        if (x != y) {
            if (rank[x] > rank[y]) {
                p[y] = x;
                size[x] += size[y];
                sum[x] += sum[y];
                actualSets.get(x).addAll(actualSets.get(y)); // Merge sets
                actualSets.remove(y);
            } else {
                p[x] = y;
                size[y] += size[x];
                sum[y] += sum[x];
                actualSets.get(y).addAll(actualSets.get(x)); // Merge sets
                actualSets.remove(x);
                if (rank[x] == rank[y]) {
                    rank[y]++;
                }
            }
        }
    }

    // Move an element to another set
    // Move an element to another set (fixed version)
public void moveSet(int i, int j) {
    int x = findSet(i); // Find root of element i
    int y = findSet(j); // Find root of element j

    if (x == y) return; // No need to move if already in the same set

    // Remove `i` from set `x`
    size[x]--;
    sum[x] -= i;
    actualSets.get(x).remove(i);

    // Update parent of `i` to point directly to `y`
    p[i] = y; // Set `i`'s parent to root of `y`
    if (!actualSets.containsKey(y)) {
        actualSets.put(y, new HashSet<>());
    }
    actualSets.get(y).add(i);

    // Add `i` to set `y`
    size[y]++;
    sum[y] += i;
}

    // Query the size and sum of the set containing `i`
    public String querySet(int i) {
        int x = findSet(i);
        return size[x] + " " + sum[x];
    }

    // Debugging method to print the current sets
    public void printSets() {
        System.out.println("Current sets:");
        for (Map.Entry<Integer, Set<Integer>> entry : actualSets.entrySet()) {
            System.out.println("Root " + entry.getKey() + ": " + entry.getValue());
        }
    }
}

public class BarelyOnionFound {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextInt()) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();

            UnionFind uf = new UnionFind(n);

            for (int i = 0; i < m; i++) {
                int op = scanner.nextInt();
                if (op == 1) {
                    int p = scanner.nextInt();
                    int q = scanner.nextInt();
                    uf.unionSet(p, q);
                    uf.printSets(); // Print sets after each operation
                } else if (op == 2) {
                    int p = scanner.nextInt();
                    int q = scanner.nextInt();
                    uf.moveSet(p, q);
                    uf.printSets(); // Print sets after each operation
                } else if (op == 3) {
                    int p = scanner.nextInt();
                    System.out.println(uf.querySet(p));
                    uf.printSets(); // Print sets after each operation
                }
            }
        }

        scanner.close();
    }
}