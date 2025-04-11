/*
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
        stack.push(v);  // Once a node's DFS is complete/all neighbours explored, push it to the stack
    }

    // DFS on transposed graph
    // all nodes in one DFS call as 1 SCC
    static void dfs2(int v, boolean[] visited, List<List<Integer>> transposedAdj, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int neighbor : transposedAdj.get(v)) {
            if (!visited[neighbor]) {
                dfs2(neighbor, visited, transposedAdj, component);
            }
        }
    }

    // Function to transpose graph (reverse all edges)
    static List<List<Integer>> transpose(List<List<Integer>> adj, int n) {
        List<List<Integer>> transposed = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            transposed.add(new ArrayList<>());
        }

        for (int v = 0; v < n; v++) {
            for (int neighbor : adj.get(v)) {
                transposed.get(neighbor).add(v);  // Reverse the edge
            }
        }

        return transposed;
    }

    // Function to find all SCCs using Kosaraju's algorithm
    static List<List<Integer>> findStronglyConnectedComponents(List<List<Integer>> adj, int n) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n];

        // Perform DFS to fill the stack with nodes by finish time
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i, visited, adj, stack);
            }
        }

        // Transpose the graph
        List<List<Integer>> transposed = transpose(adj, n);

        // Perform DFS on the transposed graph, using the nodes from the stack above to find SCCs
        Arrays.fill(visited, false);
        List<List<Integer>> sccs = new ArrayList<>();
        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                List<Integer> component = new ArrayList<>();
                dfs2(node, visited, transposed, component);
                sccs.add(component);
            }
        }

        return sccs;
    }

    // Function to calculate the roots of the DAG formed by SCCs
    static List<Integer> getRoots(List<List<Integer>> dag, int n) {
        boolean[] hasParent = new boolean[n];
        List<Integer> roots = new ArrayList<>();

        // Check for incoming edges to each node in the DAG
        for (int i = 0; i < n; i++) {
            for (int neighbor : dag.get(i)) {
                hasParent[neighbor] = true;
            }
        }

        // A root is a node with no incoming edges
        for (int i = 0; i < n; i++) {
            if (!hasParent[i]) {
                roots.add(i);
            }
        }

        return roots;
    }

    // Function to count SCCs with more than one node (botnets)
    static int countBotnets(List<List<Integer>> sccs) {
        int count = 0;
        for (List<Integer> component : sccs) {
            if (component.size() > 1) {
                count++;
            }
        }
        return count;
    }

    // Main function to read input, process, and output the result
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); 
        int m = scanner.nextInt(); 

        // Initialize the adjacency list
        List<List<Integer>> adj = new ArrayList<>(n);  
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        // Read the edges
        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            adj.get(b).add(a);  // Reverse the direction for Kosaraju's algorithm
        }

        // Step 1: Find SCCs using Kosaraju's algorithm
        List<List<Integer>> sccs = findStronglyConnectedComponents(adj, n);

        // Step 2: Create a condensed DAG of SCCs
        // Note: Each SCC can be treated as a node in the DAG. We'll count roots and big components.

        // Step 3: Calculate the roots of the DAG (solobots)
        List<Integer> roots = getRoots(adj, n);

        // Step 4: Calculate the number of solobots (roots of DAG)
        int solobots = roots.size();

        // Step 5: Calculate the number of botnets (SCCs with more than one node)
        int botnets = countBotnets(sccs);

        // Output
        System.out.println(solobots + " " + botnets);

        scanner.close();
    }
}
*/

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
    static void dfs2(int v, boolean[] visited, List<List<Integer>> transposedAdj, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int neighbor : transposedAdj.get(v)) {
            if (!visited[neighbor]) {
                dfs2(neighbor, visited, transposedAdj, component);
            }
        }
    }
 
    // Function to transpose graph (reverse all edges)
    static List<List<Integer>> transpose(List<List<Integer>> adj, int n) {
        List<List<Integer>> transposed = new ArrayList<>(n);
        for (int i = 0; i < n; i++) transposed.add(new ArrayList<>());

        for (int v = 0; v < n; v++) {
            for (int neighbor : adj.get(v)) {
                transposed.get(neighbor).add(v); // Reverse the edge
            }
        }

        return transposed;
    }

    // Function to find all SCCs using Kosaraju's algorithm
    static int[] findSCCIds(List<List<Integer>> adj, int n, List<List<Integer>> sccs) {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[n];

        // First DFS to fill the stack with nodes by finish time
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i, visited, adj, stack);
            }
        }

        // Transpose
        List<List<Integer>> transposed = transpose(adj, n);

        // 2nd DFS on the transposed graph, using the nodes from the stack above 
        // Mark each node with the id of the current SCC
        Arrays.fill(visited, false);
        int[] sccId = new int[n];
        int id = 0;

        while (!stack.isEmpty()) {
            int node = stack.pop();
            if (!visited[node]) {
                List<Integer> component = new ArrayList<>();
                dfs2(node, visited, transposed, component);
                for (int v : component) {
                    sccId[v] = id;
                }
                sccs.add(component);
                id++;
            }
        }

        return sccId;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();

        // Adj List
        List<List<Integer>> adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            adj.get(a).add(b); // a can signal to b
        }

        // Find all SCCs
        List<List<Integer>> sccs = new ArrayList<>(); // the actual components
        int[] sccId = findSCCIds(adj, n, sccs); // the component ID each node belongs to
        int sccCount = sccs.size(); // how many total SCCs were found

        // Build condensed graph of SCCs (DAG)
        List<Set<Integer>> sccGraph = new ArrayList<>(); // each node is an SCC
        for (int i = 0; i < sccCount; i++) sccGraph.add(new HashSet<>());

        int[] inDegree = new int[sccCount];

        for (int u = 0; u < n; u++) {
            for (int v : adj.get(u)) {
                int sccU = sccId[u];
                int sccV = sccId[v];
                if (sccU != sccV && sccGraph.get(sccU).add(sccV)) { // if u point to v and they belong to different SCC, add to DAG
                    inDegree[sccV]++; // and update incoming edges of v
                }
            }
        }

        int solobots = 0;
        int botnets = 0;

        for (int i = 0; i < sccCount; i++) {
            if (sccs.get(i).size() == 1 && inDegree[i] == 0) {
                solobots++; // Isolated size-1 SCCs with no in-edges
            } else if (sccs.get(i).size() > 1 && inDegree[i] == 0) {
                botnets++; // Botnets with no in-edges
            }
        }

        System.out.println(solobots + " " + botnets);
        scanner.close();
    }
}
