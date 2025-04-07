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