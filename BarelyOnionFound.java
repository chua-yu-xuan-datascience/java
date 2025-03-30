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

/*
import java.util.*;

// where, actual (traversal purposes), rank, removed(boolean), size, sum
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
    // Indicate if it is removed or not, so if union two sets, if ref a removed node, it will union with other tree
    // point to correct parent
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
    // Update remove to true for that one node, point parent to root of new set
    // update values, the node is just empty, so just a lot of empty nodes in tree but can easily get size and sum
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
*/

/*
import java.io.*;
import java.util.*;

class UnionFind {
    private int[] parent;
    private int[] next;
    private int[] size;
    private long[] sum;
    private int[] rank;

    public UnionFind(int N) {
        parent = new int[N + 1];
        next = new int[N + 1];
        size = new int[N + 1];
        sum = new long[N + 1];
        rank = new int[N + 1];

        // Initialise disjoint sets with their own element, size = 1, rank = 0
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
            next[i] = i;
            size[i] = 1;
            sum[i] = i;
            rank[i] = 0;
        }
    }

    // Find with path compression to return root 
    public int findSet(int i) {
        
        if (next[i] != parent[next[i]]) {
            next[i] = parent[next[i]]; // Path compression
        }
        return parent[next[i]]; 
    }

    // Check if two elements are in the same set by root
    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    // Union by rank with path compression
    public void unionSet(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i);
            int y = findSet(j);
                     
            
            // Attach the smaller tree under the larger one
            if (rank[x] > rank[y]) {
                parent[y] = x;
                next[i] = x; // Point i to root of the bigger tree
                size[x] += size[y];
                sum[x] += sum[y];

            } else {
                parent[x] = y;
                next[i] = y; // Point i to root of the bigger tree
                size[y] += size[x];
                sum[y] += sum[x];

                if (rank[x] == rank[y]) {
                    rank[y]++; 
                }
                    
            }
                
        }
    }

    // Move an element from one set to another
    public void moveSet(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i);
            int y = findSet(j);
            
            // Remove elem i from original set
            size[x]--;
            sum[x] -= i;

            // Move the element to the root of the set containing j
            next[i] = y;
            parent[i] = y;

            // Update the size and sum of the set containing y
            size[y]++;
            sum[y] += i;
        }
    }

    // Query the size and sum of the set containing the element i
    public String querySet(int i) {
        int x = findSet(i);
        return size[x] + " " + sum[x];
    }
}


class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}

public class BarelyOnionFound {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        while (io.hasMoreTokens()) {
            int n = io.getInt();
            int m = io.getInt();

            UnionFind auf = new UnionFind(n);

            for (int i = 0; i < m; i++) {
                int op = io.getInt();

                if (op == 1) {
                    int p = io.getInt();
                    int q = io.getInt();
                    auf.unionSet(p, q);

                } else if (op == 2) {
                    int p = io.getInt();
                    int q = io.getInt();
                    auf.moveSet(p, q);

                } else if (op == 3) {
                    int p = io.getInt();
                    io.println(auf.querySet(p));
                }
            }
        }
        io.close();
    }
}
*/

import java.io.*;
import java.util.*;

class UnionFind {
    private int[] root;
    private int[] parent;
    private long[] size;
    private long[] sum;
    private int[] rank;

    public UnionFind(int N) {
        root = new int[N + 1]; // rep of the set the node is in
        parent = new int[N + 1]; // parent of the node (or next pointer)
        size = new long[N + 1];
        sum = new long[N + 1];
        rank = new int[N + 1];
        
        // Initialise disjoint sets with their own element, size = 1
        for (int i = 1; i <= N; i++) {
            root[i] = i;
            parent[i] = i;
            size[i] = 1;
            sum[i] = i;
            rank[i] = 0;
            
        }
    }

    // Find with path compression to return real root 
    public int findSet(int i) {
        int tempRoot = root[i];
        while (parent[tempRoot] != tempRoot) { // if node's parent is not itself, it is not the real root
            tempRoot = parent[tempRoot]; // move up until identify root
        }
        root[i] = tempRoot; // real root of i
        return root[i];
    }

    // Check if two elements are in the same set by root
    public boolean isSameSet(int i, int j) {
        return findSet(i) == findSet(j);
    }

    // Union, no need by rank because in this question, all the sets will be flat
    //public void unionSet(int i, int j) {
        //if (!isSameSet(i, j)) {
            //int x = findSet(i);
            //int y = findSet(j);
                     
            //parent[x] = y; 
            //root[i] = y;
            //size[y] += size[x];
            //sum[y] += sum[x];                    
        //}
                
    //}

    public void unionSet(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i);
            int y = findSet(j);
    
            // Perform union by rank
            if (rank[x] > rank[y]) {
                parent[y] = x;
                root[j] = x;
                size[x] += size[y];
                sum[x] += sum[y];
            } else {
                parent[x] = y;
                root[i] = y;
                size[y] += size[x];
                sum[y] += sum[x];
            } 
            
            if (rank[x] == rank[y]) {
                rank[y]++;  // Increase the rank of the new root
            }
        }
    }
    

    // Move an element from one set to another
    public void moveSet(int i, int j) {
        if (!isSameSet(i, j)) {
            int x = findSet(i);
            int y = findSet(j);
            
            // Remove elem i from original set of i
            size[x]--;
            sum[x] -= i;

            // Only change root of i to y and not parent so that structure of set of i is maintained
            // when children of i calls findset (uses parent), they will find their correct root
            root[i] = y;

            // Update the size and sum of the set containing j
            size[y]++;
            sum[y] += i;
        }
    }

    // Query the size and sum of the set containing the element i
    public String querySet(int i) {
        int x = findSet(i);
        return size[x] + " " + sum[x];
    }
}


class Kattio extends PrintWriter {
    public Kattio(InputStream i) {
        super(new BufferedOutputStream(System.out));
        r = new BufferedReader(new InputStreamReader(i));
    }
    public Kattio(InputStream i, OutputStream o) {
        super(new BufferedOutputStream(o));
        r = new BufferedReader(new InputStreamReader(i));
    }

    public boolean hasMoreTokens() {
        return peekToken() != null;
    }

    public int getInt() {
        return Integer.parseInt(nextToken());
    }

    public String getWord() {
        return nextToken();
    }

    private BufferedReader r;
    private String line;
    private StringTokenizer st;
    private String token;

    private String peekToken() {
        if (token == null)
            try {
                while (st == null || !st.hasMoreTokens()) {
                    line = r.readLine();
                    if (line == null) return null;
                    st = new StringTokenizer(line);
                }
                token = st.nextToken();
            } catch (IOException e) { }
        return token;
    }

    private String nextToken() {
        String ans = peekToken();
        token = null;
        return ans;
    }
}

public class BarelyOnionFound {
    public static void main(String[] args) {
        Kattio io = new Kattio(System.in);
        while (io.hasMoreTokens()) {
            int n = io.getInt();
            int m = io.getInt();

            UnionFind auf = new UnionFind(n);

            for (int i = 0; i < m; i++) {
                int op = io.getInt();

                if (op == 1) {
                    int p = io.getInt();
                    int q = io.getInt();
                    auf.unionSet(p, q);

                } else if (op == 2) {
                    int p = io.getInt();
                    int q = io.getInt();
                    auf.moveSet(p, q);

                } else if (op == 3) {
                    int p = io.getInt();
                    io.println(auf.querySet(p));
                }
            }
        }
        io.close();
    }
}