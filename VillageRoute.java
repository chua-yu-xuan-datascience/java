import java.io.*;
import java.util.*;

// Kruskal's algorithm, use Disjoint set union
public class VillageRoute {
    // Edge with weight between vertices (villages) u and v
    static class Edge implements Comparable<Edge> {
        int u, v, weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge other) {
            return this.weight - other.weight;
        }
    }
    
    // Disjoint set union to build MST by path-compression and union-by-rank
    static class UnionFind {
        int[] parent, rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) parent[i] = i;
        }

        public int findSet(int u) {
            if (parent[u] != u) parent[u] = findSet(parent[u]);
            return parent[u];
        }

        public boolean isSameSet(int u, int v) {
            return findSet(u) == findSet(v);
        }

        public boolean unionSet(int u, int v) {
            if (!isSameSet(u, v)) {
                int x = findSet(u);
                int y = findSet(v);
        
                // Perform union by rank
                if (rank[x] > rank[y]) {
                    parent[y] = x;
                } else {
                    parent[x] = y;
                } 
                
                if (rank[x] == rank[y]) {
                    rank[y]++;  // Increase the rank of the new root
                }  
                return true; // Successfully unioned
            }
            return false; // Already in the same set
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        List<Edge> edges = new ArrayList<>();

        // Add edges (vertices and weight) to an edge list
        for (int i = 0; i < n; i++) {
            String[] parts = br.readLine().split(" ");
            for (int j = 0; j < n; j++) {
                int w = Integer.parseInt(parts[j]);
                if (i < j) {
                    edges.add(new Edge(i, j, w));
                }
            }
        }
        
        // Sort by edge weight
        Collections.sort(edges);
        UnionFind uf = new UnionFind(n);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            if (uf.unionSet(e.u, e.v)) { // if no cycle
                mst.add(e); // Add edges (along with vertices and weight info) to mst 
                if (mst.size() == n - 1) break;
            }
        }

        for (Edge e : mst) {
            bw.write((e.u + 1) + " " + (e.v + 1));
            bw.newLine();
        }

        bw.flush();
    }
}
