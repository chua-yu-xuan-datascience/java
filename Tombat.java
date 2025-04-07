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
        stack.push(v);  // Once a node's DFS is complete, push it to the stack
    }

    // DFS on transposed graph to explore and collect SCCs
    static void dfs2(int v, boolean[] visited, List<List<Integer>> transposedAdj, List<Integer> component) {
        visited[v] = true;
        component.add(v);
        for (int neighbor : transposedAdj.get(v)) {
            if (!visited[neighbor]) {
                dfs2(neighbor, visited, transposedAdj, component);
            }
        }
    }

    // Function to transpose the graph (reverse all edges)
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

        // Step 1: Perform a DFS on the original graph to fill the stack with nodes by finish time
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs1(i, visited, adj, stack);
            }
        }

        // Step 2: Transpose the graph
        List<List<Integer>> transposed = transpose(adj, n);

        // Step 3: Perform DFS on the transposed graph, using the nodes from the stack
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

        int n = scanner.nextInt();  // Number of bots (nodes)
        int m = scanner.nextInt();  // Number of edges (pairs)

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

        // Output the results
        System.out.println(solobots + " " + botnets);

        scanner.close();  // Close the scanner
    }
}

/*
 * you can consider further optimizations such as:
 * Using more efficient data structures for graph representation, like adjacency lists (which you've already done).
 * Avoiding unnecessary computations, such as keeping track of visited nodes during the SCC discovery phase to minimize redundant DFS calls.
 */