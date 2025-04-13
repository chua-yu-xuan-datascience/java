import java.util.*;

public class Tombat {
    // DFS function to process nodes and record their finishing times
    static void dfs1(int v, boolean[] visited, List<List<Integer>> adj, Stack<Integer> stack) {
        visited[v] = true;
        for (int neighbor : adj.get(v)) {
            if (!visited[neighbor]) {
                dfs1(neighbor, visited, adj, stack);
            }
        }
        stack.push(v); // Once a node's DFS is complete/all neighbours explored, push it to the stack
    }

    // DFS on transposed graph
    // all nodes in one DFS call as 1 SCC
    static void dfs2(int v, boolean[] visited, List<List<Integer>> transAdj, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int neighbor : transAdj.get(v)) {
            if (!visited[neighbor]) {
                dfs2(neighbor, visited, transAdj, component);
            }
        }
    }
 
    // Function to transpose graph (reverse all edges)
    static List<List<Integer>> transpose(List<List<Integer>> adj, int n) {
        List<List<Integer>> transposedGraph = new ArrayList<>(n);
        for (int i = 0; i < n; i++) transposedGraph.add(new ArrayList<>());

        for (int v = 0; v < n; v++) {
            for (int neighbor : adj.get(v)) {
                transposedGraph.get(neighbor).add(v); // Reverse the edge
            }
        }

        return transposedGraph;
    }

    // Function to find all SCCs using Kosaraju's algorithm
    static int[] getSCCIds(List<List<Integer>> adj, int n, List<List<Integer>> sccs) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n];

        // First DFS to fill the stack with nodes by finish time
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i, visited, adj, stack);
            }
        }

        // Transpose
        List<List<Integer>> transposedGraph = transpose(adj, n);

        // 2nd DFS on the transposed graph, using the nodes from the stack above 
        // Mark each node with the id of the current SCC
        Arrays.fill(visited, false);
        int[] sccNum = new int[n];
        int id = 0;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                List<Integer> component = new ArrayList<>();
                dfs2(node, visited, transposedGraph, component);
                for (int v : component) {
                    sccNum[v] = id;
                }
                sccs.add(component);
                id++;
            }
        }

        return sccNum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        // Adj List
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            adj.get(p).add(q); // p can signal to q
        }

        // Find all SCCs
        List<List<Integer>> sccs = new ArrayList<>(); // the actual components
        int[] sccNum = getSCCIds(adj, n, sccs); // the scc ID each node belongs to
        int sccCount = sccs.size(); // total num of SCCs were found

        // Build condensed graph of SCCs (DAG)
        List<Set<Integer>> sccGraph = new ArrayList<>(); // each node is an SCC
        for (int i = 0; i < sccCount; i++) sccGraph.add(new HashSet<>());

        int[] inNum = new int[sccCount];

        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                int sccU = sccNum[u];
                int sccV = sccNum[v];
                if (sccU != sccV && sccGraph.get(sccU).add(sccV)) { // if u point to v and they belong to different SCC, add to DAG
                    inNum[sccV]++; // and update incoming edges of v
                }
            }
        }

        int solobots = 0;
        int botnets = 0;

        for (int i = 0; i < sccCount; i++) {
            if (sccs.get(i).size() == 1 && inNum[i] == 0) {
                solobots++; // Isolated size-1 SCCs with no in-edges
            } else if (sccs.get(i).size() > 1 && inNum[i] == 0) {
                botnets++; // Botnets with no in-edges
            }
        }

        System.out.println(solobots + " " + botnets);
        scanner.close();
    }
}
